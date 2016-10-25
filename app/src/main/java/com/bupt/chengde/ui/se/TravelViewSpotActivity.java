package com.bupt.chengde.ui.se;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.ui.BaseActivity;
/***************
 * @date:2016-4-13 下午5:12:41
 * @author:litf
 * @version:
 * @return:
 * @throws:
 ***************/
public class TravelViewSpotActivity extends BaseActivity {
	private ViewPager viewPager;
	private List<Fragment> fragmentList;
	private RadioGroup vSpotrGroup;
	private MyPagerAdapter mPagerAdpter;
	private LinearLayout topBack;
	private TextView topName;
	
	private TextView[] textViews = new TextView[9];
	
	/**
     * 获取HorizontalScrollView
     */
    private HorizontalScrollView scrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_travel_view_spot);
		initViews();
		registeListener();
		topName.setText("景点");
		mPagerAdpter = new MyPagerAdapter(getSupportFragmentManager());
		viewPager.setOffscreenPageLimit(9);
		viewPager.setAdapter(mPagerAdpter);
	}

	private void initViews() {
		scrollView = (HorizontalScrollView) findViewById(R.id.con);
		viewPager = (ViewPager) findViewById(R.id.view_spot_pager);
		vSpotrGroup = (RadioGroup) findViewById(R.id.view_spot_rGroup);
		topBack = (LinearLayout) findViewById(R.id.top_back);
		topName = (TextView) findViewById(R.id.top_name);
		textViews[0] = (TextView) findViewById(R.id.travel_text_1);
		textViews[1] = (TextView) findViewById(R.id.travel_text_2);
		textViews[2] = (TextView) findViewById(R.id.travel_text_3);
		textViews[3] = (TextView) findViewById(R.id.travel_text_4);
		textViews[4] = (TextView) findViewById(R.id.travel_text_5);
		textViews[5] = (TextView) findViewById(R.id.travel_text_6);
		textViews[6] = (TextView) findViewById(R.id.travel_text_7);
		textViews[7] = (TextView) findViewById(R.id.travel_text_8);
		textViews[8] = (TextView) findViewById(R.id.travel_text_9);
		
		fragmentList = new ArrayList<Fragment>();
		for (int i = 1; i <= 9; i++) {
			fragmentList.add(TravelViewSpotMoreFragment.newInstance(i));
		}
	}

	private void registeListener() {
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					vSpotrGroup.check(R.id.view_spot_rButton1);
					setCenter(0);
					break;
				case 1:
					setCenter(1);
					vSpotrGroup.check(R.id.view_spot_rButton2);
					break;
				case 2:
					setCenter(2);
					vSpotrGroup.check(R.id.view_spot_rButton3);
					break;
				case 3:
					setCenter(3);
					vSpotrGroup.check(R.id.view_spot_rButton4);
					break;
				case 4:
					setCenter(4);
					vSpotrGroup.check(R.id.view_spot_rButton5);
					break;
				case 5:
					setCenter(5);
					vSpotrGroup.check(R.id.view_spot_rButton6);
					break;
				case 6:
					setCenter(6);
					vSpotrGroup.check(R.id.view_spot_rButton7);
					break;
				case 7:
					setCenter(7);
					vSpotrGroup.check(R.id.view_spot_rButton8);
					break;
				case 8:
					setCenter(8);
					vSpotrGroup.check(R.id.view_spot_rButton9);
					break;
				default:
					break;
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
		vSpotrGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.view_spot_rButton1:
					viewPager.setCurrentItem(0, true);
					setCenter(0);
					break;
				case R.id.view_spot_rButton2:
					viewPager.setCurrentItem(1);
					setCenter(1);
					break;
				case R.id.view_spot_rButton3:
					viewPager.setCurrentItem(2);
					setCenter(2);
					break;
				case R.id.view_spot_rButton4:
					viewPager.setCurrentItem(3);
					setCenter(3);
					break;
				case R.id.view_spot_rButton5:
					viewPager.setCurrentItem(4);
					setCenter(4);
					break;
				case R.id.view_spot_rButton6:
					viewPager.setCurrentItem(5);
					setCenter(5);
					break;
				case R.id.view_spot_rButton7:
					viewPager.setCurrentItem(6);
					setCenter(6);
					break;
				case R.id.view_spot_rButton8:
					viewPager.setCurrentItem(7);
					setCenter(7);
					break;
				case R.id.view_spot_rButton9:
					viewPager.setCurrentItem(8);
					setCenter(8);
					break;
				default:
					break;

				}
			}

		});
		topBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});

	}
	
	public int getScreenWidth(){
		return ((WindowManager) activity
	            .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
	            .getWidth();
	}
	
	
	 /**
     * 设置居中的方法
     *
     * @param index
     */
    public void setCenter(int index) {
        RadioButton childAt = (RadioButton) vSpotrGroup.getChildAt(index);

        int scrollX = (childAt.getLeft() - (getScreenWidth() / 2))
                + (childAt.getWidth() / 2);
        scrollView.smoothScrollTo(scrollX, 0);
        for (int i = 0; i < textViews.length; i++) {
			if (i == index) {
				textViews[i].setBackgroundColor(getResources().getColor(R.color.blue));
			}else {
				textViews[i].setBackgroundColor(getResources().getColor(R.color.transparent));
			}
		}
    }

	private class MyPagerAdapter extends FragmentPagerAdapter{

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragmentList.get(arg0);
		}

		@Override
		public int getCount() {
			return fragmentList == null ? 0 : fragmentList.size();
		}
	}
}
