package com.bupt.chengde.ui.me;

import java.util.Set;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseCustLoginInfo;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.MD5Utils;
import com.bupt.chengde.util.Utils;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomProgressDialog;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity {
	private static final String TAG = LoginActivity.class.getSimpleName();
	private EditText accountEditText, pwdEditText;
	private Button loginButton;

	private String account, pwdString;
	private ResponseCustLoginInfo response;

	private CustomProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();

	}

	private void initView() {
		accountEditText = (EditText) findViewById(R.id.account);
		pwdEditText = (EditText) findViewById(R.id.account_pwd);
		loginButton = (Button) findViewById(R.id.loginBtn);
		dialog = CustomProgressDialog.createDialog(activity);
		dialog.setMessage("正在请求数据，请稍后");
		loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				account = accountEditText.getText().toString().trim();
				pwdString = pwdEditText.getText().toString().trim();
				if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwdString)) {
					application.showToast("用户名或密码为空！");
					return;
				} else if (!Utils.isMobileNO(account)) {
					application.showToast("输入的号码有误！");
					return;
				}
				dialog.show();
				LoginTask task = new LoginTask();
				task.execute(account, pwdString);
			}
		});
		findViewById(R.id.wangjimima).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(LoginActivity.this,
								FindPasswordActivity.class);
						startActivity(intent);
					}
				});
		findViewById(R.id.zhuce).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
			}
		});
	}

	class LoginTask extends AsyncTask<String, Void, ResponseCustLoginInfo> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected ResponseCustLoginInfo doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				response = WebserviceUtils.getCustLoginInfo(params[0],
						params[1]);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			return response;
		}

		@Override
		protected void onPostExecute(ResponseCustLoginInfo result) {
			dialog.dismiss();
			// System.out.println(response.toString());
			if (response != null) {
				if (response.getReturnCode().trim()
						.equals(CodeConstants.RETURN_SUCCESS)) {
					if (TextUtils.isEmpty(response.getCustName())||response.getCustName().equals("null")) {
						response.setCustName("");
					}
				

					application.setCustName(response.getCustName());
					application.setCustPhone(response.getCustPhone());
					application.setCustID(response.getCustId() + "");
					application.setCustInt(response.getCustInt() + "");
					application.setLastModifiedTime(response
							.getLastModifiedTime() + "");
					application.setDiscussTime(response
							.getLastDiscussTime()+ "");
					application.setAvatarUrl(response.getUrl());
					String phoneNum = MD5Utils.MD5(application.getCustPhone());
					
					if (!TextUtils.isEmpty(phoneNum)
							&& !phoneNum.equals("null")) {
						Log.w(TAG, "MD5Utils.MD5(phoneNum)=" + phoneNum);
						JPushInterface.setAlias(getApplicationContext(),
								phoneNum, mAliasCallback);
						LoginActivity.this.finish();

					} else {
						application.showToast(response.getReturnMsg());
					}
				} else {
					application.showToast(response.getReturnMsg());
				}
			} else {
				application.showToast("网络连接异常");
			}

		}

		private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

			@Override
			public void gotResult(int code, String alias, Set<String> tags) {
				String logs;
				switch (code) {
				case 0:
					logs = "Set tag and alias success";
					Log.i(TAG, logs);
					break;

				case 6002:
					logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
					Log.i(TAG, logs);
					if (Utils.isNetworkAvailable(activity)) {
						// mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS,
						// alias), 1000 * 60);

					} else {
						Log.i(TAG, "No network");
					}
					break;

				default:
					logs = "Failed with errorCode = " + code;
					Log.e(TAG, logs);
				}

				// ExampleUtil.showToast(logs, getApplicationContext());
			}

		};
	}
}
