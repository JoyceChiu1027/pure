package com.bupt.chengde.ui.me;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.entity.ResponseAboutUsMemo;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.Utils;
import com.bupt.chengde.util.WebserviceUtils;

/**
 * @author wyf
 * @类 :关于我们页面
 * @version 1.0
 */
public class AboutUsActivity extends BaseActivity {

	private LinearLayout backTextView;
	private ResponseAboutUsMemo aboutUsMemo;
	private TextView com_jianjie,Feedback_qq,Feedback_weixin,company;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		initView();
		com_jianjie.setText("\u3000\u3000"+"《承德96888》是河北广电网络集团承德公司为方便用户体验广电业务，快捷办理业务，享受全方位服务而推出的手机APP。广电网络，视频专家。");
	}
	private void initView() {
		backTextView = (LinearLayout) findViewById(R.id.top_back);
		((TextView) findViewById(R.id.top_name)).setText("关于我们");
		com_jianjie = (TextView) findViewById(R.id.com_jianjie);
		company = (TextView) findViewById(R.id.company);
		backTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		aboutUsMemo = new ResponseAboutUsMemo();
	}
	@Override
	protected void doForeGround() throws Exception {
		System.out.println(aboutUsMemo.toString());
		com_jianjie.setText(aboutUsMemo.getMemo());

	}

	@Override
	protected void doBackGround() throws Exception {
		aboutUsMemo = WebserviceUtils.getAboutMemo(Utils.getVersionCode(activity));
	}

	


}
