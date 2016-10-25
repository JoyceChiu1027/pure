package com.bupt.chengde.ui.se;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.MyFragmentAdapter;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.widget.CustomProgressDialog;

/**
 * 
 * @author wyf
 * @类 : 便民
 * @version 1.0
 */
public class BianminActivity extends BaseActivity {

	private TextView[] textViews = new TextView[4];
	private TextView[] tags = new TextView[4];
	private ViewPager bianViewPager;
	private CustomProgressDialog pd;
	
	/**
	 * 用于存储所有的页面
	 */
	private List<Fragment> fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bianmin);

		initView();
		initFragment();
	}

	private void initFragment() {
		fragments = new ArrayList<Fragment>();
		fragments.add(BianminFragment.newInstance(1));
		fragments.add( BianminFragment.newInstance(2));
		fragments.add( BianminFragment.newInstance(3));
		fragments.add( BianminFragment.newInstance(4));
		bianViewPager.setOffscreenPageLimit(4);// 预加载2页
		MyFragmentAdapter adapter = new MyFragmentAdapter(
				getSupportFragmentManager(), fragments);
		bianViewPager.setAdapter(adapter);
		bianViewPager.setOnPageChangeListener(new OnPageChangeListener() {

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
		((TextView) findViewById(R.id.top_name)).setText("便民");
		textViews[0] = (TextView) findViewById(R.id.bian_tx1);
		textViews[1] = (TextView) findViewById(R.id.bian_tx2);
		textViews[2] = (TextView) findViewById(R.id.bian_tx3);
		textViews[3] = (TextView) findViewById(R.id.bian_tx4);
		tags[0] = (TextView) findViewById(R.id.bian_t1);
		tags[1] = (TextView) findViewById(R.id.bian_t2);
		tags[2] = (TextView) findViewById(R.id.bian_t3);
		tags[3] = (TextView) findViewById(R.id.bian_t4);
		bianViewPager = (ViewPager) findViewById(R.id.bian_viewPager);
		for (int i = 0; i < textViews.length; i++) {
			final int tag = i;
			textViews[i].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					bianViewPager.setCurrentItem(tag);
				}
			});
		}
		findViewById(R.id.top_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		/*BianminTask task1 = new BianminTask();
		task1.execute(1);
		BianminTask task2=new BianminTask();
		task2.execute(2);
		BianminTask task3=new BianminTask();
		task3.execute(3);
		BianminTask task4=new BianminTask();
		task4.execute(4);*/
	}

	
}
