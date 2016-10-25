package com.bupt.chengde.widget;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseAdPoster;
import com.bupt.chengde.ui.home.AdDetailActivity;
import com.bupt.chengde.util.GlideHelper;
import com.bupt.chengde.util.LogUtil;
import com.bupt.chengde.util.Utils;


/**
 * @author wyf
 * @类 :自定义推荐页面listview的头
 * @version 1.0
 */
public class ListViewTop extends FrameLayout {

	private Context mContext;
	private View viewRoot;
	private LinearLayout conLayout;
	private ViewPager viewPager;
	private List<ImageView> list;
	private List<TextView> textViews;
	private List<ResponseAdPoster> mReturnList;
	//private ImageLoader imageLoader;
//	private DisplayImageOptions options;
	private ViewPagerAdapter adapter;
	private Thread mThread;
	private boolean isRun = false;
	private int mTag;
	private static final String TAG = "ListViewTop";
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int what = msg.what;
			setBack(what);
			viewPager.setCurrentItem(what, true);
		}
	};

	public void setBack(int tag) {
		for (int i = 0; i < list.size(); i++) {
			if (tag == i) {
				textViews.get(i).setBackgroundResource(
						R.drawable.indicator_focused);
			} else {
				textViews.get(i).setBackgroundResource(R.drawable.indicator);
			}
		}
	}

	public ListViewTop(Context context) {
		super(context);
		init(context);
	}

	public ListViewTop(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ListViewTop(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	// 初始化viewpager和布局
	private void init(Context context) {
		mContext = context;
		mReturnList = new ArrayList<ResponseAdPoster>();
		viewRoot = LayoutInflater.from(context).inflate(R.layout.topitem, this,
				true);
		viewPager = (ViewPager) viewRoot.findViewById(R.id.viewpagerId);
		conLayout = (LinearLayout) viewRoot.findViewById(R.id.con);// 放置指示icon的布局

		viewPager.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (MotionEvent.ACTION_MOVE == event.getAction()) {
					ViewParent parent = viewPager.getParent();
					parent.requestDisallowInterceptTouchEvent(true);
				}
				return false;
			}
		});
		//imageLoader = ImageLoader.getInstance();
		mThread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (isRun) {
					//long begin=System.currentTimeMillis();
					//Log.d(TAG, "::::::::while(isRun is called)+begin="+begin);
					try {				
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					int curr = 0;
					if (viewPager.getCurrentItem() == (mReturnList.size() - 1)) {
						curr = 0;
					} else {
						curr = viewPager.getCurrentItem() + 1;
					}
					handler.sendEmptyMessage(curr);		
				}
			}
		});
	}

	/**
	 * 
	 * @param tag
	 *            标记：1代表轮播图，2代表广告条
	 * @param returnList
	 *            轮播的图片地址
	 */
	public void initData(int tag, List<ResponseAdPoster> returnList) {
		if (isRun) {
			LogUtil.d(TAG, "initData is called and is run=" + isRun);
			stopScroll();
		}
//		if (tag == 1) {
//			options = new DisplayImageOptions.Builder()
//					.cacheInMemory(true)
//					// .cacheOnDisc(true)
//					// .showStubImage(R.drawable.default_ad_big)
//					.cacheOnDisk(true)
//					.showImageOnLoading(R.drawable.default_ad_big)
//					.showImageForEmptyUri(R.drawable.default_ad_big)
//					.showImageOnFail(R.drawable.default_ad_big).build();
//		} else {
//			options = new DisplayImageOptions.Builder()
//					.cacheInMemory(true)
//					// .cacheOnDisc(true)
//					// .showStubImage(R.drawable.default_ad_little)
//					.cacheOnDisk(true)
//					.showImageOnLoading(R.drawable.default_ad_little)
//					.showImageForEmptyUri(R.drawable.default_ad_little)
//					.showImageOnFail(R.drawable.default_ad_little).build();
//		}
		mTag = tag;
		// mReturnList = returnList;
		mReturnList.clear();
		mReturnList.addAll(returnList);
		initPager();
	}

	/*public void initData(List<String> imgUrls, int tag) {
		mTag = tag;
		this.imgUrls = imgUrls;

	}*/



	// 为pager配置view和数据
	private void initPager() {
		list = new ArrayList<ImageView>();
		textViews = new ArrayList<TextView>();
		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
				Utils.dp2px(14, mContext), Utils.dp2px(14, mContext));
		TextView textView = null;
		conLayout.removeAllViews();
		for (int i = 0; i < mReturnList.size(); i++) {
			textView = new TextView(mContext);
			params1.setMargins(0, 0, 10, 0);
			textView.setLayoutParams(params1);
			if (i == 0) {
				textView.setBackgroundResource(R.drawable.indicator_focused);
			} else {
				textView.setBackgroundResource(R.drawable.indicator);
			}
			conLayout.addView(textView);
			textViews.add(textView);
			conLayout.setVisibility(View.VISIBLE);
		}

		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		@SuppressWarnings("deprecation")
		int width1 = wm.getDefaultDisplay().getWidth();

		LayoutParams params = null;
		if (mTag == 1) {
			params = new LayoutParams(width1, width1 * 7 / 16 + width1 * 7 % 16);
		} else {
			params = new LayoutParams(width1, width1 * 3 / 15 + width1 * 3 % 15);
		}

		for (int i = 0; i < mReturnList.size(); i++) {

			ImageView imageView = new ImageView(mContext);
			imageView.setLayoutParams(params);
			imageView.setScaleType(ScaleType.FIT_XY);
			// imageView.setBackgroundResource(R.drawable.lun4);
			GlideHelper.showImageWithUrl(mContext,mReturnList.get(i).getAdPicUrl(),imageView);
			final int position=i;
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent intent=new Intent();
					intent.putExtra(CodeConstants.URL,mReturnList.get(position).getAdUrl());
					intent.setClass(mContext, AdDetailActivity.class);
					mContext.startActivity(intent);
				}
			});
			list.add(imageView);
		}
		viewPager.setLayoutParams(params);
		adapter = new ViewPagerAdapter();
		viewPager.setAdapter(adapter);

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				setBack(position);
			}

			@Override
			public void onPageScrolled(int position, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int position) {

			}
		});

		if (mReturnList.size() > 1) {
			isRun = true;
			/*Thread thread = new Thread() {

				@Override
				public void run() {
					super.run();
					while (isRun) {
						long begin=System.currentTimeMillis();
						Log.d(TAG, "::::::::while(isRun is called)+begin="+begin);
						try {				
							Thread.sleep(4000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						int curr = 0;
						if (viewPager.getCurrentItem() == (mReturnList.size() - 1)) {
							curr = 0;
						} else {
							curr = viewPager.getCurrentItem() + 1;
						}
						long end=System.currentTimeMillis();
						Log.d(TAG, "::::::::while(isRun is called)+end="+end);
						Log.d(TAG, "end-begin="+(end-begin));
						Log.d(TAG, "curr="+curr+"......viewPager.currentItem"+viewPager.getCurrentItem());
						handler.sendEmptyMessage(curr);
						
					}

				}
			};*/
			try{
				mThread.start();
			}catch(IllegalThreadStateException e ){
				com.bupt.chengde.util.LogUtil.w("Thread.........", "mThread.getState()=========="+mThread.getState());
			}
			
		}

	}

	public void stopScroll() {
		isRun = false;
	}
	class ViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			//LogUtil.e(TAG, "内部类viewpageradapter的instantiateItem is called position="+position);
			ImageView imageView = list.get(position);
			
		/*	imageLoader.displayImage(
			 CodeConstants.PIC_URL_PREFIX+ mReturnList.get(position)
					.getAdPicUrl(), imageView, options);*/
			//GlideHelper.showImageWithUrl(mContext,mReturnList.get(position).getAdPicUrl(),imageView);

			container.addView(list.get(position));
			return list.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			//container.removeView(list.get(position));
			//LogUtil.i(TAG,"destroyItem is called and position="+position);

			container.removeView((View)object);
		}

		@Override
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
		}
		
		@Override
		public void finishUpdate(ViewGroup container) {
			// TODO Auto-generated method stub
			super.finishUpdate(container);
		}
	}

	public ViewPager getViewPager() {
		return viewPager;
	}

	public void notifyDataSetChanged(List<ResponseAdPoster> mDatas) {
		mReturnList.clear();
		mReturnList.addAll(mDatas);
		initPager();
	}

	@Override
	protected void onDetachedFromWindow() {
		LogUtil.w(TAG,"onDetachedFromWindow is called");
		//GlideHelper.stopLoadImg(mContext);
		super.onDetachedFromWindow();
	}
}
