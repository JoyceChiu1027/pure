package com.bupt.chengde.ui.yyt;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bupt.chengde.R;
import com.bupt.chengde.adapter.TvShowAdapter;
import com.bupt.chengde.adapter.YytAddrInfoAdapter;
import com.bupt.chengde.entity.ResponseBusinessInfo;
import com.bupt.chengde.entity.ResponseYytAddrInfo;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.bupt.chengde.widget.XListView;
import com.bupt.chengde.widget.XListView.IXListViewListener;

/**
 * @author wyf
 * @类 :实体营业厅地址信息
 * @version 1.0
 */
public class YytAddrInfoActivity extends BaseActivity implements IXListViewListener{
	private CustomFrameLayout customFrameLayout;
	private TextView errorTxt;
	private View refresh;// 刷新按钮
	private XListView mXListView;
	private LinearLayout backTextView;
	private TextView nameTextView;
	private CustomProgressDialog pd;
	
	private List<ResponseYytAddrInfo> returnList = new ArrayList<ResponseYytAddrInfo>();//
	private List<ResponseYytAddrInfo> list = null;
	private YytAddrInfoAdapter adapter;
	private int page = 1;
	private boolean isRefresh=false;
	private Context mContext;
	private AddressListTask task;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yyt_addr_info);
		mContext=this;
		initView();
		pd=CustomProgressDialog.createDialog(mContext);
		pd.setMessage("正在请求数据");
		pd.show();
		task=new AddressListTask();
		task.execute(page);
		//runHandler();
	}
	private void initView() {	
		backTextView = (LinearLayout) findViewById(R.id.top_back);
		nameTextView = (TextView) findViewById(R.id.top_name);
		nameTextView.setText("营业厅地址信息");
		
		//mXListView.setEmptyView(findViewById(R.id.empty));
		backTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		mXListView = (XListView)findViewById(R.id.comm_listview);
		mXListView.setPullLoadEnable(true);
		mXListView.setPullRefreshEnable(true);
		mXListView.setXListViewListener(this);
		customFrameLayout = (CustomFrameLayout)findViewById(R.id.web_frame);
		customFrameLayout.setList(new int[] { R.id.comm_listview,
				R.id.common_net_error });
		refresh = findViewById(R.id.error_btn);
		errorTxt = (TextView)findViewById(R.id.error_txt);

		refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				customFrameLayout.show(R.id.comm_listview);
				pd.show();
				task=new AddressListTask();
				task.execute(page);
 			}
		});
		
		
	}
	@Override
	protected void onDestroy() {
		if (task!=null) {
			task.cancel(true);
		}
		super.onDestroy();
		
	}

	
	@Override
	public void onRefresh() {
		page = 1;
		isRefresh=true;
		task=new AddressListTask();
		task.execute(page);
	}

	@Override
	public void onLoadMore() {
		page++;
		isRefresh=false;	
		task=new AddressListTask();
		task.execute(page);
	}
class AddressListTask extends AsyncTask<Integer, Void, List<ResponseYytAddrInfo>>{

	@Override
	protected List<ResponseYytAddrInfo> doInBackground(Integer... params) {
		// TODO Auto-generated method stub
		try {
			return WebserviceUtils.getAddress(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	@Override
	protected void onPostExecute(List<ResponseYytAddrInfo> result) {
		// TODO Auto-generated method stub
		pd.dismiss();
		mXListView.stopRefresh();
		mXListView.stopLoadMore();
		if (null!=result) {
			if (result.size()>0) {
				if (isRefresh) {
					returnList.clear();
				}
				returnList.addAll(result);
				if (adapter==null) {
					adapter = new YytAddrInfoAdapter(activity, returnList);
					mXListView.setAdapter(adapter);
				}else{
					adapter.notifyDataSetChanged();
				}
			
			}else {
				if (page > 1) {
					page--;
					application.showToast(getResources().getString(R.string.server_no_more));
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
				application.showToast(getResources().getString(R.string.server_time_out));
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
}
