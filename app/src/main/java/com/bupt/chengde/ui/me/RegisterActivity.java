package com.bupt.chengde.ui.me;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.CommonBaseInfo;
import com.bupt.chengde.entity.ResponseValidate;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.Utils;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomProgressDialog;

/**
 * 注册页面
 */
public class RegisterActivity extends BaseActivity {
    static final String VER_TYPE="Regist";
	private EditText phoneEditText/*, yangzhengmaEditText, passwordEditText,
			tuijianphoneEditText*/;
	private Button /*huoquButton, */regiserButton;
	private TextView topTextView;
	private LinearLayout backLayout;

	private String phoneString;
	//private String yanzhengString;
	//private String nikeNameString;
	//private String tuijianphoneString;
	private int time = 30;
	private CustomProgressDialog pd;
	private ResponseValidate responseValidate;

	/*@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == -9) {
				huoquButton.setText("重新发送(" + time + ")");
			} else if (msg.what == -8) {
				huoquButton.setText(R.string.huoquyanzhengma);
				huoquButton.setClickable(true);
				time = 30;
			}
		}
	};*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
//		huoquButton.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				phoneString = phoneEditText.getText().toString().trim();
//				if (phoneString == null || phoneString.isEmpty()
//						|| !Utils.isMobileNO(phoneString)) {
//					application.showToast("电话号码不存在");
//				} else {
//					application.showToast("phoneString = " + phoneString);
//					runHandler();// 获取验证码的线程
//					huoquButton.setClickable(false);
//					huoquButton.setText("重新发送(" + time + ")");
//					new Thread(new Runnable() {
//						@Override
//						public void run() {
//							for (; time > 0; time--) {
//								handler.sendEmptyMessage(-9);
//								if (time <= 0) {
//									break;
//								}
//								try {
//									Thread.sleep(1000);
//								} catch (InterruptedException e) {
//									e.printStackTrace();
//								}
//							}
//							handler.sendEmptyMessage(-8);
//						}
//					}).start();
//				}
//			}
//		});

//		regiserButton.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				phoneString = phoneEditText.getText().toString().trim();
//				// pwdString = passwordEditText.getText().toString().trim();
//				yanzhengString = yangzhengmaEditText.getText().toString()
//						.trim();
//				nikeNameString = passwordEditText.getText().toString().trim();
//				tuijianphoneString = tuijianphoneEditText.getText().toString()
//						.trim();
//				if (phoneString == null || phoneString.isEmpty()
//						|| /*
//							 * pwdString == null || pwdString.isEmpty() ||
//							 */yanzhengString == null
//						|| yanzhengString.isEmpty()) {
//					application.showToast("手机号或密码或验证码为空");
//				} else if (!yanzhengString.equals(responseValidate
//						.getValidate())) {
//					application.showToast("验证码输入错误！");
//				} else if (phoneString.length() > 0
//						&& !Utils.isMobileNO(phoneString)) {
//					application.showToast("注册者手机号码错误");
//				} else if (tuijianphoneString.length() > 0
//						&& !Utils.isMobileNO(tuijianphoneString)) {
//					application.showToast("推荐人手机号码错误");
//				} else {
//					/*
//					 * application.showToast("phoneString = " + phoneString +
//					 * ",pwdString = " + pwdString + ",yanzhengString = " +
//					 * yanzhengString);
//					 */
//					Date d = new Date();
//					SimpleDateFormat sd = new SimpleDateFormat(
//							"yyyy-MM-dd HH:mm:ss");
//					String dates = sd.format(d);
//					RegistTask task = new RegistTask();
//					task.execute(nikeNameString, phoneString,
//							tuijianphoneString, dates);
//				}
//			}
//		});
	}

	private void initView() {
		topTextView = (TextView) findViewById(R.id.top_name);
		topTextView.setText(R.string.newzhuce);
		backLayout = (LinearLayout) findViewById(R.id.top_back);
		phoneEditText = (EditText) findViewById(R.id.num);
		//yangzhengmaEditText = (EditText) findViewById(R.id.yanzhengma);
		//passwordEditText = (EditText) findViewById(R.id.chu_pwd);
		//tuijianphoneEditText = (EditText) findViewById(R.id.tuijian_phone);
		//huoquButton = (Button) findViewById(R.id.huoqu);
		regiserButton = (Button) findViewById(R.id.regiser);
		

		backLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		regiserButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				phoneString = phoneEditText.getText().toString().trim();
				if (TextUtils.isEmpty(phoneString)) {
					application.showToast("手机号不能为空");
					return;
					
				}
				if (!Utils.isMobileNO(phoneString)) {
					application.showToast("注册者手机号码错误");
					return;
				}
				runHandler();
			}
		});

	}

	@Override
	protected void doBackGround() throws Exception {
		responseValidate = WebserviceUtils.getValidate(phoneString,VER_TYPE);
	}

	@Override
	protected void doForeGround() throws Exception {
		if (responseValidate != null) {
			if (responseValidate.getReturnCode().trim()
					.equals(CodeConstants.RETURN_SUCCESS)) {
				//application.showToast("验证码已发送至您的手机！");
				toNextActivity();

			} else {
				application.showToast(responseValidate.getReturnMsg());
			}
		} else {
			application.showToast("网络异常！");
		}
	}

	private void toNextActivity() {
		Intent intent = new Intent(this, Register2Activity.class);
		intent.putExtra(CodeConstants.PHONE_NO, phoneString);
		//intent.putExtra(CodeConstants.CODE, responseValidate.getValidate());
		startActivity(intent);
	}

	class RegistTask extends AsyncTask<String, Void, CommonBaseInfo> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = CustomProgressDialog.createDialog(RegisterActivity.this);
			pd.setMessage("正在注册，请稍后").show();
		}

		@Override
		protected CommonBaseInfo doInBackground(String... params) {
			// TODO Auto-generated method stub
			CommonBaseInfo response = null;
			try {
				response = WebserviceUtils.OnlineRegist(params[0], params[1],
						params[2],params[3]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return response;
		}

		@Override
		protected void onPostExecute(CommonBaseInfo result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			if (result != null) {
				if (result.getReturnCode().trim()
						.equals(CodeConstants.RETURN_SUCCESS)) {
					application.showToast("注册成功！");

				} else {
					if (result.getReturnCode().trim().equals("304")) {
						application.showToast("该手机号已注册，请跟换手机号码");
					} else {
						application.showToast(result.getReturnCode());
					}

				}
			} else {
				application.showToast("网络连接异常！");
			}
		}
	}

}
