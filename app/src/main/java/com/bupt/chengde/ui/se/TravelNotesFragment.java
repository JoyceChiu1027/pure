package com.bupt.chengde.ui.se;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseNotes;
import com.bupt.chengde.ui.BaseFragment;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.bupt.chengde.widget.XListView;
import com.bupt.chengde.widget.XListView.IXListViewListener;

public class TravelNotesFragment extends BaseFragment implements
		IXListViewListener, OnItemClickListener {
	private XListView listView;
	private List<String> titleList = new ArrayList<String>();
	private List<String> urlList = new ArrayList<String>();
	private Comm1Adapter adapter;
	private List<ResponseNotes> returnList = new ArrayList<ResponseNotes>();
	private CustomFrameLayout customFrameLayout;
	private View refresh;// 刷新按钮
	private TextView errorTxt;
	private int page = 1;// 分页，从1开始,每页15条数据
	CustomProgressDialog pd;
	private boolean isRefresh=false;

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
				new TravelNoteTask().execute();
			}
		});
		pd = CustomProgressDialog.createDialog(getActivity());
		pd.setMessage("正在请求数据");
		new TravelNoteTask().execute();
		return view;
	}

	@Override
	public void onRefresh() {
		
		isRefresh=true;
		page=1;
		new TravelNoteTask().execute();
	}

	@Override
	public void onLoadMore() {
		isRefresh=false;
		page ++;
		new TravelNoteTask().execute();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	class TravelNoteTask extends AsyncTask<Void, Void, List<ResponseNotes>> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			customFrameLayout.show(R.id.comm_listview);
		}

		@Override
		protected List<ResponseNotes> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return WebserviceUtils.getNotesInfo(page);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<ResponseNotes> result) {
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
						String suggestTitle = result.get(i).getNoteTitle();
						String posterUrl = result.get(i).getNotePersonurl();
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
						listView.setAdapter(adapter);
					} else {
						adapter.notifyDataSetChanged();
					}

					listView.setOnItemClickListener(TravelNotesFragment.this);
				} else {
					if (page > 1) {
						page--;
						activity.application.showToast(getResources().getString(R.string.server_no_more));
						
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
					activity.application.showToast(getResources().getString(R.string.server_time_out));
				
					
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(getActivity(), TravelDetailActivity.class);
		intent.putExtra(CodeConstants.BUSI_ID, returnList.get(position - 1)
				.getNoteId());
		intent.putExtra(CodeConstants.MODULE_ID, CodeConstants.TRAVEL_NOTES);
		startActivity(intent);
	}
}