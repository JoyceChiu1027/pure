package com.bupt.chengde.ui.se;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.MyFragmentAdapter;
import com.bupt.chengde.control.AdTask;
import com.bupt.chengde.control.AsyncTaskCallback;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseAdPoster;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.ui.home.AdDetailActivity;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.ListViewTop;

public class TravelActivity extends BaseActivity 
//implements AsyncTaskCallback<List<ResponseAdPoster>>
{

//	private ListViewTop mListViewTop;
//	private LinearLayout adConLayout;
//	private AdTask task;
	private Context mContext;
//	private TextView[] textViews = new TextView[5];
//	private TextView[] tags = new TextView[5];
	private TextView[] textViews = new TextView[4];
	private TextView[] tags = new TextView[4];
	private ViewPager travelViewPager;
	private List<Fragment> fragments;
//	private ArrayList<String> adImageUrls = new ArrayList<String>();// 轮播广告图片
//	private ArrayList<String> adTitles = new ArrayList<String>();// 轮播广告图片上的标题，如果有的话，备用
//	private List<ResponseAdPoster> adReturnList = new ArrayList<ResponseAdPoster>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_travel);
		mContext = TravelActivity.this;
		initView();
		initFragment();
		
//		task = new AdTask(1, mContext, adReturnList);
//		task.setCallback(this);
//		task.execute(CodeConstants.TRAVEL);
	}

	private void initView() {
		//adConLayout = (LinearLayout) findViewById(R.id.ad_con_layout);
		((TextView) findViewById(R.id.top_name)).setText("旅游");
		textViews[0] = (TextView) findViewById(R.id.travel_tx1);
		textViews[1] = (TextView) findViewById(R.id.travel_tx2);
		textViews[2] = (TextView) findViewById(R.id.travel_tx3);
		textViews[3] = (TextView) findViewById(R.id.travel_tx4);
//		textViews[4] = (TextView) findViewById(R.id.travel_tx5);
		tags[0] = (TextView) findViewById(R.id.travel_t1);
		tags[1] = (TextView) findViewById(R.id.travel_t2);
		tags[2] = (TextView) findViewById(R.id.travel_t3);
		tags[3] = (TextView) findViewById(R.id.travel_t4);
//		tags[4] = (TextView) findViewById(R.id.travel_t5);
		travelViewPager = (ViewPager) findViewById(R.id.travel_viewPager);

		for (int i = 0; i < textViews.length; i++) {
			final int tag = i;
			textViews[i].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					travelViewPager.setCurrentItem(tag);
				}
			});
		}
		findViewById(R.id.top_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	private void initFragment() {
		fragments = new ArrayList<Fragment>();
		fragments.add(new TravelRecommendFragment());
		fragments.add(new TravelAgencyFragment());
		fragments.add(new TravelTogetherFragment());
		fragments.add(new TravelViewSpotFragment());
//		fragments.add(new TravelNotesFragment());
//		travelViewPager.setOffscreenPageLimit(5);
		travelViewPager.setOffscreenPageLimit(4);
		MyFragmentAdapter adapter = new MyFragmentAdapter(
				getSupportFragmentManager(), fragments);
		travelViewPager.setAdapter(adapter);
		travelViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				for (int i = 0; i < tags.length; i++) {
					if (arg0 == i) {
						tags[i].setBackgroundColor(getResources().getColor(
								R.color.blue));
					} else {
						tags[i].setBackgroundColor(getResources().getColor(
								R.color.back));
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
}
