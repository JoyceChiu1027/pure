package com.bupt.chengde.ui.me;

import java.util.concurrent.CountDownLatch;

import com.bupt.chengde.R;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.CommonBaseInfo;
import com.bupt.chengde.entity.ResponseValidate;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.Utils;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomProgressDialog;

public class FindPassword2Activity extends BaseActivity {
	Button getCodeBtn, submitBtn;
	EditText codeInput;
	TextView topTextView;
	private TextView noticeTv;
	private String phoneNo;
	private String code;
	private CustomProgressDialog pd;
	private ResponseValidate responseValidate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register2);
		phoneNo = getIntent().getStringExtra(CodeConstants.PHONE_NO);
//		code = getIntent().getStringExtra(CodeConstants.CODE);
		topTextView = (TextView) findViewById(R.id.top_name);
		topTextView.setText(R.string.zhao_top);
		findViewById(R.id.top_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FindPassword2Activity.this.finish();

			}
		});
		noticeTv = (TextView) findViewById(R.id.notice_text);
		noticeTv.setText("您的手机  " + phoneNo + " 将收到一条验证码");
		noticeTv.setVisibility(View.VISIBLE);
		codeInput = (EditText) findViewById(R.id.code_input);
		getCodeBtn = (Button) findViewById(R.id.get_code);
		getCodeBtn.setText("重新发送验证码");
		getCodeBtn.setClickable(false);

		submitBtn = (Button) findViewById(R.id.next_btn);
		new CountDownTimer(30000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				getCodeBtn.setText("重新发送(" + millisUntilFinished / 1000 + ")");
				getCodeBtn.setClickable(false);
				getCodeBtn.setBackgroundColor(getResources().getColor(
						R.color.unavailable_bg_color));
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				getCodeBtn.setClickable(true);
				getCodeBtn.setText("重新发送验证码");
				getCodeBtn.setBackgroundResource(R.drawable.sub_bg);
			}
		}.start();
		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(codeInput.getText().toString().trim())) {
					application.showToast("请输入验证码");
					return;
				}
//				if (!codeInput.getText().toString().trim().equals(code)) {
//					application.showToast("输入的验证码错误");
//					return;
//				}
				// toNextActivity();
				// runHandler();
				String yanzhengma=codeInput.getText().toString().trim();
				new ResetPwdTask().execute(phoneNo,yanzhengma);
			}
		});
		getCodeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				runHandler();
			}
		});
	}

	@Override
	protected void doBackGround() throws Exception {
		responseValidate=WebserviceUtils.getValidate(phoneNo,FindPasswordActivity.VER_TYPE);
	}

	@Override
	protected void doForeGround() throws Exception {
		if (responseValidate!=null) {
			if (responseValidate.getReturnCode().equals(CodeConstants.RETURN_SUCCESS)) {
//				code=responseValidate.getValidate();
				new CountDownTimer(30000,1000) {
					
					@Override
					public void onTick(long millisUntilFinished) {
						// TODO Auto-generated method stub
						getCodeBtn.setText("重新发送(" + millisUntilFinished / 1000 + ")");
						getCodeBtn.setClickable(false);
						getCodeBtn.setBackgroundColor(getResources().getColor(
								R.color.unavailable_bg_color));
					}
					
					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						getCodeBtn.setClickable(true);
						getCodeBtn.setText("重新发送验证码");
						getCodeBtn.setBackgroundResource(R.drawable.sub_bg);
					}
				}.start();
			}
		}
	}

	class ResetPwdTask extends AsyncTask<String, Void, CommonBaseInfo> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = CustomProgressDialog.createDialog(FindPassword2Activity.this);
			pd.setMessage("正在重置密码，请稍后").show();
			pd.setCancelable(false);
		}

		@Override
		protected CommonBaseInfo doInBackground(String... params) {
			// TODO Auto-generated method stub
			CommonBaseInfo response = null;
			try {
				response = WebserviceUtils.custFindPwd(params[0],params[1]);
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
					application.showToast("重置密码成功，新密码已发送至您的手机，请查收！");
					Intent intent = new Intent();
					intent.setClass(FindPassword2Activity.this,
							LoginActivity.class);
					startActivity(intent);
				} else {
					application.showToast(result.getReturnMsg());
				}
			} else {
				application.showToast("网络连接异常！");
			}
		}
	}

}
