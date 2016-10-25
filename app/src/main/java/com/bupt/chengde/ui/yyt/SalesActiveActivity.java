package com.bupt.chengde.ui.yyt;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.TvShowAdapter;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseBusinessInfo;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.bupt.chengde.widget.XListView;
import com.bupt.chengde.widget.XListView.IXListViewListener;

/**
 * 优惠活动
 * 
 * @author wyf
 * 
 */
public class SalesActiveActivity extends BaseActivity implements IXListViewListener {
	private CustomFrameLayout customFrameLayout;
	private TextView errorTxt;
	private View refresh;// 刷新按钮
	private XListView mXListView;

	private TextView nameTextView;
	private LinearLayout backTextView;

	private CustomProgressDialog pd;
	private List<ResponseBusinessInfo> returnList = new ArrayList<ResponseBusinessInfo>();
	private List<ResponseBusinessInfo> list;
	private Context mContext;
	private TvShowAdapter adapter;
	private int page = 1;
	private boolean isRefresh = false;
	private SalesBusinessListTask task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yewu_det);
		mContext = SalesActiveActivity.this;
		initView();
		// runHandler();
		pd = CustomProgressDialog.createDialog(mContext);
		pd.setMessage("正在请求数据");
		pd.show();
		task = new SalesBusinessListTask();
		task.execute(page);
		mXListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// application.showToast(reTvBusinesses.get(arg2 -
				// 1).getBusId());
				Intent intent = new Intent(SalesActiveActivity.this, SalesActiveDetilActivity.class);
				intent.putExtra(CodeConstants.BUSI_ID, returnList.get(position - 1).getBusId());
				startActivity(intent);
			}
		});
	}

	public void initView() {
		backTextView = (LinearLayout) findViewById(R.id.top_back);
		nameTextView = (TextView) findViewById(R.id.top_name);
		nameTextView.setText(getIntent().getStringExtra("value"));
		backTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		nameTextView.setText("优惠活动");
		mXListView = (XListView) findViewById(R.id.comm_listview);
		mXListView.setPullRefreshEnable(true);
		mXListView.setPullLoadEnable(true);
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
				task = new SalesBusinessListTask();
				task.execute(page);
			}
		});
	}
	@Override
	protected void onDestroy() {
		if (task!=null) {
			task.cancel(true);
		}
		super.onDestroy();
		
	}
	@Override
	public void onRefresh() {
		page = 1;
		isRefresh = true;
		task = new SalesBusinessListTask();
		task.execute(page);
	}

	@Override
	public void onLoadMore() {
		page++;
		isRefresh = false;
		task = new SalesBusinessListTask();
		task.execute(page);
	}

	class SalesBusinessListTask extends AsyncTask<Integer, Void, List<ResponseBusinessInfo>> {

		@Override
		protected List<ResponseBusinessInfo> doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			try {
				return WebserviceUtils.getSalesActive(page);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}

		@Override
		protected void onPostExecute(List<ResponseBusinessInfo> result) {
			// TODO Auto-generated method stub
			pd.dismiss();
			mXListView.stopRefresh();
			mXListView.stopLoadMore();
			if (null != result) {
				if (result.size() > 0) {
					if (isRefresh) {
						returnList.clear();
					}
					returnList.addAll(result);
					if (adapter == null) {
						adapter = new TvShowAdapter(returnList, activity);
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
}
