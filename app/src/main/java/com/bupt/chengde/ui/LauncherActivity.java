package com.bupt.chengde.ui;

import cn.jpush.android.api.JPushInterface;

import com.bupt.chengde.R;
import com.bupt.chengde.util.LogUtil;
import com.bupt.chengde.util.SharePreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;

public class LauncherActivity extends BaseActivity {

	private static final String TAG = LauncherActivity.class.getSimpleName();
	private static final int FIRST_USE = 0;
	// private static final int HAS_NEW_PIC = 1;
	private static final int GOTO_MAIN = 2;

	private static final int DELAY_TIME = 2000;
	private Context mContext;
	private float mDensity;
	private int mDensityDpi;
	private int mAvatarSize;
	private int mWidth;
	private int mHeight;
	/*protected Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case FIRST_USE:
				
				break;
			case GOTO_MAIN:
				
				break;
			default:
				break;
			}
		}
	};*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = LauncherActivity.this;
		setContentView(R.layout.activity_launcher);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mDensity = dm.density;
		mDensityDpi = dm.densityDpi;
		mWidth = dm.widthPixels;
		mHeight = dm.heightPixels;
		LogUtil.i(TAG, "mDensity=" + mDensity);
		com.bupt.chengde.util.LogUtil.i(TAG, "mDensityDpi=" + mDensityDpi);
		com.bupt.chengde.util.LogUtil.i(TAG, "mWidth=" + mWidth);
		com.bupt.chengde.util.LogUtil.i(TAG, "mHeight=" + mHeight);

		handler.postDelayed(r, DELAY_TIME);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		com.bupt.chengde.util.LogUtil.v(TAG, "onPause is called");
		JPushInterface.onPause(this);

	}

	Runnable r = new Runnable() {

		@Override
		public void run() {

			if (isFirstUse()) {
				handler.sendEmptyMessage(FIRST_USE);
			} else {
				handler.sendEmptyMessage(GOTO_MAIN);
			}

			//handler.sendEmptyMessage(GOTO_MAIN);
		}
	};
@Override
	protected void beginActivity(Context context, Intent intent,
			Class<? extends Activity> cls) {
		if (intent == null) {
			intent = new Intent();
		}
		if (context == null) {
			context = this;
		}
		intent.setClass(context, cls);
		startActivity(intent);
	}

	@Override
	protected void handleMessageHere(Message msg) {
		switch (msg.what) {
		case FIRST_USE:
			beginActivity(LauncherViewPagerActivity.class);
			finish();
			break;
		case GOTO_MAIN:
			beginActivity(ChengdeMainActivity.class);
			finish();
			break;
		default:
			break;
		}
	}
	private boolean isFirstUse(){
		boolean isFirstUse=SharePreferenceManager.getIsFirstUse();
		if (isFirstUse) {
			SharePreferenceManager.setIsFirstUse(false);
			return true;
		}
		return false;
	}
}
