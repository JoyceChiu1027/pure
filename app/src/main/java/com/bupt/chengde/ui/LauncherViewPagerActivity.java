package com.bupt.chengde.ui;

import java.util.ArrayList;
import java.util.List;
import com.bupt.chengde.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class LauncherViewPagerActivity extends BaseActivity implements
OnTouchListener, OnGestureListener {
	public static final String IS_FROM_MAIN_ACTIVITY_INTENT = "is_from_main_activity_intent";
    private static final int LAUNCHER_SIZE=4;
	private ViewPager viewpager = null;
	private List<View> list = null;
	//private ImageView[] img = null;
	private boolean isDetectingGesture = false;
	private GestureDetectorCompat detector;
	private boolean isFromMainActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher_viewpager);
		isFromMainActivity = getIntent().getBooleanExtra(IS_FROM_MAIN_ACTIVITY_INTENT, false);
		initView();
	}

	private void initView() {
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		addViewToList();
		
		viewpager.setAdapter(new ViewPagerAdapter(list));
		//viewpager.setOnPageChangeListener(new ViewPagerPageChangeListener());
		viewpager.setOnTouchListener(this);
		detector = new GestureDetectorCompat(this, this);
	
	}

	@SuppressLint("NewApi")
	private void addViewToList() {
		list = new ArrayList<View>();
		RelativeLayout tempView = (RelativeLayout) getLayoutInflater().inflate(
				R.layout.activity_launcher_viewpager, null);
		tempView.setBackgroundResource(R.drawable.start1);
		list.add(tempView);
		
		tempView = (RelativeLayout) getLayoutInflater().inflate(
				R.layout.activity_launcher_viewpager, null);
		tempView.setBackgroundResource(R.drawable.start2);
		list.add(tempView);
		tempView = (RelativeLayout) getLayoutInflater().inflate(
				R.layout.activity_launcher_viewpager, null);
		tempView.setBackgroundResource(R.drawable.start3);
		list.add(tempView);
		tempView = (RelativeLayout) getLayoutInflater().inflate(
				R.layout.activity_launcher_viewpager, null);
		
		Button jumpButton = new Button(this);
		RelativeLayout.LayoutParams buttonParam = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				(int) getResources().getDimension(R.dimen.jump_btn_height));
		/*RelativeLayout.LayoutParams buttonParam = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);*/
		
		buttonParam.addRule(RelativeLayout.CENTER_IN_PARENT);
//		buttonParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//		buttonParam.setMargins(0, 0, 0, 120);
		jumpButton.setLayoutParams(buttonParam);
//		jumpButton.setTextSize(20);
//		jumpButton.setTextColor(getResources().getColor(
//				R.color.white));
//		jumpButton.setText(getResources().getString(
//				R.string.main_welcome_page_start_now));
		jumpButton.setBackgroundResource(R.drawable.btn_jump);

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
			jumpButton.setPaddingRelative(20, 20, 20, 20);
		}

		jumpButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				beginActivity(ChengdeMainActivity.class);
				finish();
			}
		});
		tempView.setBackgroundResource(R.drawable.start4);
	    tempView.addView(jumpButton);
		list.add(tempView);

	}
	class ViewPagerAdapter extends PagerAdapter {

		private List<View> list = null;

		public ViewPagerAdapter(List<View> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(list.get(position));
			return list.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(list.get(position));
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (isDetectingGesture && velocityX < 0) {
			if (!isFromMainActivity) {
				beginActivity(ChengdeMainActivity.class);
				finish();
			} else {
				finish();
			}
		}
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
}
