package com.bupt.chengde.ui.me;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.CommonBaseInfo;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.WebserviceUtils;

/***************
 * 修改密码
 * @date:2016-4-18 上午9:57:36
 * @author:wyf
 * @version:1.0
 * @return:
 * @throws:
 ***************/
public class ChangePwdActivity extends BaseActivity {

	private LinearLayout backTextView;
	private EditText oldpwd,newpwd,querenpwd;
	private TextView queren;//确认按钮
	private CommonBaseInfo modifyCustPwd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pwd);
		
		initView();
	}

	private void initView() {
		oldpwd = (EditText) findViewById(R.id.chang_now_pwd);
		newpwd = (EditText) findViewById(R.id.chang_new_pwd);
		querenpwd = (EditText) findViewById(R.id.chang_queren_pwd);
		queren = (TextView) findViewById(R.id.queren);
		backTextView = (LinearLayout) findViewById(R.id.top_back);
		
		((TextView) findViewById(R.id.top_name)).setText("修改密码");
		backTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		queren.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String oldString = oldpwd.getText().toString().trim();
				String newString = newpwd.getText().toString().trim();
				String quereString = querenpwd.getText().toString().trim();
				if (oldString == null||oldString.isEmpty()) {
					Toast.makeText(getApplicationContext(), "没有输入当前密码", Toast.LENGTH_SHORT).show();
				}else if (newString == null||newString.isEmpty()){
					Toast.makeText(getApplicationContext(), "没有输入新密码", Toast.LENGTH_SHORT).show();
				}else if (newString.length()<6 || newString.length()>18) {
					Toast.makeText(getApplicationContext(), "新密码请输入6-18位字母数字组合", Toast.LENGTH_SHORT).show();
				}else if (quereString == null||quereString.isEmpty()){
					Toast.makeText(getApplicationContext(), "没有输入确认密码", Toast.LENGTH_SHORT).show();
				}else if (!newString.equals(quereString)) {
					Toast.makeText(getApplicationContext(), "两次密码输入不一样", Toast.LENGTH_SHORT).show();
				}else {
					runHandler();
				}
				
			}
		});
	}
	@Override
	protected void doBackGround() throws Exception {
		modifyCustPwd = WebserviceUtils.modifyCustPwd(application.getCustID(), oldpwd.getText().toString().trim(), newpwd.getText().toString().trim());
	}
	
	@Override
	protected void doForeGround() throws Exception {
		if (modifyCustPwd.getReturnCode().trim().equals(CodeConstants.RETURN_SUCCESS)) {
			application.setPassword(newpwd.getText().toString().trim());
			finish();
			application.showToast("保存成功");
		}else {
			application.showToast(modifyCustPwd.getReturnMsg());
		}
	}
}
