package com.bupt.chengde.ui;


import com.bupt.chengde.R;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author wyf
 * @类 :fragment的基类
 * @version 1.0
 */
public class BaseFragment extends Fragment {

	public BaseActivity activity;
	private CustomProgressDialog dialog;
	protected String message;
	private View refresh;// 刷新按钮
	private CustomFrameLayout customFrameLayout;
	private TextView errorTxt;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (BaseActivity) activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	/*	if (isNetErrorFrameShow()) {
			//customFrameLayout=inflater.inflate(resource, root)
			errorTxt=(TextView) inflater.inflate(R.layout.common_net_error, null).findViewById(R.id.error_txt);
			refresh= inflater.inflate(R.layout.common_net_error, null).findViewById(R.id.error_btn);
		}*/
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@SuppressLint("HandlerLeak") 
	protected void runHandler() {
		dialog = CustomProgressDialog.createDialog(activity);
		dialog.setMessage("正在请求数据，请稍后").show();
		dialog.setCancelable(false);
		Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();
				if (msg.what==0) {
					try {
						doForeGround();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if (msg.what == 1) {
					activity.application.showToast("加载失败！"+message);
				}
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
	
	private void handleExceptionOfThread(Exception e) {
		String msg = e.getMessage();
		if (msg == null || msg.isEmpty()) {
			msg = e.toString();
		}
		message = msg;
	}
	public boolean isNetErrorFrameShow(){
		return false;
	}
}
