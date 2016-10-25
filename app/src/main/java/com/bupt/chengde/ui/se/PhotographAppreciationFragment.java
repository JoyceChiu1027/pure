package com.bupt.chengde.ui.se;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
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
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseAppreciation;
import com.bupt.chengde.ui.BaseFragment;
import com.bupt.chengde.ui.se.PhotographForumFragment.PhotographForumTask;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.bupt.chengde.widget.XListView;
import com.bupt.chengde.widget.XListView.IXListViewListener;

/***************
 * @date:2016-4-12 下午8:08:38
 * @author:litf
 * @version:
 * @return:
 * @throws:
 ***************/
public class PhotographAppreciationFragment extends BaseFragment implements
		IXListViewListener, OnItemClickListener {
	private XListView mXListView;
	private List<String> titleList = new ArrayList<String>();
	// private List<String> detailList = new ArrayList<String>();
	private List<String> picUrlList = new ArrayList<String>();
	private Comm1Adapter adapter;
	private List<ResponseAppreciation> returnList = new ArrayList<ResponseAppreciation>();
	private View refresh;// 刷新按钮
	private CustomFrameLayout customFrameLayout;
	private TextView errorTxt;
	private Context mContext;
	private int page = 1;
	CustomProgressDialog pd;
	private boolean isRefresh = false;
    private PhotographAppreciationTask task;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
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
				new PhotographAppreciationTask().execute();
			}
		});
		pd = CustomProgressDialog.createDialog(getActivity());
		pd.setMessage("正在请求数据");
		task=new PhotographAppreciationTask();task.execute();
		return view;
	}

	@Override
	public void onRefresh() {

		page = 1;
		isRefresh = true;
		task=new PhotographAppreciationTask();task.execute();
	}

	@Override
	public void onLoadMore() {
		page++;
		isRefresh = false;
		task=new PhotographAppreciationTask();
		task.execute();
	}

	class PhotographAppreciationTask extends
			AsyncTask<Void, Void, List<ResponseAppreciation>> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			customFrameLayout.show(R.id.comm_listview);
		}

		@Override
		protected List<ResponseAppreciation> doInBackground(Void... params) {
			try {
				return WebserviceUtils.getAppreciationList(page);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<ResponseAppreciation> result) {
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
						picUrlList.clear();
						// detailList.clear();
					}
					returnList.addAll(result);
					for (int i = 0; i < result.size(); i++) {
						String title = result.get(i).getApreName();
						// String detail = result.get(i).getApreInto();
						String picUrl = result.get(i).getAprePicUrl();
						if (title != null && !title.equals("")
								&& !title.equals("null")) {
							titleList.add(title);
						} else {
							titleList.add("");
						}

						if (picUrl != null && !picUrl.equals("")
								&& !picUrl.equals("null")) {
							picUrlList.add(picUrl);
						} else {
							picUrlList.add("");
						}
					}

					if (adapter == null) {
						adapter = new Comm1Adapter(titleList, picUrlList,
								mContext);
						mXListView.setAdapter(adapter);
					} else {
						adapter.notifyDataSetChanged();
					}
					mXListView
							.setOnItemClickListener(PhotographAppreciationFragment.this);
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
				.getApreId());
		intent.putExtra(CodeConstants.MODULE_ID,
				CodeConstants.PHOTO_APPRECIATION);
		// Type : 1 推荐 2 论坛 3 赏析 4 影楼 5 摄友
		intent.putExtra(CodeConstants.TYPE, 3);
		startActivity(intent);
	}
	@Override
	public void onDestroy() {
		if (this.task != null && this.task.getStatus() == Status.RUNNING)
			this.task.cancel(true);

		super.onDestroy();
	}
}
