package com.bupt.chengde.ui;

import com.bupt.chengde.control.AppManager;
import com.bupt.chengde.control.MyApplication;
import com.bupt.chengde.util.GlideHelper;
import com.bupt.chengde.widget.CustomProgressDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

/**
 * @author wyf
 * @类 :activity的基类
 * @version 1.0
 */
public class BaseActivity extends FragmentActivity {
	public FragmentActivity activity;
	public MyApplication application;
	
	protected CustomProgressDialog dialog;
	protected String message;
	protected Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			handleMessageHere(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		application = MyApplication.getMyApplication();
		AppManager.getAppManager().addActivity(this);
	}
	
	@SuppressLint("HandlerLeak") 
	protected void runHandler() {
		dialog = CustomProgressDialog.createDialog(activity);
		dialog.setMessage("正在请求数据，请稍后").show();
		Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				
				if (msg.what==0) {
					dialog.dismiss();
					try {
						doForeGround();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if (msg.what == 1) {
					dialog.dismiss();
					application.showToast(message+",请稍后重试");
				}
				dialog.dismiss();
			}

		};
		Runnable r = new ConnectThread(handler);
		new Thread(r).start();
	}
	
	protected void doForeGround() throws Exception {
	}
	protected void doBackGround() throws Exception {
	}
	
	private class ConnectThread extends Thread {

		Handler h;

		public ConnectThread(Handler h) {
			this.h = h;
		}

		@Override
		public void run() {
			try {
				doBackGround();
				h.sendEmptyMessage(0);
			} catch (Exception e) {
				h.sendEmptyMessage(1);
				handleExceptionOfThread(e);
			}
			
		}
	}
	@SuppressLint("HandlerLeak") 
	protected void runHandler2() {
		dialog = CustomProgressDialog.createDialog(activity);
		dialog.setMessage("正在请求数据，请稍后").show();
		Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				
				if (msg.what==0) {
					dialog.dismiss();
					try {
						doForeGround2();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if (msg.what == 1) {
					dialog.dismiss();
					application.showToast(message+",请稍后重试");
				}
				dialog.dismiss();
			}

		};
		Runnable r = new ConnectThread2(handler);
		new Thread(r).start();
	}
	
	protected void doForeGround2() throws Exception {
	}
	protected void doBackGround2() throws Exception {
	}
	
	private class ConnectThread2 extends Thread {

		Handler h;

		public ConnectThread2(Handler h) {
			this.h = h;
		}

		@Override
		public void run() {
			try {
				doBackGround2();
				h.sendEmptyMessage(0);
			} catch (Exception e) {
				h.sendEmptyMessage(1);
				handleExceptionOfThread(e);
			}
			
		}
	}
	@SuppressLint("HandlerLeak") 
	protected void runHandler3() {
		dialog = CustomProgressDialog.createDialog(activity);
		dialog.setMessage("正在请求数据，请稍后").show();
		Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				
				if (msg.what==0) {
					dialog.dismiss();
					try {
						doForeGround2();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if (msg.what == 1) {
					dialog.dismiss();
					application.showToast(message+",请稍后重试");
				}
				dialog.dismiss();
			}

		};
		Runnable r = new ConnectThread2(handler);
		new Thread(r).start();
	}
	
	protected void doForeGround3() throws Exception {
	}
	protected void doBackGround3() throws Exception {
	}
	
	private class ConnectThread3 extends Thread {

		Handler h;

		public ConnectThread3(Handler h) {
			this.h = h;
		}

		@Override
		public void run() {
			try {
				doBackGround3();
				h.sendEmptyMessage(0);
			} catch (Exception e) {
				h.sendEmptyMessage(1);
				handleExceptionOfThread(e);
			}
			
		}
	}
	private void handleExceptionOfThread(Exception e) {
		String msg = e.getMessage();
		if (msg == null || msg.isEmpty()) {
			msg = e.toString();
		}
		message = msg;
	}
	
	@Override
	protected void onDestroy() {
		if (dialog != null) {
			dialog.dismiss();
		}
		super.onDestroy();

	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		AppManager.getAppManager().finishActivity(activity);
	}
	
	
	/**
	 * 方便Activity跳转
	 * 
	 * @param cls
	 *            要跳转的Activity.class
	 */
	protected void beginActivity(Class<? extends Activity> cls) {
		beginActivity(null, null, cls);
	}

	/**
	 * 方便Activity跳转
	 * 
	 * @param context
	 * @param intent
	 * @param cls
	 */
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
	protected void handleMessageHere(Message msg) {
	}
}
