package com.bupt.chengde.ui.se;

import java.util.ArrayList;
import java.util.List;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.LiveAdapter;
import com.bupt.chengde.entity.ResponseChannelId;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.WebserviceUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 二级频道列表
 * @author wyf
 *
 */
public class JiemuTwoActivity extends BaseActivity {

	private ListView listView;
	private List<ResponseChannelId> datas = new ArrayList<ResponseChannelId>();
	private List<ResponseChannelId> list;
	
	private LiveAdapter liveAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jiemu_yugao);
		initView();
		runHandler();
	}

	private ResponseChannelId responseChannelId = null;
	
	@Override
	protected void doBackGround() throws Exception {
		list = WebserviceUtils.getAllTwoChannel(responseChannelId.getChannelName());
	}
	
	@Override
	protected void doForeGround() throws Exception {
		datas.addAll(list);
		liveAdapter.notifyDataSetChanged();
	}
	
	private void initView() {
		responseChannelId = (ResponseChannelId) getIntent().getSerializableExtra("object");
		((TextView) findViewById(R.id.top_name)).setText(responseChannelId.getChannelName());
		findViewById(R.id.top_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		listView = (ListView) findViewById(R.id.jiemu_list);

		// 初始化一级频道列表
		liveAdapter = new LiveAdapter(JiemuTwoActivity.this, datas);
		listView.setAdapter(liveAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(JiemuTwoActivity.this,JiemuThreeActivity.class);
				intent.putExtra("object", datas.get(position));
				startActivity(intent);
			}
		});
	}
	
}
