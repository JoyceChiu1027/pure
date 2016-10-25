package com.bupt.chengde.ui.me;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.GuideAdapter;
import com.bupt.chengde.adapter.ProblemAdapter;
import com.bupt.chengde.entity.ResponseCommonProblem;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.ui.me.ZhiNanActivity.GuideListTask;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.bupt.chengde.widget.XListView;
import com.bupt.chengde.widget.XListView.IXListViewListener;

/**
 * 常见问题列表
 * 
 * @author wyf
 * 
 */
public class ProListActivity extends BaseActivity implements IXListViewListener {
	private CustomFrameLayout customFrameLayout;
	private View refresh;// 刷新按钮
	private TextView errorTxt;
	private XListView mXListView;

	private LinearLayout backTextView;
	private CustomProgressDialog pd;
	private List<ResponseCommonProblem> returnList=new ArrayList<ResponseCommonProblem>();
	private ProblemAdapter adapter;
	private boolean isRefresh = false;// 是否加载更多
	private int page = 1;
	private Context mContext;
	private GetQuestionsTask task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pro_list);
		mContext=this;
		initView();
		pd=CustomProgressDialog.createDialog(this);
		pd.setMessage("正在请求数据，请稍后");
		pd.show();
		task=new GetQuestionsTask();
		task.execute(page);
		//runHandler();
	}

	private void initView() {
		((TextView) findViewById(R.id.top_name)).setText("常见问题");
		findViewById(R.id.top_back).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		mXListView = (XListView) findViewById(R.id.comm_listview);
		mXListView.setPullLoadEnable(true);
		mXListView.setPullRefreshEnable(true);
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
				task = new GetQuestionsTask();
				task.execute(page);
			}
		});
	}


	@Override
	public void onRefresh() {
		isRefresh = true;
		page = 1;
		task=new GetQuestionsTask();
		task.execute(page);
	}

	@Override
	public void onLoadMore() {
		isRefresh = false;
		page++;
		task=new GetQuestionsTask();
		task.execute(page);
	}

	class GetQuestionsTask extends AsyncTask<Integer, Void, List<ResponseCommonProblem>> {

		@Override
		protected List<ResponseCommonProblem> doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			try {
				return WebserviceUtils.getQuestions(params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}
		@Override
		protected void onPostExecute(List<ResponseCommonProblem> result) {
			pd.dismiss();
			mXListView.stopRefresh();
			mXListView.stopLoadMore();
			if (result != null) {
				if (result.size() > 0) {
					if (isRefresh) {
						returnList.clear();
					}
					returnList.addAll(result);
					if (adapter == null) {
						adapter = new ProblemAdapter( returnList,mContext);
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
