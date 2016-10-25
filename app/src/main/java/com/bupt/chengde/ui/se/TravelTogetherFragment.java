package com.bupt.chengde.ui.se;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.Comm1Adapter;
import com.bupt.chengde.adapter.Comm3Adapter;
import com.bupt.chengde.adapter.CommAdapter;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseSuggest;
import com.bupt.chengde.entity.ResponseTogether;
import com.bupt.chengde.entity.ResponseTravel;
import com.bupt.chengde.ui.BaseFragment;
import com.bupt.chengde.ui.se.TravelAgencyFragment.TravelAgencyTask;
import com.bupt.chengde.ui.se.TravelRecommendFragment.TravelRecommendTask;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.bupt.chengde.widget.XListView;
import com.bupt.chengde.widget.XListView.IXListViewListener;

public class TravelTogetherFragment extends BaseFragment implements
		IXListViewListener,OnItemClickListener {
	private XListView listView;
	private List<String> titleList = new ArrayList<String>();
	private List<String> urlList = new ArrayList<String>();
	private List<String> countList = new ArrayList<String>();
	private Comm3Adapter adapter;
	private List<ResponseTogether> returnList = new ArrayList<ResponseTogether>();
	private View refresh;// 刷新按钮
	private CustomFrameLayout customFrameLayout;
	private TextView errorTxt;
	private int page = 1;// 分页，从1开始,每页15条数据
	CustomProgressDialog pd;
	private boolean isRefresh;
    private TravelTogetherTask task;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_comm, null, false);
		listView = (XListView) view.findViewById(R.id.comm_listview);
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(true);
		listView.setXListViewListener(this);

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
				new TravelTogetherTask().execute();
			}
		});
		pd = CustomProgressDialog.createDialog(getActivity());
		pd.setMessage("正在请求数据");
		task=new TravelTogetherTask();task.execute();
		return view;
	}

	@Override
	public void onRefresh() {
		page=1;
		isRefresh=true;
		task=new TravelTogetherTask();task.execute();
	}

	@Override
	public void onLoadMore() {
		page++;
		isRefresh=false;
		task=new TravelTogetherTask();task.execute();

	}

	class TravelTogetherTask extends
			AsyncTask<Void, Void, List<ResponseTogether>> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			customFrameLayout.show(R.id.comm_listview);
		}

		@Override
		protected List<ResponseTogether> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return WebserviceUtils.getTogetherInfo(page);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<ResponseTogether> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();		   
			listView.stopRefresh();
			listView.stopLoadMore();
			if (result != null) {
				if (result.size() > 0) {
					 if (isRefresh) {
	                    	returnList.clear();
	                		titleList.clear();
	                		urlList.clear();
				}
					returnList.addAll(result);
					for (int i = 0; i < result.size(); i++) {
						String suggestTitle = result.get(i)
								.getTogetherTitle();
						String posterUrl = result.get(i).getTogetherPersonurl();
						String personCount=result.get(i).getTogetherPersonCount();
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
						if (personCount != null && !personCount.equals("")
								&& !personCount.equals("null")) {
							countList.add("已参团人数"+personCount+"人");
						} else {
							countList.add("");
						}

					}
					if (adapter == null) {
						adapter = new Comm3Adapter(titleList, countList,urlList,
								getActivity());
						listView.setAdapter(adapter);
					} else {
						adapter.notifyDataSetChanged();
					}
					listView.setOnItemClickListener(TravelTogetherFragment.this);
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(getActivity(), TravelDetailActivity.class);
		intent.putExtra(CodeConstants.BUSI_ID, returnList.get(position - 1)
				.getTogetherId());
		intent.putExtra(CodeConstants.MODULE_ID, CodeConstants.TRAVEL_TOGETHER);
		startActivity(intent);

	}
	@Override
	public void onDestroy() {
		if (this.task != null)
			this.task.cancel(true);

		super.onDestroy();
	}
}
