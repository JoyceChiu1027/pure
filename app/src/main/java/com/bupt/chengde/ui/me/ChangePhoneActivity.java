package com.bupt.chengde.ui.me;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.CommonBaseInfo;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.Utils;
import com.bupt.chengde.util.WebserviceUtils;

/**
 * @author wyf
 * @类 :修改手机号
 * @version 1.0
 */
public class ChangePhoneActivity extends BaseActivity {
	private static final String TAG=ChangePhoneActivity.class.getSimpleName();
	private TextView cancleTextView;
	private TextView saveTextView;
	private EditText editText;
	private CommonBaseInfo modifyPhoneNo;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_change_nicheng);
		//LogUtil.i(TAG, "custId="+application.getCustID());
		initView();
	}

	@Override
	protected void doBackGround() throws Exception {
		modifyPhoneNo = WebserviceUtils.modifyPhoneNo(application.getCustID(), editText.getText().toString());
	}
	
	@Override
	protected void doForeGround() throws Exception {
		if (modifyPhoneNo.getReturnCode().trim().equals(CodeConstants.RETURN_SUCCESS)) {
			application.setCustPhone(editText.getText().toString());
			finish();
			application.showToast("保存成功");
		}else {
			application.showToast(modifyPhoneNo.getReturnMsg());
		}
	}
	
	private void initView() {
		editText = (EditText) findViewById(R.id.nicheng_input);
		cancleTextView = (TextView)findViewById(R.id.top_cancle);
		saveTextView = (TextView) findViewById(R.id.top_sub);
		((LinearLayout)findViewById(R.id.top_back)).setVisibility(View.GONE);
		cancleTextView.setVisibility(View.VISIBLE);
		saveTextView.setVisibility(View.VISIBLE);
		((TextView)findViewById(R.id.top_name)).setText("手机号");
		saveTextView.setText("保存");
		editText.setHint(getIntent().getStringExtra("phone"));
		saveTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (Utils.isMobileNO(editText.getText().toString())) {
					runHandler();
				}else {
					application.showToast("手机号不合法");
				}
				
			}
		});
		cancleTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
}
