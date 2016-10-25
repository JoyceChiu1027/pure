package com.bupt.chengde.ui.me;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.CommonBaseInfo;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.WebserviceUtils;

/**
 * @author ln
 * @类 :修改昵称
 * @version 1.0
 */
public class ChangeNichengActivity extends BaseActivity {

	private TextView cancleTextView;
	private TextView saveTextView;
	private EditText editText;
	private CommonBaseInfo modifyCustName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_nicheng);
		
		initView();
	}

	private void initView() {
		editText = (EditText) findViewById(R.id.nicheng_input);
		cancleTextView = (TextView)findViewById(R.id.top_cancle);
		saveTextView = (TextView) findViewById(R.id.top_sub);
		findViewById(R.id.top_back).setVisibility(View.GONE);
		cancleTextView.setVisibility(View.VISIBLE);
		saveTextView.setVisibility(View.VISIBLE);
		((TextView)findViewById(R.id.top_name)).setText("昵称");
		saveTextView.setText("保存");
		editText.setHint(getIntent().getStringExtra("nicheng"));
		saveTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (editText.getText().toString().trim().equals("")) {
					application.showToast("用户名不能为空");
				}else {
					runHandler();
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

	@Override
	protected void doBackGround() throws Exception {
		Log.i("!!!!!!!!!!!!!!!!!!!!!!", application.getCustID()+":"+application.getCustName().toString().trim()+":"+editText.getText().toString().trim());
		modifyCustName = WebserviceUtils.modifyCustName(application.getCustID(), application.getCustName().toString().trim(), editText.getText().toString().trim());
	}
	
	@Override
	protected void doForeGround() throws Exception {
		Log.i("____________________", modifyCustName.getReturnCode().trim());
		if (modifyCustName.getReturnCode().trim().equals(CodeConstants.RETURN_SUCCESS)) {
			application.setCustName(editText.getText().toString());
			finish();
			application.showToast("保存成功");
		}else {
			application.showToast(modifyCustName.getReturnMsg());
		}
	}
	
}
