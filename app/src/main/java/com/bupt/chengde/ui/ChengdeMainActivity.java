package com.bupt.chengde.ui;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bupt.chengde.R;
import com.bupt.chengde.control.AppManager;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.ui.home.HomeFragment;
import com.bupt.chengde.ui.me.AboutUsActivity;
import com.bupt.chengde.ui.me.MeFragment;
import com.bupt.chengde.ui.me.MyCollectionActivity;
import com.bupt.chengde.ui.me.ProListActivity;
import com.bupt.chengde.ui.me.VersionActivity;
import com.bupt.chengde.ui.me.ZhiNanActivity;
import com.bupt.chengde.ui.se.ServiceFragment;
import com.bupt.chengde.ui.yyt.YytFragment;
import com.bupt.chengde.util.Utils;
//import com.bupt.chengde.widget.SharePopupWindow;
//import com.mob.tools.utils.UIHandler;
//import cn.sharesdk.tencent.qq.QQClientNotExistException;
//import cn.sharesdk.wechat.utils.WechatClientNotExistException;
/**
 * @author wyf
 * @类 :主页面
 * @version 1.0
 */
public class ChengdeMainActivity extends BaseActivity /*implements
		PlatformActionListener, Handler.Callback */{

	private TextView[] textViews = new TextView[4];
	private ImageView[] imageButtons = new ImageView[4];

	private HomeFragment homeFragment = null;
	private MeFragment meFragment = null;
	private YytFragment yytFragment = null;
	private ServiceFragment serviceFragment = null;

	private FragmentManager fragmentManager;

	//SharePopupWindow popupWindow = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ShareSDK.initSDK(this);
		setContentView(R.layout.activity_main);
		initView();

		setDefaultFragment();
	}

	private void setDefaultFragment() {
		fragmentManager = getSupportFragmentManager();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mIndex = (mIndex == 0) ? R.id.re_zhy : mIndex;
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		ShowFragment(transaction, mIndex);
	}

	private void ShowFragment(FragmentTransaction transaction, int tag) {
		hideFragments(transaction);
		switch (tag) {
		case R.id.re_zhy:
			if (homeFragment == null) {
				homeFragment = new HomeFragment();
				transaction.add(R.id.main_con, homeFragment);
			} else {
				transaction.show(homeFragment);
			}
			setTextColor(0);
			imageButtons[0].setBackgroundResource(R.drawable.main_home_icon_selected);
			imageButtons[1].setBackgroundResource(R.drawable.main_business_icon_normal);
			imageButtons[2].setBackgroundResource(R.drawable.main_service_icon_normal);
			imageButtons[3].setBackgroundResource(R.drawable.main_me_icon_normal);
			break;

		case R.id.re_yyt:
			if (yytFragment == null) {
				yytFragment = new YytFragment();
				transaction.add(R.id.main_con, yytFragment);
			} else {
				transaction.show(yytFragment);
			}
			setTextColor(1);
			imageButtons[0].setBackgroundResource(R.drawable.main_home_icon_normal);
			imageButtons[1].setBackgroundResource(R.drawable.main_business_icon_selected);
			imageButtons[2].setBackgroundResource(R.drawable.main_service_icon_normal);
			imageButtons[3].setBackgroundResource(R.drawable.main_me_icon_normal);
			break;

		case R.id.re_se:
			if (serviceFragment == null) {
				serviceFragment = new ServiceFragment();
				transaction.add(R.id.main_con, serviceFragment);
			} else {
				transaction.show(serviceFragment);
			}
			setTextColor(2);
			imageButtons[0].setBackgroundResource(R.drawable.main_home_icon_normal);
			imageButtons[1].setBackgroundResource(R.drawable.main_business_icon_normal);
			imageButtons[2].setBackgroundResource(R.drawable.main_service_icon_selected);
			imageButtons[3].setBackgroundResource(R.drawable.main_me_icon_normal);
			break;

		case R.id.re_me:
			if (meFragment == null) {
				meFragment = new MeFragment();
				transaction.add(R.id.main_con, meFragment);
			} else {
				transaction.show(meFragment);
			}
			setTextColor(3);
			imageButtons[0].setBackgroundResource(R.drawable.main_home_icon_normal);
			imageButtons[1].setBackgroundResource(R.drawable.main_business_icon_normal);
			imageButtons[2].setBackgroundResource(R.drawable.main_service_icon_normal);
			imageButtons[3].setBackgroundResource(R.drawable.main_me_icon_selected);
			break;

		default:
			break;
		}
		transaction.commitAllowingStateLoss();
	}

	@SuppressLint("InflateParams")
	public void click(View view) {
		switch (view.getId()) {
		case R.id.me_jifeng:// 我的 积分商城
//			application.showToast("积分商城");
			application.showToast("此功能稍后上线");
			break;

		case R.id.me_shoucang:// 我的 我的收藏
//			application.showToast("我的收藏");
			Intent intent = new Intent();
			intent.setClass(application, MyCollectionActivity.class);
			startActivity(intent);
			break;

		case R.id.me_tuijian:// 我的 推荐给好友
			if (Utils.isNetworkAvailable(activity)) {
				// showShare();
//				application.showToast("1234");
				//share();
				application.showToast("此功能稍后上线");
			} else {
				application.showToast("网络不可用");
			}
			break;

		case R.id.me_bangding:// 我的绑定智能卡
//			application.showToast("绑定智能卡");
			application.showToast("此功能稍后上线");
			break;

		case R.id.me_choujiang:// 我的 抽奖
//			application.showToast("抽奖");
			application.showToast("此功能稍后上线");
			break;

		case R.id.me_shiyong:// 我的 App使用指南
			startActivity(new Intent(ChengdeMainActivity.this,ZhiNanActivity.class));
			break;

		case R.id.me_wenti:// 我的 常见问题列表
			startActivity(new Intent(ChengdeMainActivity.this,ProListActivity.class));
			break;

		case R.id.me_aboutus:// 我的 关于我们
			startActivity(new Intent(ChengdeMainActivity.this,AboutUsActivity.class));
			break;

		case R.id.me_banben:// 我的 版本信息
			startActivity(new Intent(ChengdeMainActivity.this,VersionActivity.class));
			break;

		default:
			break;
		}
	}

	/**
	 * 分享
	 */
	@SuppressLint("InflateParams")
