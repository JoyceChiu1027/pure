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
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import com.bupt.chengde.R;
import com.bupt.chengde.adapter.Comm2Adapter;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseSuggestScenic;
import com.bupt.chengde.ui.BaseFragment;
import com.bupt.chengde.util.LogUtil;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

public class TravelViewSpotFragment extends BaseFragment implements OnItemClickListener/* ,IXGridViewListener */ {
	private static final String TAG = TravelViewSpotFragment.class.getSimpleName();
	private PullToRefreshGridView mPullRefreshGridView;
	private GridView mGridView;
	// private XGridView mXGridView;
	// private GridViewWithHeaderAndFooter mGridViewWithHeader;
	private int page = 1;
	private List<String> titleList = new ArrayList<String>();
	private List<String> urlList = new ArrayList<String>();
	private Comm2Adapter adapter;
	private List<ResponseSuggestScenic> returnList = new ArrayList<ResponseSuggestScenic>();
	private View refresh;// 刷新按钮
	private CustomFrameLayout customFrameLayout;
	private TextView errorTxt, viewspotMore;

	CustomProgressDialog pd;
	private boolean isRefresh = false;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_comm2, null, false);
		mPullRefreshGridView = (PullToRefreshGridView) view.findViewById(R.id.common2_gridView);
		mGridView = mPullRefreshGridView.getRefreshableView();
		viewspotMore = (TextView) view.findViewById(R.id.travel_viewspot_more);
		customFrameLayout = (CustomFrameLayout) view.findViewById(R.id.web_frame);
		customFrameLayout.setList(new int[] { R.id.common2_gridView, R.id.common_net_error });
		refresh = view.findViewById(R.id.error_btn);
		errorTxt = (TextView) view.findViewById(R.id.error_txt);
		refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				customFrameLayout.show(R.id.common2_gridView);
				pd.show();
				new TravelViewSpotTask().execute();
			}
		});
		viewspotMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), TravelViewSpotActivity.class);
				startActivity(intent);

			}
		});
		mPullRefreshGridView.setOnRefreshListener(new OnRefreshListener2<GridView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
				page = 1;
				isRefresh = true;
				new TravelViewSpotTask().execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
				page++;
				isRefresh = false;
				new TravelViewSpotTask().execute();
			}

		});
		pd = CustomProgressDialog.createDialog(getActivity());
		pd.setMessage("正在请求数据");
		new TravelViewSpotTask().execute();
		return view;

	}

	class TravelViewSpotTask extends AsyncTask<Void, Void, List<ResponseSuggestScenic>> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			customFrameLayout.show(R.id.common2_gridView);
			viewspotMore.setVisibility(View.VISIBLE);
		}

		@Override
		protected List<ResponseSuggestScenic> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return WebserviceUtils.getSuggestScenicInfo(page);// getTestData();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<ResponseSuggestScenic> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			// mXGridView.stopRefresh();
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
						if (suggestTitle != null && !suggestTitle.equals("") && !suggestTitle.equals("null")) {
							titleList.add(suggestTitle);
						} else {
							titleList.add("");
						}
						if (posterUrl != null && !posterUrl.equals("") && !posterUrl.equals("null")) {
							urlList.add(posterUrl);
						} else {
							urlList.add("");
						}

					}

					if (adapter == null) {
						adapter = new Comm2Adapter(titleList, urlList, getActivity());
						mGridView.setAdapter(adapter);

					} else {
						adapter.notifyDataSetChanged();
					}
					viewspotMore.setVisibility(View.VISIBLE);
					mPullRefreshGridView.setOnItemClickListener(TravelViewSpotFragment.this);
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
							viewspotMore.setVisibility(View.GONE);
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
						viewspotMore.setVisibility(View.GONE);
					}
				}
			}
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(getActivity(), TravelDetailActivity.class);
		intent.putExtra(CodeConstants.BUSI_ID, returnList.get(position).getScenicId());
		intent.putExtra(CodeConstants.MODULE_ID, CodeConstants.TRAVEL_SCENIC);
		LogUtil.d(TAG, "position=" + position);
		startActivity(intent);
	}

	// @Override
	// public void onRefresh() {
	// // TODO Auto-generated method stub
	// mDatas.clear();
	// titleList.clear();
	// urlList.clear();
	// page=1;
	// new TravelViewSpotTask().execute();
	// }
	//
	// @Override
	// public void onLoadMore() {
	// // TODO Auto-generated method stub
	// page ++;
	// new TravelViewSpotTask().execute();
	//
	// }

	// private List<ResponseSuggestScenic> getTestData(){
	// List<ResponseSuggestScenic> returnList=new
	// ArrayList<ResponseSuggestScenic>();
	// //String[] titles={"","","","","",""};
	// String title="景点";
	// String
	// imageUrl="http://tpic.home.news.cn/xhForum/xhdisk003/M00/11/2F/wKhJCVQvRQcEAAAAAAAAAAAAAAA590.JPG";
	// for (int i = (page)*20; i <=20*page; i++) {
	// ResponseSuggestScenic response=new ResponseSuggestScenic();
	// response.setScenicName(title+i);
	// response.setScenicAdurl(imageUrl);
	// returnList.add(response);
	// }
	// return returnList;
	// }
	//

}
