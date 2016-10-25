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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class PhotographActivity extends BaseActivity 
//implements AsyncTaskCallback<List<ResponseAdPoster>>
{
	private TextView[] textViews = new TextView[5];
	private TextView[] tags = new TextView[5];
	private ViewPager photographViewPager;
	private List<Fragment> fragments;
	private ArrayList<String> adImageUrls = new ArrayList<String>();// 轮播广告图片
	private ArrayList<String> adTitles = new ArrayList<String>();// 轮播广告图片上的标题，如果有的话，备用
	private LinearLayout adConLayout;
	private ListViewTop mListViewTop;
	private List<ResponseAdPoster> adReturnList=new ArrayList<ResponseAdPoster>();
	private AdTask task;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photograph);
		mContext = PhotographActivity.this;
		initView();
		initFragment();
		
//		task = new AdTask(1, mContext, adReturnList);
//		task.setCallback(this);
//		task.execute(CodeConstants.TRAVEL);
	}

	private void initView() {
//		adConLayout=(LinearLayout) findViewById(R.id.ad_con_layout);
		((TextView) findViewById(R.id.top_name)).setText("摄影");
		textViews[0] = (TextView) findViewById(R.id.photograph_tx1);
		textViews[1] = (TextView) findViewById(R.id.photograph_tx2);
		textViews[2] = (TextView) findViewById(R.id.photograph_tx3);
		textViews[3] = (TextView) findViewById(R.id.photograph_tx4);
		textViews[4] = (TextView) findViewById(R.id.photograph_tx5);
		tags[0] = (TextView) findViewById(R.id.photograph_t1);
		tags[1] = (TextView) findViewById(R.id.photograph_t2);
		tags[2] = (TextView) findViewById(R.id.photograph_t3);
		tags[3] = (TextView) findViewById(R.id.photograph_t4);
		tags[4] = (TextView) findViewById(R.id.photograph_t5);
		photographViewPager = (ViewPager) findViewById(R.id.photograph_viewPager);
		for (int i = 0; i < textViews.length; i++) {
			final int tag = i;
			textViews[i].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					photographViewPager.setCurrentItem(tag);
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
		/*
		 * fragments.add(new CommFragment(1)); fragments.add(new
		 * CommFragment(2)); fragments.add(new CommFragment(3));
		 * fragments.add(new CommFragment(4)); fragments.add(new
		 * CommFragment(5));
		 */
		fragments.add(new PhotographRecommendFragment());
		fragments.add(new PhotographForumFragment());
		fragments.add(new PhotographAppreciationFragment());
		fragments.add(new PhotographStudioFragment());
		fragments.add(new PhotographFellowFragment());
		photographViewPager.setOffscreenPageLimit(5);// 预加载3页
		MyFragmentAdapter adapter = new MyFragmentAdapter(
				getSupportFragmentManager(), fragments);
		photographViewPager.setAdapter(adapter);
		photographViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
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

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

//	@Override
//	public void onCompleted(List<ResponseAdPoster> response) {
//		 if (mListViewTop==null) {
//				mListViewTop=new ListViewTop(this);
//				mListViewTop.initData(1, response);
//			}else{
//				mListViewTop.notifyDataSetChanged(response);
//			}
//		    adConLayout.removeAllViews();
//		    adConLayout.addView(mListViewTop);
//		    adConLayout.setVisibility(View.VISIBLE);
//		
//	}
//
//	@Override
//	public void onFailed(List<ResponseAdPoster> response) {
//		adConLayout.removeAllViews();
//		adConLayout.setVisibility(View.GONE);
//	}
//
//	@Override
//	public void onNetError() {
//		adConLayout.removeAllViews();
//		adConLayout.setVisibility(View.GONE);
//	}

}
