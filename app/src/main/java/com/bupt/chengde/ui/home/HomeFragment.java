package com.bupt.chengde.ui.home;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.FloatMath;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.HomeListAdapter;
import com.bupt.chengde.adapter.HomeMainMenuAdapter;
import com.bupt.chengde.adapter.YytGridAdapter;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.HomeBean;
import com.bupt.chengde.entity.ResponseAdPoster;
import com.bupt.chengde.entity.ResponseJson;
import com.bupt.chengde.entity.ResponseMainMenu;
import com.bupt.chengde.ui.BaseFragment;
import com.bupt.chengde.ui.se.BianminActivity;
import com.bupt.chengde.ui.se.JiemuYugaoActivity;
import com.bupt.chengde.ui.se.NewsActivity;
import com.bupt.chengde.ui.se.PhotographActivity;

import com.bupt.chengde.ui.se.TravelActivity;
import com.bupt.chengde.ui.yyt.GuZhangActivity;
import com.bupt.chengde.ui.yyt.InvestigationActivity;
import com.bupt.chengde.ui.yyt.SalesActiveActivity;
import com.bupt.chengde.ui.yyt.TvYewuActivity;
import com.bupt.chengde.ui.yyt.TvYewuDeActivity;
import com.bupt.chengde.ui.yyt.YytAddrInfoActivity;
import com.bupt.chengde.util.SharePreferenceManager;
import com.bupt.chengde.util.WebserviceUtils;

import com.bupt.chengde.widget.CustomProgressDialog;
import com.bupt.chengde.widget.ListViewTop;
import com.bupt.chengde.widget.NoScrollGridView;
import com.bupt.chengde.widget.XListView;
import com.bupt.chengde.widget.XListView.IXListViewListener;

/**
 * @author wyf
 * @类 :主页
 * @version 1.0
 */
public class HomeFragment extends BaseFragment implements IXListViewListener {
	private static final String TAG = "HomeFragment";
	private View view, topView;
	private XListView listView;
	private NoScrollGridView gridView;

	private ListViewTop listViewTop;
	private YytGridAdapter adapter;
	private HomeListAdapter homeListAdapter;

	// private CustomFrameLayout customFrameLayout;
	private LinearLayout errorLayout;
	private TextView errorTxt;
	private View refresh;// 刷新按钮
	CustomProgressDialog pd;

	private ResponseJson responseJson = null;
	private List<ResponseMainMenu> mainMenuList = new ArrayList<ResponseMainMenu>();
	private List<ResponseAdPoster> ladPosterList = new ArrayList<ResponseAdPoster>();
	private List<ResponseAdPoster> sadPosterList= new ArrayList<ResponseAdPoster>();
	private List<String> itemList, nameList;
	private List<HomeBean> homeBeans;
	private List<List<HomeBean>> hLists;
	private List<Integer> idList, idList2;
	private List<String> adUrls = new ArrayList<String>();

