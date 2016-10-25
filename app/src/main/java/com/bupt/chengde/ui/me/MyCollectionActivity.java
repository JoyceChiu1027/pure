package com.bupt.chengde.ui.me;

import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.ui.BaseActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyCollectionActivity extends BaseActivity implements OnClickListener {
	private LinearLayout topBack;
	private TextView topName;
	private LinearLayout[] collectList = new LinearLayout[5];
	private String custId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_collection);
		custId = application.getCustID();
		initViews();
		topName.setText("我的收藏");
		registerListener();
		topBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initViews() {
		topBack = (LinearLayout) findViewById(R.id.top_back);
		topName = (TextView) findViewById(R.id.top_name);
		collectList[0] = (LinearLayout) findViewById(R.id.collect_bianmin);
		collectList[1] = (LinearLayout) findViewById(R.id.collect_photo);
		collectList[2] = (LinearLayout) findViewById(R.id.collect_travel);
		collectList[3] = (LinearLayout) findViewById(R.id.collect_home);
		collectList[4] = (LinearLayout) findViewById(R.id.collect_news);

	}

	private void registerListener() {
		collectList[0].setOnClickListener(this);
		collectList[1].setOnClickListener(this);
		collectList[2].setOnClickListener(this);
		collectList[3].setOnClickListener(this);
		collectList[4].setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		if (!application.isLogin()) {
			Toast.makeText(this, "操作前请先登录!", Toast.LENGTH_SHORT).show();
			intent.setClass(this, LoginActivity.class);
		} else {
			intent.setClass(this, MyCollectionListActivity.class);
			intent.putExtra("custId", custId);
			switch (v.getId()) {
			case R.id.collect_bianmin:
				intent.putExtra(CodeConstants.ME_COLLECTION_BIGMODID, "ben");
				break;
			case R.id.collect_photo:
				intent.putExtra(CodeConstants.ME_COLLECTION_BIGMODID, "photo");
				break;
			case R.id.collect_travel:
				intent.putExtra(CodeConstants.ME_COLLECTION_BIGMODID, "travel");
				break;
			case R.id.collect_home:
				intent.putExtra(CodeConstants.ME_COLLECTION_BIGMODID, "main");
				break;
			case R.id.collect_news:
				intent.putExtra(CodeConstants.ME_COLLECTION_BIGMODID, "news");
				break;

			default:
				break;
			}
		}
		startActivity(intent);
	}

}
