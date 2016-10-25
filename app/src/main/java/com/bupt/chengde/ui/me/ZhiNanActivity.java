package com.bupt.chengde.ui.me;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.bupt.chengde.R;
import com.bupt.chengde.adapter.GuideAdapter;
import com.bupt.chengde.entity.ResponseAppUseGuide;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.LogUtil;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.bupt.chengde.widget.XListView;
import com.bupt.chengde.widget.XListView.IXListViewListener;

public class ZhiNanActivity extends BaseActivity implements IXListViewListener {
    private static final String TAG=ZhiNanActivity.class.getSimpleName();
	private CustomFrameLayout customFrameLayout;
	private View refresh;// 刷新按钮
	private TextView errorTxt;
	private XListView mXListView;
	private CustomProgressDialog pd;
	private List<ResponseAppUseGuide> returnList = new ArrayList<ResponseAppUseGuide>();
	private GuideAdapter adapter;
	private Context mContext;
	private int page = 1;
	private boolean isRefresh = false;
	private GuideListTask task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhi_nan);
		com.bupt.chengde.util.LogUtil.i(TAG, "onCreate is called");
		mContext=this;
		initView();
		pd = CustomProgressDialog.createDialog(this);
		pd.setMessage("正在请求数据，请稍后");
		pd.show();
		task = new GuideListTask();
		task.execute(page);
		// runHandler();
	}

	private void initView() {
		((TextView) findViewById(R.id.top_name)).setText("使用指南");
		findViewById(R.id.top_back).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		mXListView = (XListView) findViewById(R.id.comm_listview);
		
		mXListView.setPullRefreshEnable(true);
		mXListView.setPullLoadEnable(false);
		mXListView.setXListViewListener(this);
		customFrameLayout = (CustomFrameLayout) findViewById(R.id.web_frame);
		customFrameLayout.setList(new int[] { R.id.comm_listview, R.id.common_net_error });
		refresh = findViewById(R.id.error_btn);
		errorTxt = (TextView) findViewById(R.id.error_txt);

		refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				customFrameLayout.show(R.id.comm_listview);
				pd.show();
				task = new GuideListTask();
				task.execute(page);
			}
		});

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		page = 1;
		isRefresh = true;
		task = new GuideListTask();
		task.execute(page);
	}

	@Override
	public void onLoadMore() {
		page++;
		isRefresh = false;
		task = new GuideListTask();
		task.execute(page);
	}

	class GuideListTask extends AsyncTask<Integer, Void, List<ResponseAppUseGuide>> {

		@Override
		protected List<ResponseAppUseGuide> doInBackground(Integer... params) {
			try {
				return WebserviceUtils.getUserGuide(params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		}

		@Override
		protected void onPostExecute(List<ResponseAppUseGuide> result) {
			pd.dismiss();
			mXListView.stopRefresh();
			mXListView.stopLoadMore();
			LogUtil.e(TAG, "GuideListTask.onPostExecute is called and result=null is "+(result==null));
			
			if (result != null) {
				com.bupt.chengde.util.LogUtil.i(TAG, " result.size= "+result.size());
				if (result.size() > 0) {
					if (isRefresh) {
						returnList.clear();
					}
					returnList.addAll(result);
					if (adapter == null) {
						com.bupt.chengde.util.LogUtil.w(TAG, "guideadapter==null is called");
						adapter = new GuideAdapter(mContext, returnList);
						mXListView.setAdapter(adapter);
					} else {
						adapter.notifyDataSetChanged();
					}
				} else {
					if (page > 1) {
						page--;
						application.showToast(getResources().getString(R.string.server_no_more));
					} else {
						if (!isRefresh) {
							customFrameLayout.show(R.id.common_net_error);
							refresh.setVisibility(View.VISIBLE);
							errorTxt.setText(getResources().getString(R.string.server_no_data));
						}
					}
				}
			} else {
				if (page > 1) {
					page--;
					application.showToast(getResources().getString(R.string.server_time_out));
				} else {
					if (!isRefresh) {
						customFrameLayout.show(R.id.common_net_error);
						refresh.setVisibility(View.VISIBLE);
						errorTxt.setText(getResources().getString(R.string.server_time_out));
					}
				}
			}

		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (task != null) {
			task.cancel(true);
		}
		super.onDestroy();
	}
}
