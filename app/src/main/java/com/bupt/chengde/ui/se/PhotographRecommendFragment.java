package com.bupt.chengde.ui.se;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.Comm1Adapter;
import com.bupt.chengde.adapter.Comm3Adapter;
import com.bupt.chengde.control.AdTask;
import com.bupt.chengde.control.AsyncTaskCallback;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseAdPoster;
import com.bupt.chengde.entity.ResponseSuggest;
import com.bupt.chengde.entity.ResponseTravel;
import com.bupt.chengde.ui.BaseFragment;
import com.bupt.chengde.ui.se.PhotographAppreciationFragment.PhotographAppreciationTask;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.bupt.chengde.widget.ListViewTop;
import com.bupt.chengde.widget.XListView;
import com.bupt.chengde.widget.XListView.IXListViewListener;

/***************
 * @date:2016-4-12 下午8:19:57
 * @author:litf
 * @version:
 * @return:
 * @throws:
 ***************/
public class PhotographRecommendFragment extends BaseFragment
		implements IXListViewListener, OnItemClickListener, AsyncTaskCallback<List<ResponseAdPoster>> {

	private boolean isRefresh = false;
	private AdTask adTask;
	private PhotographRecommendTask task;
	private List<ResponseAdPoster> adReturnList = new ArrayList<ResponseAdPoster>();
	private ListViewTop listViewTop;

	private XListView mXListView;
	private List<String> titleList = new ArrayList<String>();
	private List<String> urlList = new ArrayList<String>();
	private Comm1Adapter adapter;
	private List<ResponseSuggest> returnList = new ArrayList<ResponseSuggest>();
	private View refresh;// 刷新按钮
	private CustomFrameLayout customFrameLayout;
	private TextView errorTxt;
	CustomProgressDialog pd;
	private int page = 1;
	private Context mContext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_comm, null, false);
		mContext = getActivity();
		mXListView = (XListView) view.findViewById(R.id.comm_listview);
		mXListView.setPullLoadEnable(true);
		mXListView.setPullRefreshEnable(true);
		mXListView.setXListViewListener(this);

		pd = CustomProgressDialog.createDialog(getActivity());
		pd.setMessage("正在请求数据");
		listViewTop = new ListViewTop(getActivity());
		mXListView.addHeaderView(listViewTop);
		pd.show();

		customFrameLayout = (CustomFrameLayout) view.findViewById(R.id.web_frame);
		customFrameLayout.setList(new int[] { R.id.comm_listview, R.id.common_net_error });
		refresh = view.findViewById(R.id.error_btn);
		errorTxt = (TextView) view.findViewById(R.id.error_txt);

		refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				customFrameLayout.show(R.id.comm_listview);
				pd.show();
				adTask = new AdTask(1, mContext, adReturnList);
				adTask.setCallback(PhotographRecommendFragment.this);
				adTask.execute(CodeConstants.PHOTO_SUGGEST);
				new PhotographRecommendTask().execute();

			}
		});

		adTask = new AdTask(1, mContext, adReturnList);
		adTask.setCallback(this);
		adTask.execute(CodeConstants.PHOTO_SUGGEST);
		task = new PhotographRecommendTask();
		task.execute();
		return view;
	}

	@Override
	public void onRefresh() {

		page = 1;
		isRefresh = true;
		adTask = new AdTask(1, mContext, adReturnList);
		adTask.setCallback(this);
		adTask.execute(CodeConstants.PHOTO_SUGGEST);
		task = new PhotographRecommendTask();
		task.execute();
	}

	@Override
	public void onLoadMore() {
		page++;
		isRefresh = false;
		task = new PhotographRecommendTask();
		task.execute();
	}

	class PhotographRecommendTask extends AsyncTask<Void, Void, List<ResponseSuggest>> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			customFrameLayout.show(R.id.comm_listview);
		}

		@Override
		protected List<ResponseSuggest> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return WebserviceUtils.getSuggestPictureInfo(page);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<ResponseSuggest> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			mXListView.stopRefresh();
			mXListView.stopLoadMore();
			if (result != null) {
				if (result.size() > 0) {
					// 请求广告条
					if (isRefresh) {
						returnList.clear();
						titleList.clear();
						urlList.clear();
					}
					returnList.addAll(result);
					for (int i = 0; i < result.size(); i++) {
						String title = result.get(i).getSuggestTitle();
						String picUrl = result.get(i).getPosterurl();
						if (title != null && !title.equals("") && !title.equals("null")) {
							titleList.add(title);
						} else {
							titleList.add("");
						}

						if (picUrl != null && !picUrl.equals("") && !picUrl.equals("null")) {
							urlList.add(picUrl);
						} else {
							urlList.add("");
						}
					}

					if (adapter == null) {
						adapter = new Comm1Adapter(titleList, urlList, mContext);
						mXListView.setAdapter(adapter);
					} else {
						adapter.notifyDataSetChanged();
					}
					mXListView.setOnItemClickListener(PhotographRecommendFragment.this);
				} else {
					if (page > 1) {
						page--;
						if (isAdded())
						activity.application.showToast(getResources().getString(R.string.server_no_more));

					} else {
						if (!isRefresh) {
							customFrameLayout.show(R.id.common_net_error);
							refresh.setVisibility(View.VISIBLE);
							if (isAdded())
							errorTxt.setText(getResources().getString(R.string.server_no_data));
						}
					}
				}
			} else {
				if (page > 1) {
					page--;
					if (isAdded())
					activity.application.showToast(getResources().getString(R.string.server_time_out));

				} else {
					if (!isRefresh) {
						customFrameLayout.show(R.id.common_net_error);
						refresh.setVisibility(View.VISIBLE);
						if (isAdded())
						errorTxt.setText(getResources().getString(R.string.server_time_out));
					}
				}
			}

		}

	}

	@Override
	public void onCompleted(List<ResponseAdPoster> response) {
		// TODO Auto-generated method stub
		if (isRefresh) {
			mXListView.removeHeaderView(listViewTop);
			mXListView.addHeaderView(listViewTop);
		}
		listViewTop.initData(1, response);
	}

	@Override
	public void onFailed(List<ResponseAdPoster> response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNetError() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.d("PhotographRecomment", "position=" + position);
		Intent intent = new Intent();
		intent.setClass(getActivity(), PhotographDetailActivity.class);
		intent.putExtra(CodeConstants.BUSI_ID, returnList.get(position - 2).getSuggestId());
		intent.putExtra(CodeConstants.MODULE_ID, CodeConstants.PHOTO_SUGGEST);
		// Type : 1 推荐 2 论坛 3 赏析 4 影楼 5 摄友
		// intent.putExtra(CodeConstants.TYPE, 1);
		startActivity(intent);
	}

	@Override
	public void onDestroy() {
		if (this.task != null && this.task.getStatus() == Status.RUNNING)
			this.task.cancel(true);
		if (this.adTask != null && this.adTask.getStatus() == Status.RUNNING) {
			this.adTask.cancel(true);
		}
		listViewTop.stopScroll();
		super.onDestroy();
	}
}
