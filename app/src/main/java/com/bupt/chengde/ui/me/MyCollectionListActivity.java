package com.bupt.chengde.ui.me;

import java.util.ArrayList;
import java.util.List;
import com.bupt.chengde.R;
import com.bupt.chengde.adapter.Comm1Adapter;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseMyCollection;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.ui.home.HomeListDetailActivity;
import com.bupt.chengde.ui.se.BianminDetailActivity;
import com.bupt.chengde.ui.se.NewsDetailActivity;
import com.bupt.chengde.ui.se.PhotographDetailActivity;
import com.bupt.chengde.ui.se.TravelDetailActivity;
import com.bupt.chengde.util.LogUtil;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.bupt.chengde.widget.XListView;
import com.bupt.chengde.widget.XListView.IXListViewListener;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MyCollectionListActivity extends BaseActivity implements IXListViewListener, OnItemClickListener {
	private static final String TAG=MyCollectionListActivity.class.getSimpleName();
	private XListView mXListView;
	private List<String> titleList = new ArrayList<String>();
	private List<String> urlList = new ArrayList<String>();
	private Comm1Adapter adapter;
	private CustomFrameLayout customFrameLayout;
	private List<ResponseMyCollection> returnList = new ArrayList<ResponseMyCollection>();
	private TextView errorTxt, topTitle;
	private View refresh;// 刷新按钮
	private View topBar, topBack;
//	private int page = 1;// 分页，从1开始,每页15条数据
	private String custId, bigModId;
	CustomProgressDialog pd;
	private int page=1;
	private boolean isRefresh=false;
    private MyCollectionListTask task;
    private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_comm);
		mContext=this;
		custId = application.getCustID();
		bigModId = getIntent().getStringExtra(CodeConstants.ME_COLLECTION_BIGMODID);

		mXListView = (XListView) findViewById(R.id.comm_listview);
		mXListView.setPullLoadEnable(false);
		mXListView.setPullRefreshEnable(true);
		mXListView.setXListViewListener(this);
		customFrameLayout = (CustomFrameLayout) findViewById(R.id.web_frame);
		customFrameLayout.setList(new int[] { R.id.comm_listview, R.id.common_net_error });
		refresh = findViewById(R.id.error_btn);
		topBar = findViewById(R.id.topbar);
		topTitle = (TextView) findViewById(R.id.top_name);
		topBack = findViewById(R.id.top_back);
		topBar.setVisibility(View.VISIBLE);
		topTitle.setText("我的收藏");
		errorTxt = (TextView) findViewById(R.id.error_txt);
		topBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				customFrameLayout.show(R.id.comm_listview);
				pd.show();			
				task=new MyCollectionListTask();
				task.execute();
			}
		});
		pd = CustomProgressDialog.createDialog(this);
		pd.setMessage("正在请求数据");
		pd.show();
		task=new MyCollectionListTask();
		task.execute();
	}

	@Override
	public void onRefresh() {
		page=1;
		isRefresh=true;
		task=new MyCollectionListTask();
		task.execute();

	}

	@Override
	public void onLoadMore() {
		page ++;
		isRefresh=false;
		task=new MyCollectionListTask();
		task.execute();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	class MyCollectionListTask extends AsyncTask<Void, Void, List<ResponseMyCollection>> {
		@Override
		protected List<ResponseMyCollection> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return WebserviceUtils.getMyCollection(custId, bigModId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<ResponseMyCollection> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			mXListView.stopRefresh();
			mXListView.stopLoadMore();
			if (result != null) {
				LogUtil.i("=====", result.size() + "	" + result.toString());
				if (result.size() > 0) {
					if (isRefresh) {
						returnList.clear();
						titleList.clear();
						urlList.clear();
					}
					returnList.addAll(result);
	
					for (int i = 0; i < result.size(); i++) {
						String Title = result.get(i).getBusName();
						String Url = result.get(i).getBusPicUrl();
						if (!TextUtils.isEmpty(Title) && !Title.equals("null")) {
							titleList.add(Title);
						} else {
							titleList.add("");
						}
						if (!TextUtils.isEmpty(Url)&& !Url.equals("null")) {
							urlList.add(Url);
						} else {
							urlList.add("");
						}

					}
					if (adapter == null) {
						adapter = new Comm1Adapter(titleList, urlList, MyCollectionListActivity.this);
						mXListView.setAdapter(adapter);
					} else {
						adapter.notifyDataSetChanged();
					}
					mXListView.setOnItemClickListener(MyCollectionListActivity.this);
				} else {
				
						customFrameLayout.show(R.id.common_net_error);
						refresh.setVisibility(View.GONE);
						errorTxt.setText("服务器无数据返回,请先添加收藏！");
			
				}
			} else {
				customFrameLayout.show(R.id.common_net_error);
				refresh.setVisibility(View.VISIBLE);
				errorTxt.setText(getResources().getString(R.string.server_time_out));
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent();
		intent.putExtra(CodeConstants.BUSI_ID, returnList.get(position-1).getBusId());
		intent.putExtra(CodeConstants.MODULE_ID, returnList.get(position-1).getModType());
		String modType = returnList.get(position-1).getModType();
		if (modType.equals(CodeConstants.MAIN_NEWS) || modType.equals(CodeConstants.MAIN_HOT) || modType.equals(CodeConstants.MAIN_SALES)) {
			intent.setClass(MyCollectionListActivity.this, HomeListDetailActivity.class);
		} else if (modType.equals(CodeConstants.BEN_CLEANER) || modType.equals(CodeConstants.BEN_REMOVER) || modType.equals(CodeConstants.BEN_REPAIR) || modType.equals(CodeConstants.BEN_WEDDING)) {
			intent.setClass(MyCollectionListActivity.this, BianminDetailActivity.class);
		}else if (modType.equals(CodeConstants.NEWS_TOP) || modType.equals(CodeConstants.NEWS_CD)) {
			intent.setClass(MyCollectionListActivity.this, NewsDetailActivity.class);
		}else if (modType.equals(CodeConstants.TRAVEL_SUGGEST) || modType.equals(CodeConstants.TRAVEL_TRAVEL) || modType.equals(CodeConstants.TRAVEL_TOGETHER) || modType.equals(CodeConstants.TRAVEL_SCENIC) || modType.equals(CodeConstants.TRAVEL_NOTES)) {
			intent.setClass(MyCollectionListActivity.this, TravelDetailActivity.class);
		}else if (modType.equals(CodeConstants.PHOTO_SUGGEST) || modType.equals(CodeConstants.PHOTO_FORUM) || modType.equals(CodeConstants.PHOTO_APPRECIATION) || modType.equals(CodeConstants.PHOTO_STUDIO) || modType.equals(CodeConstants.PHOTO_PHOTOGRAPHER)) {
			intent.setClass(MyCollectionListActivity.this, PhotographDetailActivity.class);
		}
		startActivity(intent);
	}
}
