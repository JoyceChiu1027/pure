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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.Comm2Adapter;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseSuggestScenic;
import com.bupt.chengde.ui.BaseFragment;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
/***************
 * @date:2016-4-13 下午5:12:26
 * @author:litf
 * @version:
 * @return:
 * @throws:
 ***************/
public class TravelViewSpotMoreFragment extends BaseFragment implements
		OnItemClickListener {
	private static final String TAG=TravelViewSpotMoreFragment.class.getSimpleName();
	private PullToRefreshGridView mPullRefreshGridView;
	private GridView mGridView;
	private int page = 1;
	private int areaID;
	private List<String> titleList = new ArrayList<String>();
	private List<String> urlList = new ArrayList<String>();
	private Comm2Adapter adapter;
	private List<ResponseSuggestScenic> returnList = new ArrayList<ResponseSuggestScenic>();
	private View refresh;// 刷新按钮
	private CustomFrameLayout customFrameLayout;
	private TextView errorTxt;
	CustomProgressDialog pd;
    private boolean isRefresh=false;
    private TravelViewSpotChengDeShiTask task;
	public static TravelViewSpotMoreFragment newInstance(int areaID) {
		TravelViewSpotMoreFragment f = new TravelViewSpotMoreFragment();
		Bundle args = new Bundle();
		args.putInt(CodeConstants.TYPE, areaID);
		// （注 : areaID = 1 承德市 2 承德县 3 宽城县 4 兴隆县 5 平泉县 6 滦平县 7 隆化县 8 丰宁县 9 围场县）
		f.setArguments(args);// Arguments:参数
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_comm2, null, false);
		mPullRefreshGridView = (PullToRefreshGridView) view
				.findViewById(R.id.common2_gridView);
		mGridView = mPullRefreshGridView.getRefreshableView();
		customFrameLayout = (CustomFrameLayout) view
				.findViewById(R.id.web_frame);
		customFrameLayout.setList(new int[] { R.id.common2_gridView,
				R.id.common_net_error });
		refresh = view.findViewById(R.id.error_btn);
		errorTxt = (TextView) view.findViewById(R.id.error_txt);

		refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				customFrameLayout.show(R.id.common2_gridView);
				pd.show();
				new TravelViewSpotChengDeShiTask().execute();
			}
		});
		mPullRefreshGridView
				.setOnRefreshListener(new OnRefreshListener2<GridView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<GridView> refreshView) {
						page=1;
						isRefresh=true;		
						task=new TravelViewSpotChengDeShiTask();
						task.execute();
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<GridView> refreshView) {
						page++;
						isRefresh=false;
						task=new TravelViewSpotChengDeShiTask();
						task.execute();
					}

				});

		areaID = getArguments().getInt(CodeConstants.TYPE);
		pd = CustomProgressDialog.createDialog(getActivity());
		pd.setMessage("正在请求数据");
		if (areaID == 1) {
			pd.show();
		}
		task=new TravelViewSpotChengDeShiTask();
		task.execute();
		return view;
	}

	class TravelViewSpotChengDeShiTask extends
			AsyncTask<Void, Void, List<ResponseSuggestScenic>> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd.setCancelable(false);
			customFrameLayout.show(R.id.common2_gridView);
		}

		@Override
		protected List<ResponseSuggestScenic> doInBackground(Void... params) {
			try {
				return WebserviceUtils.getScenicByArea(areaID, page);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<ResponseSuggestScenic> result) {
			super.onPostExecute(result);
			pd.dismiss();
			mPullRefreshGridView.onRefreshComplete();
			if (result != null) {
				if (result.size() > 0) {
					if (isRefresh) {
						returnList.clear();
						titleList.clear();
						urlList.clear();
					}
					returnList.addAll(result);
					for (int i = 0; i < result.size(); i++) {
						String suggestTitle = result.get(i).getScenicName();
						String posterUrl = result.get(i).getScenicAdurl();
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
						adapter = new Comm2Adapter(titleList, urlList,
								getActivity());
						mGridView.setAdapter(adapter);
					} else {
						adapter.notifyDataSetChanged();
					}
					mPullRefreshGridView
							.setOnItemClickListener(TravelViewSpotMoreFragment.this);
				} else {
					if (page > 1) {
						page--;
						if(isAdded())
						activity.application.showToast(getResources().getString(R.string.server_no_more));

					} else {
						if (!isRefresh) {
							customFrameLayout.show(R.id.common_net_error);
							refresh.setVisibility(View.VISIBLE);
							if(isAdded())
							errorTxt.setText(getResources().getString(R.string.server_no_data));
						
						}
					}
				}
			} else {
				if (page > 1) {
					page--;
					if(isAdded())
					activity.application.showToast(getResources().getString(R.string.server_time_out));
				} else {
					if (!isRefresh) {
						customFrameLayout.show(R.id.common_net_error);
						refresh.setVisibility(View.VISIBLE);
						if(isAdded())
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
		intent.putExtra(CodeConstants.BUSI_ID, returnList.get(position)
				.getScenicId());
		intent.putExtra(CodeConstants.MODULE_ID, CodeConstants.TRAVEL_SCENIC);
		startActivity(intent);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if (task!=null) {
			task.cancel(true);
		}
		super.onDestroy();
	}
}