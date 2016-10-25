package com.bupt.chengde.ui.se;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.MyFragmentAdapter;
import com.bupt.chengde.control.AdTask;
import com.bupt.chengde.control.AsyncTaskCallback;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseAdPoster;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.widget.ListViewTop;

/**
 * @author wyf
 * @类 :		新闻
 * @version 1.0
 */
public class NewsActivity extends BaseActivity 
//implements AsyncTaskCallback<List<ResponseAdPoster>>
{
    private static final String TAG=NewsActivity.class.getSimpleName();

	private TextView[] textViews=new TextView[2];
	private TextView[] tags = new TextView[2];
	private ViewPager viewPager;
	
	private LinearLayout adConLayout;
	private ListViewTop mListViewTop;
	private List<ResponseAdPoster> returnList=new ArrayList<ResponseAdPoster>();
	private AdTask task;
 
	private Context mContext;
	
	/**
	 * 用于存储所有的页面
	 */
	private List<Fragment> fragments;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		mContext=NewsActivity.this;
		initView();
		initFragment();
		
//		task=new AdTask(1,mContext, returnList);
//		task.setCallback(this);
//		task.execute(CodeConstants.NEWS);
		
	}

	private void initFragment() {
		fragments = new ArrayList<Fragment>();
		fragments.add(CommFragment.newInstance(/*"news_top",*/CodeConstants.NEWS_TOP));
		fragments.add(CommFragment.newInstance(/*"news_cd",*/CodeConstants.NEWS_CD));
		viewPager.setOffscreenPageLimit(2);// 预加载3页
		MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(),fragments);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

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

	private void initView() {
		((TextView) findViewById(R.id.top_name)).setText("新闻");
		adConLayout=(LinearLayout) findViewById(R.id.ad_con_layout);
        textViews[0] = (TextView) findViewById(R.id.news_tx1);
		textViews[1] = (TextView) findViewById(R.id.news_tx2);
		tags[0] = (TextView) findViewById(R.id.news_t1);
		tags[1] = (TextView) findViewById(R.id.news_t2);
		viewPager = (ViewPager) findViewById(R.id.news_viewPager);
		for (int i = 0; i < textViews.length; i++) {
			final int tag = i;
			textViews[i].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					viewPager.setCurrentItem(tag);
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

//	@Override
//	public void onCompleted(List<ResponseAdPoster> response) {
//		// TODO Auto-generated method stub
//	    if (mListViewTop==null) {
//			mListViewTop=new ListViewTop(this);
//			mListViewTop.initData(1, response);
//		}else{
//			mListViewTop.notifyDataSetChanged(response);
//		}
//	    adConLayout.removeAllViews();
//	    adConLayout.addView(mListViewTop);
//	    adConLayout.setVisibility(View.VISIBLE);
//	}
//
//	@Override
//	public void onFailed(List<ResponseAdPoster> response) {
//		// TODO Auto-generated method stub
//		adConLayout.removeAllViews();
//		adConLayout.setVisibility(View.GONE);
//	    Log.d(TAG, "adtask onfailed");
//	
//	}
//
//	@Override
//	public void onNetError() {
//		// TODO Auto-generated method stub
//		adConLayout.removeAllViews();
//		adConLayout.setVisibility(View.GONE);
//		Log.d(TAG, "adtask onNetError");
//	}
	
}
