package com.bupt.chengde.ui.me;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
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

public class Register2Activity extends BaseActivity {
	private TextView topTextView;
	// private LinearLayout backLayout;
	private Button getCodeBtn, submitBtn;
	private TextView noticeTv;
	private EditText codeInput, tuijianrenInput, nicknameInput;
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
		//code = getIntent().getStringExtra(CodeConstants.CODE);
		topTextView = (TextView) findViewById(R.id.top_name);
		topTextView.setText(R.string.zhuce);
		findViewById(R.id.top_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Register2Activity.this.finish();

			}
		});
		noticeTv = (TextView) findViewById(R.id.notice_text);
		noticeTv.setText("您的手机  " + phoneNo + " 将收到一条验证码");
		noticeTv.setVisibility(View.VISIBLE);
		codeInput = (EditText) findViewById(R.id.code_input);
		nicknameInput = (EditText) findViewById(R.id.nickname_input);
		nicknameInput.setVisibility(View.VISIBLE);
		tuijianrenInput = (EditText) findViewById(R.id.tuijianren_phone_input);
		tuijianrenInput.setVisibility(View.VISIBLE);
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
				String tuijianrenPhone = tuijianrenInput.getText().toString()
						.trim();
				if (!TextUtils.isEmpty(tuijianrenPhone)
						&& !Utils.isMobileNO(tuijianrenPhone)) {
					application.showToast("推荐人手机号填写错误");
					return;
				}
				String nickName = nicknameInput.getText().toString().trim();
				String yanzhengma=codeInput.getText().toString().trim();
				new RegisterTask().execute(nickName, phoneNo, tuijianrenPhone,yanzhengma);
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
		responseValidate = WebserviceUtils.getValidate(phoneNo,RegisterActivity.VER_TYPE);
	}

	@Override
	protected void doForeGround() throws Exception {
		if (responseValidate != null) {
			if (responseValidate.getReturnCode().equals(
					CodeConstants.RETURN_SUCCESS)) {
				//code = responseValidate.getValidate();
				new CountDownTimer(30000, 1000) {

					@Override
					public void onTick(long millisUntilFinished) {
						// TODO Auto-generated method stub
						getCodeBtn.setText("重新发送(" + millisUntilFinished / 1000
								+ ")");
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

	class RegisterTask extends AsyncTask<String, Void, CommonBaseInfo> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = CustomProgressDialog.createDialog(Register2Activity.this);
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
					application.showToast("注册成功,密码已发至您的手机！");
					toNextActivity();

				} else {
					if (result.getReturnCode().trim().equals("304")) {
						application.showToast("该手机号已注册，请更换手机号码");
					}else if (result.getReturnCode().trim().equals("888")) {
						application.showToast("验证码失效，请重新获取验证码");
					} else {
						application.showToast(result.getReturnMsg());
					}

				}
			} else {
				application.showToast("网络连接异常！");
			}
		}
	}

	public void toNextActivity() {
		Intent intent = new Intent();
		intent.setClass(Register2Activity.this, LoginActivity.class);
		startActivity(intent);
	}
}
