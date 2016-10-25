package com.bupt.chengde.ui.se;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.text.TextUtils;
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
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseForumInfo;
import com.bupt.chengde.ui.BaseFragment;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.bupt.chengde.widget.XListView;
import com.bupt.chengde.widget.XListView.IXListViewListener;

public class PhotographForumFragment extends BaseFragment implements
		IXListViewListener, OnItemClickListener {
	private XListView mXListView;

	private List<String> titleList = new ArrayList<String>();
	private List<String> urlList = new ArrayList<String>();
	private List<String> detailList = new ArrayList<String>();
	private Comm3Adapter adapter;
	private List<ResponseForumInfo> returnList = new ArrayList<ResponseForumInfo>();
	private View refresh;// 刷新按钮
	private CustomFrameLayout customFrameLayout;
	private TextView errorTxt;
	private Context mContext;
	private int page = 1;
	CustomProgressDialog pd;
	private boolean isRefresh=false;
	private PhotographForumTask task;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_comm, null, false);
		mContext = getActivity();
		mXListView = (XListView) view.findViewById(R.id.comm_listview);
		mXListView.setPullLoadEnable(true);
		mXListView.setPullRefreshEnable(true);
		mXListView.setXListViewListener(this);
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
				new PhotographForumTask().execute();

			}
		});
		pd = CustomProgressDialog.createDialog(getActivity());
		pd.setMessage("正在请求数据");
		task=new PhotographForumTask();task.execute();
		return view;
	}

	@Override
	public void onRefresh() {
		
		page = 1;
		isRefresh=true;
		task=new PhotographForumTask();task.execute();
	}

	@Override
	public void onLoadMore() {
		page++;
		isRefresh=false;
		task=new PhotographForumTask();task.execute();
	}

	class PhotographForumTask extends
			AsyncTask<Void, Void, List<ResponseForumInfo>> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected List<ResponseForumInfo> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return WebserviceUtils.getForumInfo(page);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<ResponseForumInfo> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			mXListView.stopRefresh();
			mXListView.stopLoadMore();
			if (result != null) {
				if (result.size() > 0) {
					if (isRefresh) {
						returnList.clear();
						titleList.clear();
						urlList.clear();
						detailList.clear();
					}
					returnList.addAll(result);
					for (int i = 0; i < result.size(); i++) {
						String title = result.get(i).getCardTitle();
						String picUrl = result.get(i).getCardPersonurl();
						String detail=result.get(i).getCardPersonname();
						if (title != null && !title.equals("")
								&& !title.equals("null")) {
							titleList.add(title);
						} else {
							titleList.add("");
						}

						if (picUrl != null && !picUrl.equals("")
								&& !picUrl.equals("null")) {
							urlList.add(picUrl);
						} else {
							urlList.add("");
						}
						if (detail != null && !detail.equals("")
								&& !detail.equals("null")) {
							detailList.add(detail);
						} else {
							detailList.add("");
						}
					}

					if (adapter == null) {
						adapter = new Comm3Adapter(titleList,detailList, urlList, mContext);
						mXListView.setAdapter(adapter);
					} else {
						adapter.notifyDataSetChanged();
					}
					mXListView
							.setOnItemClickListener(PhotographForumFragment.this);
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
		Intent intent = new Intent();
		intent.setClass(getActivity(), PhotographDetailActivity.class);
		intent.putExtra(CodeConstants.BUSI_ID, returnList.get(position - 1)
				.getCardId());
		intent.putExtra(CodeConstants.MODULE_ID, CodeConstants.PHOTO_FORUM);
//		 Type : 1 推荐 2 论坛 3 赏析 4 影楼 5 摄友
		//intent.putExtra(CodeConstants.TYPE, 2);
		startActivity(intent);

	}
	@Override
	public void onDestroy() {
		if (this.task != null && this.task.getStatus() == Status.RUNNING)
			this.task.cancel(true);
		super.onDestroy();
	}
}
