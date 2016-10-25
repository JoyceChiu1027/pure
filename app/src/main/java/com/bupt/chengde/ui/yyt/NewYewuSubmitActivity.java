package com.bupt.chengde.ui.yyt;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.ui.BaseActivity;

/**
 * @author wyf
 * @类 :新装业务
 * @version 1.0
 */
public class NewYewuSubmitActivity extends BaseActivity {

	private LinearLayout backTextView;
	private TextView normalTextView,fastTextView,norti,fastti;
	private ViewPager viewPager;
	
	private List<Fragment> fragments;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_yewu_submit);
		initView();
		initFragment();
	}

	private void initFragment() {
		fragments = new ArrayList<Fragment>();
		fragments.add(new ChangGuiyuFragment());
		fragments.add(new FastyuFragment());
		viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				switch (arg0) {
				case 0:
					normalTextView.setTextColor(getResources().getColor(R.color.absolute));
					fastTextView.setTextColor(getResources().getColor(R.color.grey));
					norti.setBackgroundColor(getResources().getColor(R.color.absolute));
					fastti.setBackgroundColor(getResources().getColor(R.color.grey));
					break;
					
				case 1:
					normalTextView.setTextColor(getResources().getColor(R.color.grey));
					fastTextView.setTextColor(getResources().getColor(R.color.absolute));
					norti.setBackgroundColor(getResources().getColor(R.color.grey));
					fastti.setBackgroundColor(getResources().getColor(R.color.absolute));
					break;

				default:
					break;
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
		normalTextView = (TextView) findViewById(R.id.yuyue_normal);
		fastTextView = (TextView) findViewById(R.id.yuyue_fast);
		norti = (TextView) findViewById(R.id.yuyue_ti_normal);
		fastti = (TextView) findViewById(R.id.yuyue_ti_fast);
		viewPager = (ViewPager) findViewById(R.id.yuyue_viewpager);
		backTextView = (LinearLayout) findViewById(R.id.top_back);
		norti.setBackgroundColor(getResources().getColor(R.color.absolute));
		((TextView) findViewById(R.id.top_name)).setText(getIntent().getStringExtra("name"));
		backTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		normalTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				normalTextView.setTextColor(getResources().getColor(R.color.absolute));
				fastTextView.setTextColor(getResources().getColor(R.color.grey));
				norti.setBackgroundColor(getResources().getColor(R.color.absolute));
				fastti.setBackgroundColor(getResources().getColor(R.color.grey));
				viewPager.setCurrentItem(0);
			}
		});
		
		fastTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				normalTextView.setTextColor(getResources().getColor(R.color.grey));
				fastTextView.setTextColor(getResources().getColor(R.color.absolute));
				norti.setBackgroundColor(getResources().getColor(R.color.grey));
				fastti.setBackgroundColor(getResources().getColor(R.color.absolute));
				viewPager.setCurrentItem(1);
			}
		});
	}

	class MyFragmentAdapter extends FragmentPagerAdapter{

		public MyFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}
}