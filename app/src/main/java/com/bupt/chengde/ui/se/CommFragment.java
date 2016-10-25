package com.bupt.chengde.ui.se;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
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
import com.bupt.chengde.adapter.CommAdapter;
import com.bupt.chengde.control.AdTask;
import com.bupt.chengde.control.AsyncTaskCallback;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseAdPoster;
import com.bupt.chengde.entity.ResponseNews;
import com.bupt.chengde.ui.BaseFragment;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.bupt.chengde.widget.ListViewTop;
import com.bupt.chengde.widget.XListView;
import com.bupt.chengde.widget.XListView.IXListViewListener;

public class CommFragment extends BaseFragment implements IXListViewListener,OnItemClickListener,
		AsyncTaskCallback<List<ResponseAdPoster>> {
	private static final String TAG = CommFragment.class.getSimpleName();
	private ListViewTop mListViewTop;
	private List<ResponseAdPoster> adreturnList = new ArrayList<ResponseAdPoster>();
	private NewsTask task;
	private AdTask adTask;
	private Context mContext;
	private boolean isRefresh = false;

	private XListView mXListView;

	private CommAdapter adapter;
	private String moduleId;
	private int page = 1;
	private CustomFrameLayout customFrameLayout;
	private TextView errorTxt;
	private View refresh;// 刷新按钮
	CustomProgressDialog pd;
	private List<String> titleList;
	private List<ResponseNews> returnList =new ArrayList<ResponseNews>();
	//private List<ResponseNews> mDatas=new ArrayList<ResponseNews>();

//	public CommFragment(/*String type,*/ String moduleId) {
//		//this.type = type;
//		this.moduleId = moduleId;
//	}
	static CommFragment newInstance(String moduleId){
		Bundle args = new Bundle();
		args.putCharSequence(CodeConstants.MODULE_ID, moduleId);
		CommFragment f = new CommFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle=getArguments();
		this.moduleId=bundle.getString(CodeConstants.MODULE_ID);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_comm, null, false);
		mXListView = (XListView) view.findViewById(R.id.comm_listview);
		mXListView.setPullLoadEnable(true);
		mXListView.setPullRefreshEnable(true);
		mXListView.setXListViewListener(this);

		mContext = getActivity();
		pd = CustomProgressDialog.createDialog(getActivity());
		pd.setMessage("正在请求数据，请稍后");
		if (moduleId.equals(CodeConstants.NEWS_TOP)) {
			mListViewTop = new ListViewTop(getActivity());
			mXListView.addHeaderView(mListViewTop);
		}
		
		customFrameLayout = (CustomFrameLayout) view
				.findViewById(R.id.web_frame);
		customFrameLayout.setList(new int[] { R.id.comm_listview,
				R.id.common_net_error });
		refresh = view.findViewById(R.id.error_btn);
		errorTxt = (TextView) view.findViewById(R.id.error_txt);

		refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pd.show();
				pd.setCancelable(false);
				customFrameLayout.show(R.id.comm_listview);
				if (moduleId.equals(CodeConstants.NEWS_TOP)) {
					adTask = new AdTask(1, mContext, adreturnList);
					adTask.setCallback(CommFragment.this);
					adTask.execute(CodeConstants.NEWS_TOP);
				}
				new NewsTask().execute(moduleId, page);

			}
		});
		pd.setCancelable(false);
		mXListView.setOnItemClickListener(this);
		if (moduleId.equals(CodeConstants.NEWS_TOP)) {
			adTask = new AdTask(1, mContext, adreturnList);
			adTask.setCallback(this);
			adTask.execute(CodeConstants.NEWS_TOP);
			pd.show();
		}
		task=new NewsTask();
		task.execute(moduleId, page);
		return view;
	}

	@Override
	public void onRefresh() {
		page = 1;
		isRefresh=true;
		if (moduleId.equals(CodeConstants.NEWS_TOP)) {
			adTask = new AdTask(1, mContext, adreturnList);
			adTask.setCallback(this);
			adTask.execute(CodeConstants.NEWS_TOP);
		}
		task=new NewsTask();
		task.execute(moduleId, page);
	}

	@Override
	public void onLoadMore() {
		page++;
		isRefresh=false;
		task=new NewsTask();
		task.execute(moduleId, page);
	}

	// 两种
	class NewsTask extends AsyncTask<Object, Void, List<ResponseNews>> {

		@Override
		protected List<ResponseNews> doInBackground(Object... params) {
			// TODO Auto-generated method stub
			// WebserviceUtils.getne

			try {
				
				return WebserviceUtils.getNewsInfo(moduleId, page);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<ResponseNews> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			mXListView.stopRefresh();
			mXListView.stopLoadMore();;
			if (result != null ) {
				if (result.size() > 0) {
					if (page==1) {
						returnList.clear();
					}
					returnList.addAll(result);
					if (adapter == null) {
						adapter = new CommAdapter(returnList, getActivity());
						mXListView.setAdapter(adapter);
					} else {
						adapter.notifyDataSetChanged();
					}
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
//			if (result != null && result.size() > 0) {
//
//				Log.d(TAG, "result.size=" + result.size());
//				returnList.addAll(result);
//				if (adapter == null) {
//					Log.d(TAG, "CommAdapter==NULL IS CALLED");
//					Log.i(TAG, "returnList.size=" + returnList.size());
//					adapter = new CommAdapter(returnList, getActivity());
//					mXListView.setAdapter(adapter);
//				} else {
//					Log.d(TAG,
//							"CommAdapter!=NULL AND notifyDataSetChanged IS CALLED");
//					adapter.notifyDataSetChanged();
//				}
//			} else {
//				if (page > 1) {
//					page--;
//					Toast.makeText(getActivity(), "没有更多", Toast.LENGTH_SHORT)
//							.show();
//				} else {
//					customFrameLayout.show(R.id.common_net_error);
//					errorTxt.setText("网络连接超时，请检查网络设置");
//					refresh.setVisibility(View.VISIBLE);
//				}
//
//			}
		}

	}

	@Override
	public void onCompleted(List<ResponseAdPoster> response) {
		// TODO Auto-generated method stub
		if (moduleId.equals(CodeConstants.NEWS_TOP)) {
			if (isRefresh) {
				mXListView.removeHeaderView(mListViewTop);
				mXListView.addHeaderView(mListViewTop);
			}
			mListViewTop.initData(1, response);
		}
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
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(getActivity(), NewsDetailActivity.class);
		intent.putExtra(CodeConstants.MODULE_ID, moduleId);
		if (moduleId.equals(CodeConstants.NEWS_TOP)) {
			intent.putExtra(CodeConstants.BUSI_ID,
					returnList.get(position - 2).getNewsId());
		} else {
			intent.putExtra(CodeConstants.BUSI_ID,
					returnList.get(position - 1).getNewsId());
		}
		startActivity(intent);
	}

	/*@Override
	protected void doBackGround() throws Exception {
		// TODO Auto-generated method stub
		super.doBackGround();
		returnList=WebserviceUtils.getNewsInfo(moduleId, page);
	}

	@Override
	protected void doForeGround() throws Exception {
		// TODO Auto-generated method stub
		super.doForeGround();
		mXListView.stopRefresh();
		mXListView.stopLoadMore();
		if (returnList != null ) {
			if (returnList.size() > 0) {
				if (page==1) {
					mDatas.clear();			
				}
				mDatas.addAll(returnList);
				if (adapter == null) {
					
					Log.d(TAG, "CommAdapter=null IS CALLED");
					adapter = new CommAdapter(mDatas, getActivity());
					mXListView.setAdapter(adapter);
				} else {
					Log.d(TAG,
							"CommAdapter!=null AND notifyDataSetChanged IS CALLED");
					adapter.notifyDataSetChanged();
				}
			}else {
				customFrameLayout.show(R.id.common_net_error);
				errorTxt.setText("服务器返回无数据，请点击刷新");
				refresh.setVisibility(View.VISIBLE);
			}
			
		} else {
			customFrameLayout.show(R.id.common_net_error);
			errorTxt.setText("服务器返回无数据，请点击刷新");
			refresh.setVisibility(View.VISIBLE);
		}
	}*/

	@Override
	public void onDestroy() {
		if (this.task != null)
			this.task.cancel(true);
		if (this.adTask != null )
			this.adTask.cancel(true);
		super.onDestroy();
	}
}
