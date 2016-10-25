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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.Comm1Adapter;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseStudioInfo;
import com.bupt.chengde.ui.BaseFragment;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.bupt.chengde.widget.XListView;
import com.bupt.chengde.widget.XListView.IXListViewListener;

public class PhotographStudioFragment extends BaseFragment implements IXListViewListener, OnItemClickListener {
	private XListView mXListView;

	private List<String> titleList = new ArrayList<String>();
	private List<String> urlList = new ArrayList<String>();
	private Comm1Adapter adapter;
	private List<ResponseStudioInfo> returnList = new ArrayList<ResponseStudioInfo>();
	private View refresh;// 刷新按钮
	private CustomFrameLayout customFrameLayout;
	private TextView errorTxt;
	private Context mContext;
	private int page = 1;
	CustomProgressDialog pd;
	private boolean isRefresh = false;
	private PhotographStudioTask task;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_comm, null, false);
		mContext = getActivity();
		mXListView = (XListView) view.findViewById(R.id.comm_listview);
		mXListView.setPullLoadEnable(true);
		mXListView.setPullRefreshEnable(true);
		mXListView.setXListViewListener(this);
		customFrameLayout = (CustomFrameLayout) view.findViewById(R.id.web_frame);
		customFrameLayout.setList(new int[] { R.id.comm_listview, R.id.common_net_error });
		refresh = view.findViewById(R.id.error_btn);
		errorTxt = (TextView) view.findViewById(R.id.error_txt);

		refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				customFrameLayout.show(R.id.comm_listview);
				pd.show();
				new PhotographStudioTask().execute();
			}
		});
		pd = CustomProgressDialog.createDialog(getActivity());
		pd.setMessage("正在请求数据");
		task = new PhotographStudioTask();
		task.execute();
		return view;
	}

	@Override
	public void onRefresh() {

		page = 1;
		isRefresh = true;
		task = new PhotographStudioTask();
		task.execute();
	}

	@Override
	public void onLoadMore() {
		page++;
		isRefresh = false;
		task = new PhotographStudioTask();
		task.execute();
	}

	class PhotographStudioTask extends AsyncTask<Void, Void, List<ResponseStudioInfo>> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected List<ResponseStudioInfo> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return WebserviceUtils.getStudioInfo(page);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<ResponseStudioInfo> result) {
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
					}
					returnList.addAll(result);
					for (int i = 0; i < result.size(); i++) {
						String title = result.get(i).getStudioName();
						String picUrl = result.get(i).getStudioUrl();
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
					mXListView.setOnItemClickListener(PhotographStudioFragment.this);
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), PhotographDetailActivity.class);
		intent.putExtra(CodeConstants.BUSI_ID, returnList.get(position - 1).getStudioId() + "");
		intent.putExtra(CodeConstants.MODULE_ID, CodeConstants.PHOTO_STUDIO);
		intent.putExtra(CodeConstants.TYPE, 4);
		startActivity(intent);
	}

	@Override
	public void onDestroy() {
		if (this.task != null && this.task.getStatus() == Status.RUNNING)
			this.task.cancel(true);

		super.onDestroy();
	}
}
