package com.bupt.chengde.ui.se;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.CommentsListAdapter;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseDiscussList;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.LogUtil;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.bupt.chengde.widget.XListView;
import com.bupt.chengde.widget.XListView.IXListViewListener;

public class CommentListActivity extends BaseActivity implements IXListViewListener {
	private static final String TAG = CommentListActivity.class.getSimpleName();
	private CustomFrameLayout customFrameLayout;
	private TextView errorTxt, titleTv;
	private View refresh, topbar;// 刷新按钮
	private XListView mXListView;
	private CustomProgressDialog pd;
	private boolean isRefresh = false;
	private int page = 1;

	private String modId;
	private String busiId;

	private CommentsListAdapter adapter;
	private List<ResponseDiscussList> returnList=new ArrayList<ResponseDiscussList>();
	// private List<ResponseDiscussList> datas;
	private Context mContext;
	private CommentsListTask task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_comm);
		mContext = CommentListActivity.this;
		modId = getIntent().getStringExtra(CodeConstants.MODULE_ID);
		busiId = getIntent().getStringExtra(CodeConstants.BUSI_ID);
		LogUtil.d(TAG, "===modId=" + modId + "===busiId=" + busiId);
		initView();
		// runHandler();
		pd = CustomProgressDialog.createDialog(this);
		pd.setMessage("正在请求数据，请稍后");
		if (!TextUtils.isEmpty(modId) && !modId.equals("null") && !TextUtils.isEmpty(busiId)
				&& !busiId.equals("null")) {
			pd.show();
			task = new CommentsListTask();
			task.execute(page);
		} else {
			customFrameLayout.show(R.id.common_net_error);
			errorTxt.setText("未知错误，moduid="+modId+"busiId="+busiId);
			refresh.setVisibility(View.GONE);
		}

	}

	private void initView() {

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
				task=new CommentsListTask();
				task.execute(page);
			}
		});
		topbar = findViewById(R.id.topbar);
		topbar.setVisibility(View.VISIBLE);
		titleTv = (TextView) findViewById(R.id.top_name);
		titleTv.setText("评论");
		findViewById(R.id.top_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

//	@Override
//	protected void doBackGround() throws Exception {
//		returnList = WebserviceUtils.getDiscussList(modId, busiId, page);
//		// returnList=getData();
//	}
//
//	@Override
//	protected void doForeGround() throws Exception {
//		mXListView.stopRefresh();
//		mXListView.stopLoadMore();
//		if (returnList != null) {
//			LogUtil.d(TAG, "returnList.size()=" + returnList.size());
//			if (returnList.size() > 0) {
//				if (isRefresh) {
//					returnList.clear();
//				}
//				returnList.addAll(returnList);
//				if (null == adapter) {
//					LogUtil.d(TAG, "null==adapter");
//					adapter = new CommentsListAdapter(mContext, returnList);
//					mXListView.setAdapter(adapter);
//				} else {
//					adapter.notifyDataSetChanged();
//				}
//			} else {
//				if (page > 1) {
//					page--;
//					application.showToast("没有更多评论");
//
//				} else {
//					customFrameLayout.show(R.id.common_net_error);
//					errorTxt.setText("暂无评论,点击刷新");
//					refresh.setVisibility(View.VISIBLE);
//				}
//
//			}
//		} else {
//			if (page > 1) {
//				page--;
//				application.showToast(getResources().getString(R.string.server_time_out));
//			} else {
//				if (!isRefresh) {
//					customFrameLayout.show(R.id.common_net_error);
//					errorTxt.setText(getResources().getString(R.string.server_time_out));
//					refresh.setVisibility(View.VISIBLE);
//				}
//			}
//
//		}
//	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		page = 1;
		isRefresh = true;
		task=new CommentsListTask();
		task.execute(page);

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		page++;
		isRefresh = false;
		task=new CommentsListTask();
		task.execute(page);
	}

	class CommentsListTask extends AsyncTask<Integer, Void, List<ResponseDiscussList>> {

		@Override
		protected List<ResponseDiscussList> doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			try {
				return WebserviceUtils.getDiscussList(modId, busiId, params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}
		@Override
		protected void onPostExecute(List<ResponseDiscussList> result) {
			pd.dismiss();
			mXListView.stopRefresh();
			mXListView.stopLoadMore();
			if (result != null) {
				com.bupt.chengde.util.LogUtil.d(TAG, "result.size()=" + result.size());
				if (result.size() > 0) {
					if (isRefresh) {
						returnList.clear();
					}
					returnList.addAll(result);
					if (null == adapter) {
						com.bupt.chengde.util.LogUtil.d(TAG, "null==adapter");
						adapter = new CommentsListAdapter(mContext, returnList);
						mXListView.setAdapter(adapter);
					} else {
						adapter.notifyDataSetChanged();
					}
				} else {
					if (page > 1) {
						page--;
						application.showToast("没有更多评论");

					} else {
						customFrameLayout.show(R.id.common_net_error);
						errorTxt.setText("暂无评论,点击刷新");
						refresh.setVisibility(View.VISIBLE);
					}

				}
			} else {
				if (page > 1) {
					page--;
					application.showToast(getResources().getString(R.string.server_time_out));
				} else {
					if (!isRefresh) {
						customFrameLayout.show(R.id.common_net_error);
						errorTxt.setText(getResources().getString(R.string.server_time_out));
						refresh.setVisibility(View.VISIBLE);
					}
				}

			}
		}

	}

}
