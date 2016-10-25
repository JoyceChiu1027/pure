package com.bupt.chengde.ui.yyt;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.MacBusinessAdapter;
import com.bupt.chengde.entity.ResponseMacBusiness;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.WebserviceUtils;

/**
 * @author wyf
 * @类 :宽带业务列表
 * @version 1.0
 */
public class MacBusinessActivity extends BaseActivity {

	private ListView listView;
	private List<ResponseMacBusiness> macBusiness,list;
	private TextView nameTextView;
	private LinearLayout backTextView;
	
	private MacBusinessAdapter adapter; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mac_business);
		initView();
		runHandler();
	}

	@Override
	protected void doForeGround() throws Exception {
		macBusiness.addAll(list);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	protected void doBackGround() throws Exception {
		list = WebserviceUtils.getMacBusiness(getIntent().getStringExtra("name"));
	}
	
	public void initView() {
		listView = (ListView) findViewById(R.id.MacListView);
		backTextView = (LinearLayout) findViewById(R.id.top_back);
		nameTextView = (TextView) findViewById(R.id.top_name);
		nameTextView.setText(getIntent().getStringExtra("value"));
		backTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		macBusiness = new ArrayList<ResponseMacBusiness>();
		adapter = new MacBusinessAdapter(activity, macBusiness);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				Intent intent = new Intent(MacBusinessActivity.this, YewuDingActivity.class);
				intent.putExtra("tag", macBusiness.get(arg2).getMbId());
				intent.putExtra("type", "mb");
				startActivity(intent);
			}
		});
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}
}
