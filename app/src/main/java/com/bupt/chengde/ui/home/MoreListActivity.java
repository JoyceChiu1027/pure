package com.bupt.chengde.ui.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.Comm1Adapter;
import com.bupt.chengde.adapter.HomeMoreAdapter;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseGetMore;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.bupt.chengde.widget.XListView;
import com.bupt.chengde.widget.XListView.IXListViewListener;

/***************
 * @date:2016-4-11 下午5:36:47
 * @author:litf
 * @version:
 * @return:
 * @throws:
 ***************/
public class MoreListActivity extends BaseActivity implements IXListViewListener, OnItemClickListener, OnClickListener {
	private int type;// (type = 1 推荐新闻 2 每日惠3 热门活动)
	private int page = 1;
	private List<ResponseGetMore> returnList = new ArrayList<ResponseGetMore>();
	private TextView topName, errorTxt;
	private XListView mXListView;
	private List<Integer> moreIdList = new ArrayList<Integer>();
	private List<String> moreTitleList = new ArrayList<String>();
	private List<String> morePosterUrlList = new ArrayList<String>();
	private List<String> moreDateList = new ArrayList<String>();
	private List<String> moreDetailList = new ArrayList<String>();
	private HomeMoreAdapter adapter;
	private CustomFrameLayout customFrameLayout;
	private View refresh;// 刷新按钮
	CustomProgressDialog pd;

	private String modId;
	private String busiId;
	private boolean isRefresh = false;
    private Context mContext;
	private HomeRecommendTask task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext=MoreListActivity.this;
		setContentView(R.layout.activity_home_suggest_more);
		type = getIntent().getIntExtra(CodeConstants.HOME_LIST_TYPE, 1);

		initView();
		initListener();
		mXListView.setPullLoadEnable(true);
		mXListView.setPullRefreshEnable(true);
		mXListView.setXListViewListener(this);

		switch (type) {
		case 1:
			topName.setText("推荐");
			break;
		case 2:
			topName.setText("每日惠");
			break;
		case 3:
			topName.setText("热门活动");
			break;
		default:
			break;
		}

		customFrameLayout.setList(new int[] { R.id.comm_listview, R.id.common_net_error });

		pd = CustomProgressDialog.createDialog(this);
		pd.setMessage("正在请求数据");
		pd.show();
		task = new HomeRecommendTask();
		task.execute(type,page);
	}

	private void initView() {
		mXListView = (XListView) findViewById(R.id.comm_listview);
		customFrameLayout = (CustomFrameLayout) findViewById(R.id.web_frame);
		refresh = findViewById(R.id.error_btn);
		errorTxt = (TextView) findViewById(R.id.error_txt);
		topName = (TextView) findViewById(R.id.top_name);
	}

	private void initListener() {
		refresh.setOnClickListener(this);
		findViewById(R.id.top_back).setOnClickListener(this);
	}

	@Override
	public void onRefresh() {
		isRefresh = true;
		page = 1;
		task=new HomeRecommendTask();
		task.execute(type,page);
	}

	@Override
	public void onLoadMore() {
		page++;
		isRefresh = false;
		task=new HomeRecommendTask();
		task.execute(type,page);
	}

	class HomeRecommendTask extends AsyncTask<Integer, Void, List<ResponseGetMore>> {
		@Override
		protected List<ResponseGetMore> doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			try {
				return WebserviceUtils.getMoreUnique(params[0],params[1]);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<ResponseGetMore> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			mXListView.stopRefresh();
			mXListView.stopLoadMore();
			if (result != null) {
				if (result.size() > 0) {
					if (isRefresh) {
						returnList.clear();
						moreIdList.clear();
						moreTitleList.clear();
						morePosterUrlList.clear();
						moreDateList.clear();
						moreDetailList.clear();
					}
					returnList.addAll(result);
					for (int i = 0; i < result.size(); i++) {
						int moreId = result.get(i).getMoreId();
						String moreTitle = result.get(i).getMoreTitle();
						String morePosterUrl = result.get(i).getMorePosterUrl();
						String moreDate = result.get(i).getMoreDate();
						String moreDetail = result.get(i).getMoreContent();

						if (moreId != 0) {
							moreIdList.add(moreId);
						} else {
							moreIdList.add(0);
						}
						if (moreTitle != null && !moreTitle.equals("") && !moreTitle.equals("null")) {
							moreTitleList.add(moreTitle);
						} else {
							moreTitleList.add("");
						}
						if (morePosterUrl != null && !morePosterUrl.equals("") && !morePosterUrl.equals("null")) {
							morePosterUrlList.add(morePosterUrl);
						} else {
							morePosterUrlList.add("");
						}
						if (moreDate != null && !moreDate.equals("") && !moreDate.equals("null")) {
							moreDateList.add(moreDate);
						} else {
							moreDateList.add("");
						}
						if (moreDetail != null && !moreDetail.equals("") && !moreDetail.equals("null")) {
							moreDetailList.add(moreDetail);
						} else {
							moreDetailList.add("");
						}
					}
					if (adapter == null) {
						adapter = new HomeMoreAdapter(moreTitleList, morePosterUrlList, moreDateList, moreDetailList,
								MoreListActivity.this);
						mXListView.setAdapter(adapter);
					} else {
						adapter.notifyDataSetChanged();
					}
					mXListView.setOnItemClickListener(MoreListActivity.this);
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.error_btn:
			customFrameLayout.show(R.id.comm_listview);
			pd.show();
			task=new HomeRecommendTask();
			task.execute(type,page);
			break;
		case R.id.top_back:
			finish();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent();
		intent.setClass(MoreListActivity.this, HomeListDetailActivity.class);
		intent.putExtra(CodeConstants.BUSI_ID, returnList.get(position - 1).getMoreId() + "");
		// (type: 1推荐新闻，2每日惠，3热门活动；)
		if (type == 1) {
			intent.putExtra(CodeConstants.MODULE_ID, CodeConstants.MAIN_NEWS);
		} else if (type == 2) {
			intent.putExtra(CodeConstants.MODULE_ID, CodeConstants.MAIN_SALES);
		} else if (type == 3) {
			intent.putExtra(CodeConstants.MODULE_ID, CodeConstants.MAIN_HOT);
		}
		startActivity(intent);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (null!=task) {
			task.cancel(true);
		}
		super.onDestroy();
	}
}
