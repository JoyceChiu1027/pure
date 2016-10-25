package com.bupt.chengde.ui.me;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.ui.BaseActivity;

/**
 * @author wyf
 * @类 :修改生日
 * @version 1.0
 */
public class ChangeBirthdayActivity extends BaseActivity {

	private TextView birTextView;
	private TextView cancleTextView;
	private TextView saveTextView;
	
	private Calendar calendar;
	private String mYear;
	private String mMonth;
	private String mDay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_sex);

		initView();
	}

	private void initView() {
		birTextView = (TextView) findViewById(R.id.changesex);
		cancleTextView = (TextView) findViewById(R.id.top_cancle);
		saveTextView = (TextView) findViewById(R.id.top_sub);
		((LinearLayout) findViewById(R.id.top_back)).setVisibility(View.GONE);
		cancleTextView.setVisibility(View.VISIBLE);
		saveTextView.setVisibility(View.VISIBLE);
		((TextView) findViewById(R.id.top_name)).setText("生日");
		saveTextView.setText("保存");
		if (application.getUserBirthday() == null || application.getUserBirthday().isEmpty()) {
			birTextView.setText("待完善");
		}else {
			birTextView.setText(application.getUserBirthday());
		}
		calendar = Calendar.getInstance();
		birTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new DatePickerDialog(ChangeBirthdayActivity.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int month, int day) {
								// TODO Auto-generated method stub
								mYear = ""+year;
								mMonth = (month + 1)<10?"0"+(month+1):""+(month+1);
								mDay = day < 10 ?"0"+day:""+day;
								// 更新EditText控件日期 小于10加0
								birTextView.setText(new StringBuilder()
										.append(mYear)
										.append("年")
										.append(mMonth).append("月")
										.append(mDay).append("日"));
							}
						}, calendar.get(Calendar.YEAR), calendar
								.get(Calendar.MONTH), calendar
								.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		saveTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				application.setUserBirthday(birTextView.getText().toString());
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
