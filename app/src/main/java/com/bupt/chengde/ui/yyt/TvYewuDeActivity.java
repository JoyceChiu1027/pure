package com.bupt.chengde.ui.yyt;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.TvShowAdapter;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseBusinessInfo;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.bupt.chengde.widget.XListView;
import com.bupt.chengde.widget.XListView.IXListViewListener;

/**
 * @author wyf
 * @类 :电视业务的列表显示
 * @version 1.0
 */
public class TvYewuDeActivity extends BaseActivity implements
		IXListViewListener {

	private CustomFrameLayout customFrameLayout;
	private TextView errorTxt;
	private View refresh;// 刷新按钮
	private XListView mXListView;
	
	private TextView nameTextView;
	private LinearLayout backTextView;
	private CustomProgressDialog pd;
	private List<ResponseBusinessInfo> returnList=new ArrayList<ResponseBusinessInfo>();
	//private List<ResponseBusinessInfo>  list;
	private TvShowAdapter adapter;
	private boolean isRefresh=false;
	private String typeId="";
	private int type;
	private int page=1;
	private Context mContext;
	private BusinessListTask task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yewu_det);
		mContext=TvYewuDeActivity.this;
		initView();
		//runHandler();
		pd=CustomProgressDialog.createDialog(mContext);
		pd.setMessage("正在请求数据");
		pd.show();
		task=new BusinessListTask();
		task.execute(type,page);
		mXListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent = new Intent(mContext,
						YewuDingActivity.class);
				//application.showToast("position="+position);
				intent.putExtra("object", returnList.get(position-1));
				intent.putExtra(CodeConstants.TYPE, type);
				startActivity(intent);
			}
		});

	}

	public void initView() {
		
		backTextView = (LinearLayout) findViewById(R.id.top_back);
		nameTextView = (TextView) findViewById(R.id.top_name);
		
		nameTextView.setText(getIntent().getStringExtra("value"));
	
		backTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		type = getIntent().getIntExtra(CodeConstants.TYPE, 0);
		if (type == 1) {
			nameTextView.setText("电视业务");
			typeId=getIntent().getStringExtra(CodeConstants.TYPE_ID);
		} else if (type == 2) {
			nameTextView.setText("宽带业务");
		} else if (type == 3) {
			nameTextView.setText("新装业务");
		}
		mXListView = (XListView) findViewById(R.id.comm_listview);
		mXListView.setPullRefreshEnable(true);
		mXListView.setPullLoadEnable(true);
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
                task=new BusinessListTask();
                task.execute(type,page);
 			}
		});
		
		
	}
	@Override
	public void onRefresh() {
		page=1;
		isRefresh=true;
		task=new BusinessListTask();
		task.execute(type,page);
	}

	@Override
	public void onLoadMore() {
		isRefresh = false;
		page++;
		task=new BusinessListTask();
		task.execute(type,page);
	}

	class BusinessListTask extends
			AsyncTask<Integer, Void, List<ResponseBusinessInfo>> {

		@Override
		protected List<ResponseBusinessInfo> doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			
			try {
				Log.e("BusinessListTask", "doInBackground is called");
				return WebserviceUtils.getBusinessInfo(typeId,type, page);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<ResponseBusinessInfo> result) {
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
						adapter = new TvShowAdapter(returnList, activity);
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
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (task!=null) {
			task.cancel(true);
		}
		super.onDestroy();
	}
}