	//private int loadingType = 0;
    private boolean isRefresh=false;
    private boolean isCached=false;
	private static final int[] menuRes={ R.drawable.business_tv_icon,R.drawable.business_mac_icon,R.drawable.business_new_icon,R.drawable.business_hui_icon,R.drawable.business_fix_icon,R.drawable.business_addr_icon,R.drawable.business_survey_icon, 
	           R.drawable.service_news_icon,R.drawable.service_conv_icon,R.drawable.service_travel_icon,R.drawable.service_photo_icon,R.drawable.service_preview_icon};
	private static final Class<?>[] clazzs = { TvYewuActivity.class,
		   TvYewuDeActivity.class, TvYewuDeActivity.class,SalesActiveActivity.class,
		   GuZhangActivity.class, YytAddrInfoActivity.class,	   
		   InvestigationActivity.class, NewsActivity.class,
		   BianminActivity.class,TravelActivity.class,PhotographActivity.class,JiemuYugaoActivity.class };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_home, container, false);
		listView = (XListView) view.findViewById(R.id.home_listviewId);
		listView.setPullLoadEnable(false);
		listView.setPullRefreshEnable(true);
		listView.setXListViewListener(this);

		pd = CustomProgressDialog.createDialog(getActivity());
		pd.setMessage("正在请求数据");
		listViewTop = new ListViewTop(getActivity());
		listView.addHeaderView(listViewTop);   
		
		topView = LayoutInflater.from(getActivity()).inflate(
				R.layout.home_list_top, null, false);
		gridView = (NoScrollGridView) topView
				.findViewById(R.id.home_gridViewId);
		listView.addHeaderView(topView);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), mainMenuList.get(position)
						.getClazz());
				if (mainMenuList.get(position).getMainMenuId()==1||mainMenuList.get(position).getMainMenuId()==2||mainMenuList.get(position).getMainMenuId()==3||mainMenuList.get(position).getMainMenuId()==4) {
					intent.putExtra(CodeConstants.TYPE, mainMenuList.get(position).getMainMenuId());
				}
				startActivity(intent);
			}
		});
		
		errorLayout = (LinearLayout) view
				.findViewById(R.id.common_error_layout);
		refresh = view.findViewById(R.id.error_btn);
		errorTxt = (TextView) view.findViewById(R.id.error_txt);
		errorLayout.setVisibility(View.GONE);
		refresh = view.findViewById(R.id.error_btn);
		errorTxt = (TextView) view.findViewById(R.id.error_txt);
		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				errorLayout.setVisibility(View.GONE);
				pd.setCancelable(false);
				pd.show();
				new HomePageTask().execute();

			}
		});
		String json=SharePreferenceManager.getCachedHomepageData();
		if (!TextUtils.isEmpty(json)&&!json.equals("null")) {
			 try {
			    isCached=true;
				jieXi(json);
				Log.e(TAG, "JSON="+json);
				
				//hasCachedData=true;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			pd.setCancelable(false);
			pd.setMessage("首次加载请稍后");
			pd.show();
			//new HomePageTask().execute();
		}
		//onRefresh();
		new HomePageTask().execute();
		return view;
	}


	@Override
	public void onRefresh() {
		isRefresh=true;
        new HomePageTask().execute();
	}

	@Override
	public void onLoadMore() {

	}

	@Override
	public void onDestroy() {
		listViewTop.stopScroll();
		super.onDestroy();
	}

	class HomePageTask extends AsyncTask<Void, Void, ResponseJson> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();		
			listView.setVisibility(View.VISIBLE);
			errorLayout.setVisibility(View.GONE);

		}

		@Override
		protected ResponseJson doInBackground(Void... params) {
			try {
				responseJson = WebserviceUtils.getMainPageInfo();
			} catch (Exception e) {

				e.printStackTrace();
			}

			Log.e("HomePageTask", "doInBackground is called");
			
			return responseJson;
		}

		@Override
		protected void onPostExecute(ResponseJson result) {
			Log.i("HomePageTask", "onPostExecute is called");
			pd.dismiss();
			listView.stopRefresh();
			if (responseJson != null &&!TextUtils.isEmpty(responseJson.getJson())&&!responseJson.getJson().equals("null")) {
				try {
					jieXi(responseJson.getJson());
					SharePreferenceManager.setCachedHomepageData(responseJson.getJson());
				
				} catch (JSONException e) {

					e.printStackTrace();
					Log.e(TAG, "发生json异常 message=" + e.getMessage());
					listView.setVisibility(View.GONE);
					errorLayout.setVisibility(View.VISIBLE);
					refresh.setVisibility(View.VISIBLE);
					errorTxt.setText("抱歉！数据出现异常！");
				}
			} else {
				String json=SharePreferenceManager.getCachedHomepageData();
                if (TextUtils.isEmpty(json)) {
                	listView.setVisibility(View.GONE);
    				errorLayout.setVisibility(View.VISIBLE);
    				refresh.setVisibility(View.VISIBLE);
    				errorTxt.setText("抱歉！网络连接超时\n请检查您的网络配置！");
    				return;
				}
				try {
					jieXi(json);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e(TAG, "发生json异常 message=" + e.getMessage());
					listView.setVisibility(View.GONE);
					errorLayout.setVisibility(View.VISIBLE);
					refresh.setVisibility(View.VISIBLE);
					errorTxt.setText("抱歉！数据出现异常！");
				}

			}
		}

	}

	private void jieXi(String jsonStr) throws JSONException {
		Log.d(TAG, "jiexi is called");
		JSONObject object = new JSONObject(jsonStr);
		JSONArray ladArray = object.getJSONArray("rslap");
		JSONArray sadArray=object.getJSONArray("rssap");
		JSONArray menuArray = object.getJSONArray("rsmm");
		JSONArray newsArray = object.getJSONArray("rsns");
		JSONArray hotsArray = object.getJSONArray("rshs");
		JSONArray salesArray = object.getJSONArray("rsss");
		
		int ladLength = ladArray.length();
		int sadLength = sadArray.length();
		int menuLength = menuArray.length();
		int newsLength = newsArray.length();
		int hotsLength = hotsArray.length();
		int salesLength = salesArray.length();
		Log.e(TAG, "ladLength= " + ladLength);
		Log.e(TAG, "sadLength= " + menuLength);
		Log.e(TAG, "menuLength= " + menuLength);
		Log.e(TAG, "newsLength=" + newsLength);
		Log.e(TAG, "hotsLength= " + hotsLength);
		Log.e(TAG, "salesLength=" + salesLength);
		if (ladLength <= 0 &&sadLength<=0&& menuLength <= 0 && newsLength <= 0
				&& hotsLength <= 0 && salesLength <= 0) {
			pd.dismiss();
			/*if (isCached) {
				//listView.setVisibility(View.VISIBLE);
				//return;
			}else{*/
				Toast.makeText(getActivity(), "无数据", Toast.LENGTH_SHORT).show();
				listView.setVisibility(View.GONE);
				errorLayout.setVisibility(View.VISIBLE);
				refresh.setVisibility(View.VISIBLE);
				errorTxt.setText("服务器无数据返回");
				return;
			/*}*/			
		}
		
		getLAdData(ladArray);
		getSAdData(sadArray);
		getMenuData(menuArray);
		// 把后三条数据统一放入homebean中便于显示
		if (hLists == null) {
			hLists = new ArrayList<List<HomeBean>>();
		} else {
			hLists.clear();
		}
		if (nameList == null) {
			nameList = new ArrayList<String>();
		} else {
			nameList.clear();

		}
		if (newsLength > 0) {
			hLists.add(getNewsData(newsArray, 1, newsLength));
			nameList.add("推荐");
		}
		if (salesLength > 0) {
			hLists.add(getNewsData(salesArray, 2, salesLength));
			nameList.add("每日惠");
		}
		if (hotsLength > 0) {
			hLists.add(getNewsData(hotsArray, 3, hotsLength));
			nameList.add("热门活动");
		}
		Log.e(TAG, "hLists.size()=" + hLists.size());
		Log.e(TAG, "nameList.size()=" + nameList.size());
		
/*
		if (loadingType == 0) {
			homeListAdapter = new HomeListAdapter(activity, hLists, nameList,
					sadPosterList);
			listView.setAdapter(homeListAdapter);
			Log.i(TAG, "loadingType==0 ");
		} else {
			adapter.notifyDataSetChanged();
		}*/
		/*if (isRefresh) {
			if (homeListAdapter==null) {
				homeListAdapter = new HomeListAdapter(activity, hLists, nameList,
						sadPosterList);
				listView.setAdapter(homeListAdapter);
				Log.i(TAG, "isrefresh="+isRefresh);
			}else{
				homeListAdapter.notifyDataSetChanged();
			}
		}*/
		homeListAdapter = new HomeListAdapter(activity, hLists, nameList,
				sadPosterList);
		listView.setAdapter(homeListAdapter);
		Log.i(TAG, "isrefresh="+isRefresh);
	}

	private void getLAdData(JSONArray adArray) throws JSONException {
		Log.e(TAG, "getAdData is called");
		if (adArray.length()>0) {
			ladPosterList.clear();
		}
		List<ResponseAdPoster> list = new ArrayList<ResponseAdPoster>();
		for (int i = 0; i < adArray.length(); i++) {
			ResponseAdPoster response = new ResponseAdPoster();
			JSONObject adObject = adArray.getJSONObject(i);
			response.setAdId(adObject.getInt("adId"));
			response.setAdPicUrl(adObject.getString("adPicUrl"));
			response.setAdUrl(adObject.getString("adUrl"));
			list.add(response);
			adUrls.add(response.getAdPicUrl());
		}

		if (list.size() > 0) {
			ladPosterList.addAll(list);
			Log.i(TAG, "listViewTop==null is "+(listViewTop==null));
			Log.i(TAG, "isRefresh="+isRefresh);
			if (isRefresh) {
				listView.removeHeaderView(listViewTop);
				listView.addHeaderView(listViewTop);				
			}			
			listViewTop.initData(1, ladPosterList);
			//listViewTop.stopScroll();
		/*	if (loadingType==0) {
				
			}
			if (listViewTop!=null) {
				listView.removeHeaderView(listViewTop);
			}else{
				listViewTop=new ListViewTop(activity);
			}
			listView.addHeaderView(listViewTop);		
			listViewTop.initData(1, ladPosterList);*/
		/*	if (loadingType==0) {*/
			    
			    //listView.removeHeaderView(listViewTop);
			  
			   // listViewTop=new ListViewTop(activity);
			    //listView.addHeaderView(listViewTop);
				
			/*}else{
				Log.d(TAG, "loadingType="+loadingType);
				listViewTop.notifyDataSetChanged(list);
			}*/
			
		}

	}
	private void getSAdData(JSONArray adArray) throws JSONException {
		Log.e(TAG, "getSAdData is called");
		if (adArray.length()>0) {
			sadPosterList.clear();
		}
		for (int i = 0; i < adArray.length(); i++) {
			ResponseAdPoster response = new ResponseAdPoster();
			JSONObject adObject = adArray.getJSONObject(i);
			response.setAdId(adObject.getInt("adId"));
			response.setAdPicUrl(adObject.getString("adPicUrl"));
			response.setAdUrl(adObject.getString("adUrl"));
			sadPosterList.add(response);
		}		
	}
	private void getMenuData(JSONArray menuArray) throws JSONException {
		mainMenuList.clear();
		//Log.e(TAG, "getMenuData is called and loadingType=" + loadingType);
		// List<ResponseMainMenu> datas=new ArrayList<ResponseMainMenu>();
		itemList = new ArrayList<String>();
		idList = new ArrayList<Integer>();
		for (int i = 0; i < menuArray.length(); i++) {
			ResponseMainMenu response = new ResponseMainMenu();
			JSONObject menuObject = menuArray.getJSONObject(i);
			response.setMainMenuId(menuObject.getInt("mainMenuId"));
			response.setMainMenuName(menuObject.getString("mainMenuName"));
			response.setClazz(clazzs[response.getMainMenuId()-1]);
			response.setResId(menuRes[response.getMainMenuId()-1]);
			mainMenuList.add(response);
		}

		if (mainMenuList.size() > 0) {
			for (int i = 0; i < mainMenuList.size(); i++) {
				itemList.add(mainMenuList.get(i).getMainMenuName());
				idList.add(mainMenuList.get(i).getResId());
			}
			for (int i = 0; i < mainMenuList.size(); i++) {
				Log.i(TAG, "itemList.get(" + i + ")=" + itemList.get(i));
				Log.e(TAG, "idList.get(" + i + ")=" + idList.get(i));
			}
	       if (isRefresh) {
	    	  listView.removeHeaderView(topView);
	    	  listView.addHeaderView(topView);	 
		   }
	      
	       gridView.setAdapter(new YytGridAdapter(itemList, idList, activity));

		}

	}
 	private List<HomeBean> getNewsData(JSONArray jsonArray, int type, int length)
			throws JSONException {

		List<HomeBean> homeBeans = new ArrayList<HomeBean>();
		HomeBean bean = null;
	
		if (type == 1) {
			for (int j = 0; j < length; j++) {

				JSONObject jsonObj = jsonArray.getJSONObject(j);
				bean = new HomeBean();
				bean.setId(jsonObj.getInt("newsId"));
				bean.setTitle(jsonObj.getString("newsName"));
				bean.setTime(jsonObj.getString("newsDate"));
				bean.setImgUrl(jsonObj.getString("newsPosterUrl"));
				bean.setDetail(jsonObj.getString("content"));
				bean.setType(type);
				homeBeans.add(bean);
			}
		} else if (type == 2) {
			for (int j = 0; j < length; j++) {
				JSONObject jsonObj = jsonArray.getJSONObject(j);
				bean = new HomeBean();
				bean.setId(jsonObj.getInt("saleId"));
				bean.setTitle(jsonObj.getString("saleTitle"));
				bean.setTime(jsonObj.getString("saleDate"));
				bean.setImgUrl(jsonObj.getString("salePosterUrl"));
				bean.setDetail(jsonObj.getString("content"));
				bean.setType(type);
				homeBeans.add(bean);
			}
		}else if (type == 3) {
			for (int j = 0; j < length; j++) {

				JSONObject jsonObj = jsonArray.getJSONObject(j);
				bean = new HomeBean();
				bean.setId(jsonObj.getInt("hotId"));
				bean.setTitle(jsonObj.getString("hotTitle"));
				bean.setTime(jsonObj.getString("hotDate"));
				bean.setImgUrl(jsonObj.getString("hotPosterUrl"));
				bean.setDetail(jsonObj.getString("content"));
				bean.setType(type);
				homeBeans.add(bean);
			}
		} 
		return homeBeans;
	}
}
