package com.bupt.chengde.ui.se;

import java.util.ArrayList;
import java.util.List;

import android.R.transition;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.bupt.chengde.R;
import com.bupt.chengde.adapter.Comm1Adapter;

import com.bupt.chengde.control.AdTask;
import com.bupt.chengde.control.AsyncTaskCallback;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseAdPoster;
import com.bupt.chengde.entity.ResponseSuggest;
import com.bupt.chengde.ui.BaseFragment;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.bupt.chengde.widget.ListViewTop;
import com.bupt.chengde.widget.XListView;
import com.bupt.chengde.widget.XListView.IXListViewListener;

public class TravelRecommendFragment extends BaseFragment implements
		IXListViewListener, OnItemClickListener,
		AsyncTaskCallback<List<ResponseAdPoster>> {
	
	private ListViewTop listViewTop;
	private AdTask adTask;
	private Context mContext;
	private List<ResponseAdPoster> adReturnList = new ArrayList<ResponseAdPoster>();

	private XListView mXListView;
	private List<String> titleList = new ArrayList<String>();
	private List<String> urlList = new ArrayList<String>();
	private Comm1Adapter adapter;
	private CustomFrameLayout customFrameLayout;
	private List<ResponseSuggest> returnList = new ArrayList<ResponseSuggest>();
	private TextView errorTxt;
	private View refresh;// 刷新按钮
	private int page = 1;// 分页，从1开始,每页15条数据
	private boolean isRefresh=false;
	CustomProgressDialog pd;
    private TravelRecommendTask task;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_comm, null, false);
		mXListView = (XListView) view.findViewById(R.id.comm_listview);
		mXListView.setPullLoadEnable(true);
		mXListView.setPullRefreshEnable(true);
		mXListView.setXListViewListener(this);

		pd = CustomProgressDialog.createDialog(getActivity());
		pd.setMessage("正在请求数据");
		listViewTop = new ListViewTop(getActivity());
		mXListView.addHeaderView(listViewTop);
		pd.show();

		customFrameLayout = (CustomFrameLayout) view
				.findViewById(R.id.web_frame);
		customFrameLayout.setList(new int[] { R.id.comm_listview,
				R.id.common_net_error });
		refresh = view.findViewById(R.id.error_btn);
		errorTxt = (TextView) view.findViewById(R.id.error_txt);

		refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				customFrameLayout.show(R.id.comm_listview);
				pd.show();
				adTask = new AdTask(1, mContext, adReturnList);
				adTask.setCallback(TravelRecommendFragment.this);
				adTask.execute(CodeConstants.TRAVEL_SUGGEST);
				new TravelRecommendTask().execute();
				
			}
		});
		adTask = new AdTask(1, mContext, adReturnList);
		adTask.setCallback(this);
		adTask.execute(CodeConstants.TRAVEL_SUGGEST);
		task=new TravelRecommendTask();
		task.execute();
		return view;
	}

	@Override
	public void onRefresh() {
		page=1;
		isRefresh = true;
		adTask = new AdTask(1, mContext, adReturnList);
		adTask.setCallback(this);
		adTask.execute(CodeConstants.TRAVEL_SUGGEST);
		task=new TravelRecommendTask();task.execute();
	}

	@Override
	public void onLoadMore() {
		page++;
		isRefresh=false;
		task=new TravelRecommendTask();task.execute();
	}

	class TravelRecommendTask extends
			AsyncTask<Void, Void, List<ResponseSuggest>> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected List<ResponseSuggest> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return WebserviceUtils.getSuggestInfo(page);
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
						String suggestTitle = result.get(i).getSuggestTitle();
						String posterUrl = result.get(i).getPosterurl();
						if (suggestTitle != null && !suggestTitle.equals("")
								&& !suggestTitle.equals("null")) {
							titleList.add(suggestTitle);
						} else {
							titleList.add("");
						}
						if (posterUrl != null && !posterUrl.equals("")
								&& !posterUrl.equals("null")) {
							urlList.add(posterUrl);
						} else {
							urlList.add("");
						}

					}
					if (adapter == null) {
						adapter = new Comm1Adapter(titleList, urlList,
								getActivity());
						mXListView.setAdapter(adapter);
					} else {
						adapter.notifyDataSetChanged();
					}

					mXListView
							.setOnItemClickListener(TravelRecommendFragment.this);
				} else {
					if (page > 1) {
						page--;
						Toast.makeText(getActivity(), "没有更多",
								Toast.LENGTH_SHORT).show();
						
						
					} else {
						if (!isRefresh) {
							customFrameLayout.show(R.id.common_net_error);
							refresh.setVisibility(View.VISIBLE);
							errorTxt.setText("服务器无数据返回");
						}
						
					}
				}
			} else {
				if (page>1) {
					page--;
					Toast.makeText(getActivity(), "网络连接超时，请检查网络设置",
							Toast.LENGTH_SHORT).show();
					
				}else{
					if (!isRefresh) {
						customFrameLayout.show(R.id.common_net_error);
						refresh.setVisibility(View.VISIBLE);
						errorTxt.setText("网络连接超时，请检查网络设置");
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(getActivity(), TravelDetailActivity.class);
		intent.putExtra(CodeConstants.BUSI_ID, returnList.get(position - 2)
				.getSuggestId());
		intent.putExtra(CodeConstants.MODULE_ID, CodeConstants.TRAVEL_SUGGEST);
		startActivity(intent);
	}
	@Override
	public void onDestroy() {
		if (this.task != null && this.task.getStatus() == Status.RUNNING)
			this.task.cancel(true);
		if (this.adTask!=null&&this.adTask.getStatus()==Status.RUNNING) {
			this.adTask.cancel(true);
		}
		listViewTop.stopScroll();
		super.onDestroy();
	}
}
