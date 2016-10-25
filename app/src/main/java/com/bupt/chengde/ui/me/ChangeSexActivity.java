package com.bupt.chengde.ui.me;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.ui.BaseActivity;

/**
 * @author wyf
 * @类 :修改性别
 * @version 1.0
 */
public class ChangeSexActivity extends BaseActivity {

	private TextView sexTextView;
	private TextView cancleTextView;
	private TextView saveTextView;
	
	private int selected = 0; 
	private String[] strings = new String[] {"男","女"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_sex);
		
		initView();
		
	}

	private void initView() {
		sexTextView = (TextView) findViewById(R.id.changesex);
		cancleTextView = (TextView)findViewById(R.id.top_cancle);
		saveTextView = (TextView) findViewById(R.id.top_sub);
		((LinearLayout)findViewById(R.id.top_back)).setVisibility(View.GONE);
		cancleTextView.setVisibility(View.VISIBLE);
		saveTextView.setVisibility(View.VISIBLE);
		((TextView)findViewById(R.id.top_name)).setText("性别");
		saveTextView.setText("保存");
		sexTextView.setText(getIntent().getStringExtra("sex"));
		sexTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Dialog dialog = new AlertDialog.Builder(ChangeSexActivity.this).setTitle(null)
				.setIcon(null)
				.setSingleChoiceItems(strings, 0, 
					 	  new DialogInterface.OnClickListener() {
                     
			 	     public void onClick(DialogInterface dialog, int which) {
			 	        selected = which;
			 	     }
			 	  }
			 	)
			 	.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						sexTextView.setText(strings[selected]);
					}
				}).create();
				dialog.show();
			}
		});
		saveTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				application.setUserGender(sexTextView.getText().toString());
				finish();
				application.showToast("保存成功");
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
