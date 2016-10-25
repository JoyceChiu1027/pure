package com.bupt.chengde.ui.yyt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.ui.BaseActivity;

/**
 * @author wyf
 * @类 :新装业务
 * @version 1.0
 */
public class InstallNewServicesActivity extends BaseActivity {

		private ListView listView;
		private LinearLayout backTextView;
		
		private String[] types = { "机顶盒购买预约", "机顶盒置换预约", "电视新装预约", "家庭组合业务预约" };
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_install_new_services);
			initView();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
					R.layout.item3, R.id.textId, types);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					Intent intent = new Intent();
					intent.setClass(InstallNewServicesActivity.this,
							NewYewuSubmitActivity.class);
//					intent.putExtra("pType", arg2+"");
					intent.putExtra("name", types[arg2]);
					startActivity(intent);
				}
			});
		}

		public void initView() {
			listView = (ListView) findViewById(R.id.newListViewId);
			backTextView = (LinearLayout) findViewById(R.id.top_back);
			((TextView) findViewById(R.id.top_name)).setText("新装业务");
			backTextView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					finish();
				}
			});
		}
	}

