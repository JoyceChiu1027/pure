package com.bupt.chengde.ui.me;

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

/**
 * 找回密码
 */
public class FindPasswordActivity extends BaseActivity {
    static final String VER_TYPE="FindPwd";
	private EditText phoneEditText/* , yangzhengmaEditText, passwordEditText */;
	private Button /* huoquButton, */chonzhiButton;
	private TextView topTextView;
	private String phoneString;
	//private String codeString;
	// private String newpwdString;
	// private LinearLayout backLayout;
	private ResponseValidate responseValidate;

	// private int time = 30;
	// private CommonBaseInfo response = null;
	// private CustomProgressDialog pd;
	/*
	 * @SuppressLint("HandlerLeak") Handler handler = new Handler() { public
	 * void handleMessage(Message msg) { if (msg.what == -9) {
	 * huoquButton.setText("重新发送(" + time + ")"); } else if (msg.what == -8) {
	 * huoquButton.setText(R.string.huoquyanzhengma);
	 * 
	 * huoquButton.setClickable(true);
	 * 
	 * time = 30; } } };
	 */

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_register);
		initView();
		/*
		 * huoquButton.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { phoneString =
		 * phoneEditText.getText().toString().trim(); if (phoneString == null ||
		 * phoneString.isEmpty() || !Utils.isMobileNO(phoneString)) {
		 * application.showToast("电话号码不存在"); } else {
		 * 
		 * application.showToast("phoneString = " + phoneString);
		 * runHandler();//获取验证码的线程 huoquButton.setClickable(false);
		 * huoquButton.setText("重新发送(" + time + ")"); new Thread(new Runnable()
		 * {
		 * 
		 * @Override public void run() { for (; time > 0; time--) {
		 * handler.sendEmptyMessage(-9); if (time <= 0) { break; } try {
		 * Thread.sleep(1000); } catch (InterruptedException e) {
		 * e.printStackTrace(); } } handler.sendEmptyMessage(-8); } }).start();
		 * } } });
		 * 
		 * chonzhiButton.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { phoneString =
		 * phoneEditText.getText().toString().trim(); //newpwdString =
		 * passwordEditText.getText().toString().trim(); yanzhengString =
		 * yangzhengmaEditText.getText().toString() .trim(); if (phoneString ==
		 * null || phoneString.isEmpty() || newpwdString == null ||
		 * newpwdString.isEmpty() || yanzhengString == null ||
		 * yanzhengString.isEmpty()) { application.showToast("手机号或密码或验证码为空"); }
		 * else if (!yanzhengString.equals(responseValidate.getValidate())) {
		 * application.showToast("验证码输入错误！"); }else {
		 * application.showToast("phoneString = " + phoneString +
		 * ",newpwdString = " + newpwdString + ",yanzhengString = " +
		 * yanzhengString); ResetPwdTask task=new ResetPwdTask();
		 * task.execute(phoneString); } } });
		 */
	}

	private void initView() {
		topTextView = (TextView) findViewById(R.id.top_name);
		phoneEditText = (EditText) findViewById(R.id.num);
		// yangzhengmaEditText = (EditText) findViewById(R.id.yanzhengma);
		// passwordEditText = (EditText) findViewById(R.id.chu_pwd);
		// huoquButton = (Button) findViewById(R.id.huoqu);
		chonzhiButton = (Button) findViewById(R.id.regiser);
		// backLayout = (LinearLayout) findViewById(R.id.top_back);

		findViewById(R.id.top_back).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						finish();
					}
				});

		phoneEditText.setHint(R.string.zhao_num);
		// yangzhengmaEditText.setHint(R.string.yanzhengma);
		topTextView.setText(R.string.zhao_top);
		// huoquButton.setText(R.string.huoquyanzhengma);
		// passwordEditText.setHint(R.string.zhao_mima);
		chonzhiButton.setText(R.string.zhao_btn);
		chonzhiButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				phoneString = phoneEditText.getText().toString().trim();

				if (TextUtils.isEmpty(phoneString)) {
					application.showToast("手机号不能为空");
					return;

				}
				if (!Utils.isMobileNO(phoneString)) {
					application.showToast("手机号码格式错误");
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
		Intent intent = new Intent(this, FindPassword2Activity.class);
		intent.putExtra(CodeConstants.PHONE_NO, phoneString);
//		intent.putExtra(CodeConstants.CODE, responseValidate.getValidate());
		startActivity(intent);
	}
//	class ResetPwdTask extends AsyncTask<String, Void, CommonBaseInfo> {
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			//pd = CustomProgressDialog.createDialog(FindPasswordActivity.this);
//			//pd.setMessage("正在充值密码，请稍后").show();
//		}
//
//		@Override
//		protected CommonBaseInfo doInBackground(String... params) {
//			// TODO Auto-generated method stub
//			try {
//				response = WebserviceUtils.custFindPwd(params[0]);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return response;
//		}
//
//		@Override
//		protected void onPostExecute(CommonBaseInfo result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			pd.dismiss();
//			if (response != null) {
//				if (response.getReturnCode().trim()
//						.equals(CodeConstants.RETURN_SUCCESS)) {
//					application.showToast("重置密码成功，新密码已发送至您的手机，请查收！");
//				} else {
//					application.showToast(response.getReturnMsg());
//				}
//			} else {
//				application.showToast("网络连接异常！");
//			}
//		}
//
//	}
}