//	private void share() {
//		popupWindow = new SharePopupWindow(activity);
//		popupWindow.showShareWindow();
//		popupWindow
//				.initShareParams(
//						"分享",
//						"快来下载承德96888",
//						CodeConstants.URL_RES+"/app_logo.png",
//						CodeConstants.URL_RES+"/Chengde.apk");
//		//popupWindow.setPlatformActionListener(this);
//		// 显示窗口 (设置layout在PopupWindow中显示的位置)
//		LayoutInflater factorys = LayoutInflater.from(activity);
//		final View textEntryView = factorys.inflate(
//				R.layout.fragment_me, null);
//		popupWindow.showAtLocation(textEntryView.findViewById(R.id.pa),
//				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//	}


	private void hideFragments(FragmentTransaction transaction) {
		if (homeFragment != null) {
			transaction.hide(homeFragment);
		}
		if (meFragment != null) {
			transaction.hide(meFragment);
		}
		if (serviceFragment != null) {
			transaction.hide(serviceFragment);
		}
		if (yytFragment != null) {
			transaction.hide(yytFragment);
		}
	}

	private void initView() {
		textViews[0] = (TextView) findViewById(R.id.text_zhuy);
		textViews[1] = (TextView) findViewById(R.id.text_yyt);
		textViews[2] = (TextView) findViewById(R.id.text_se);
		textViews[3] = (TextView) findViewById(R.id.text_me);
		imageButtons[0] = (ImageView) findViewById(R.id.rb_zhuy);
		imageButtons[1] = (ImageView) findViewById(R.id.rb_yyt);
		imageButtons[2] = (ImageView) findViewById(R.id.rb_se);
		imageButtons[3] = (ImageView) findViewById(R.id.rb_me);
	}

	public void jianTing(View view) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		mIndex = view.getId();
		ShowFragment(transaction, mIndex);
	}

	private void setTextColor(int index) {
		for (int i = 0; i < textViews.length; i++) {
			if (i == index) {
				textViews[i]
						.setTextColor(getResources().getColor(R.color.blue));
			} else {
				textViews[i].setTextColor(getResources()
						.getColor(R.color.black));
			}
		}
	}

	long waitTime = 2000;
	long touchTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {

			long currentTime = System.currentTimeMillis();
			if ((currentTime - touchTime) >= waitTime) {
				application.showToast("再按一次，退出程序");
				touchTime = currentTime;
			} else {
				AppManager.getAppManager().finishAllActivity();
			}
			return true;
		}
		return false;
	}

	private int mIndex = 0;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// 自己记录fragment的位置,防止activity被系统回收时，fragment错乱的问题
		// super.onSaveInstanceState(outState);
		outState.putInt("index", mIndex);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// super.onRestoreInstanceState(savedInstanceState);
		mIndex = savedInstanceState.getInt("index");
	}

	/**
	 * 分享完成
//	 */
//	@Override
//	public void onComplete(Platform platform, int i,
//			HashMap<String, Object> hashMap) {
//		System.out.println("--------------------完成---------------------");
//		Message msg = new Message();
//		msg.what = 2;
//		msg.arg1 = 1;
//		msg.arg2 = i;
//		msg.obj = platform;
//		UIHandler.sendMessage(msg, this);
//	}

//	/**
//	 * 分享错误
//	 */
//	@Override
//	public void onError(Platform platform, int action, Throwable throwable) {
//		System.out.println("--------------------错误---------------------");
//		Message msg = new Message();
//		msg.what = 1;
//		msg.arg1 = action;
//		msg.obj = throwable;
//		UIHandler.sendMessage(msg, this);
//	}

	/**
	 * 分享取消
	 */
//	@Override
//	public void onCancel(Platform arg0, int arg1) {
//		System.out.println("--------------------取消---------------------");
//		Message msg = new Message();
//		msg.what = 0;
//		UIHandler.sendMessage(msg, this);
//	}

	/**
	 * 分享接受消息
	 */
//	@Override
//	public boolean handleMessage(Message msg) {
//		int what = msg.what;
//		switch (what) {
//		case 0:
//			application.showToast("分享已取消");
//			break;
//		case 1:
//			String failtext;
//			if (msg.obj instanceof WechatClientNotExistException
//					|| msg.obj instanceof QQClientNotExistException
//					|| msg.obj instanceof WechatClientNotExistException) {
//				failtext = "版本过低或未安装客户端";
//			} else if (msg.obj instanceof java.lang.Throwable
//					&& msg.obj.toString() != null
//					&& msg.obj.toString().contains(
//							"prevent duplicate publication")) {
//				failtext = "请稍后发送";
//			} else if (msg.obj.toString().contains("error")) {
//				failtext = "分享失败";
//			} else {
//				failtext = "分享失败";
//			}
//			application.showToast(failtext);
//			break;
//		case 2:
//			application.showToast("分享成功");
//			break;
//		}
//		if (popupWindow != null) {
//			popupWindow.dismiss();
//		}
//		return false;
//	}

}
