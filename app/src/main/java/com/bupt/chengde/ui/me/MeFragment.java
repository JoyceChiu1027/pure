package com.bupt.chengde.ui.me;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.control.MyApplication;
import com.bupt.chengde.entity.PayAmountInfoQiandao;
import com.bupt.chengde.ui.BaseFragment;
import com.bupt.chengde.util.GlideHelper;
import com.bupt.chengde.util.LogUtil;
import com.bupt.chengde.util.WebserviceUtils;

/**
 * @author wyf
 * @类 :我的
 * @version 1.0
 */
public class MeFragment extends BaseFragment {

	private View view;
	private Button qiandaoButton, loginButton;
	private LinearLayout nologin, logined;
	private TextView jifen,custNameTV;
	private ImageView imageView;
	private String date;
	private String nowdate;
	
	@SuppressWarnings("unused")
//	private ImageLoader imageLoader;
//	@SuppressWarnings("unused")
//	 private DisplayImageOptions options;
	public MyApplication application;
	
	private String jifenStr;
    private static final String TAG=MeFragment.class.getSimpleName();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_me, container, false);
		application = MyApplication.getMyApplication();
//		imageLoader = ImageLoader.getInstance();
//		options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
//				.showImageForEmptyUri(R.drawable.default_head_icon).showImageOnFail(R.drawable.default_head_icon)
//				.showImageOnLoading(R.drawable.default_head_icon).displayer(new CircleBitmapDisplayer()).build();
		initView();
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		if (activity.application.getCustID()==null
				|| activity.application.getCustID().isEmpty()) {
			logined.setVisibility(View.GONE);
			nologin.setVisibility(View.VISIBLE);
		} else {
			com.bupt.chengde.util.LogUtil.d(TAG, "activity.application.getCustID()="+activity.application.getCustID());
			logined.setVisibility(View.VISIBLE);
			nologin.setVisibility(View.GONE);
		}
		if (activity.application.getAvatarUrl() == null
				|| activity.application.getAvatarUrl().isEmpty()) {
			imageView.setBackgroundResource(R.drawable.default_head_icon);
		} else {
//			 imageLoader.displayImage(
//			 WebserviceUtils.URL_PREFIX + application.getPortraiturl(),
//			 imageView, options);
			GlideHelper.showAvatarWithUrl(activity,application.getAvatarUrl(),imageView);
		}
		if (!TextUtils.isEmpty(activity.application.getCustName())&&!activity.application.getCustName().equals("null")) {
			com.bupt.chengde.util.LogUtil.d(TAG, "activity.application.getCustName()="+activity.application.getCustName());
			custNameTV.setText(activity.application.getCustName());
		}else{
			custNameTV.setText(getResources().getString(R.string.app_name));
		}
		if (!TextUtils.isEmpty(activity.application.getCustInt())&&!activity.application.getCustInt().equals("null")) {
			jifenStr=activity.application.getCustInt();
			jifen.setText("积分：" + jifenStr + "分");
		}else{
			jifen.setText("积分：" + 0 + "分");
		}
		if (!TextUtils.isEmpty(activity.application.getLastModifiedTime())&&!activity.application.getLastModifiedTime().equals("null")) {
			Date d = new Date();
			SimpleDateFormat sd = new SimpleDateFormat(
					"yyyy-MM-dd");
			String newdate = sd.format(d);
			if (newdate.equals(activity.application.getLastModifiedTime().substring(0, 10))) {
				qiandaoButton.setText("已签到");
				qiandaoButton.setClickable(false);
				com.bupt.chengde.util.LogUtil.i(TAG, newdate);
				com.bupt.chengde.util.LogUtil.i(TAG, activity.application.getLastModifiedTime().substring(0, 10));
			}else{
				qiandaoButton.setText("未签到");
				//qiandaoButton.setEnabled(true);
				qiandaoButton.setClickable(true);
				qiandaoButton.setBackgroundResource(R.drawable.me_bu_back);
			}
			
			LogUtil.i(TAG, "onStart中newdate="+newdate+" and getLastModifiedTime="+activity.application.getLastModifiedTime());
		}else{
			qiandaoButton.setText("未签到");
			qiandaoButton.setClickable(true);
			
		}
	}

	private String dateNowStr;
	private PayAmountInfoQiandao payAmountInfo = null;

	private void initView() {
	/*	Date d = new Date();
		SimpleDateFormat sd = new SimpleDateFormat(
				"yyyy-MM-dd");
		nowdate = sd.format(d);
		Log.i(TAG,"initView的nowdate="+ nowdate);
		*/
		qiandaoButton = (Button) view.findViewById(R.id.me_qiandao);
		loginButton = (Button) view.findViewById(R.id.login);
		nologin = (LinearLayout) view.findViewById(R.id.me_nologin);
		logined = (LinearLayout) view.findViewById(R.id.me_login);
		custNameTV=(TextView) view.findViewById(R.id.cust_name_tv);
		jifen = (TextView) view.findViewById(R.id.me_jifen);
		imageView = (ImageView) view.findViewById(R.id.me_touxiang);
		/*if (TextUtils.isEmpty(activity.application.getPortraiturl())
				|| activity.application.getPortraiturl().equals("null")) {
			imageView.setBackgroundResource(R.drawable.me_touxiang);
		} else {
			Utils.setImagePhoto(activity.application.getPortraiturl(),
					imageView, activity);
			// imageLoader.displayImage(
			// WebServiceUtil.PIC_URL + application.getPortraiturl(),
			// touImageView, options);
		}

		if (activity.application.getCustInt() == null
				|| activity.application.getCustInt().isEmpty()) {
			jifen.setText("积分：" + activity.application.getCustInt() + "分");
		}else {
			jifen.setText("积分：" + activity.application.getCustInt() + "分");
		} 
		*/
		/*if (activity.application.getCustInt() == null
				|| activity.application.getCustInt().isEmpty()) {
		}else if(activity.application.getLastModifiedTime().substring(0, 10).equals(nowdate)) {
			qiandaoButton.setText("已签到");
			Log.i("MeFragment", activity.application.getLastModifiedTime().substring(0, 10));
			Log.i("MeFragment", nowdate);
		}
		*/
		imageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(activity,
						MineInfromationActivity.class));
			}
		});
		qiandaoButton.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View arg0) {
				Date d = new Date();
				SimpleDateFormat sd = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				dateNowStr = sd.format(d);
				date    =    sd.format(d); 
				com.bupt.chengde.util.LogUtil.i(TAG, "签到时间："+date);
				if (qiandaoButton.getText().toString().equals("已签到")) {
					qiandaoButton.setClickable(false);
					//qiandaoButton.setEnabled(false);
					
				}else {	
					qiandaoButton.setClickable(true);
					//qiandaoButton.setEnabled(true);
					runHandler();
				}
//				runHandler();
			}
		});
		loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
		});
	}

	@Override
	protected void doForeGround() throws Exception {
		System.out.println(payAmountInfo.toString());
		if (payAmountInfo.getReturnCode().equals(CodeConstants.RETURN_SUCCESS)) {
			activity.application.setCustInt(payAmountInfo.getLatestCount());
			activity.application.setLastModifiedTime(date+"");
			jifen.setText("积分：" + payAmountInfo.getLatestCount() + "分");
			qiandaoButton.setText("已签到");
			qiandaoButton.setClickable(false);
			//qiandaoButton.setBackgroundResource(R.color.unavailable_bg_color);
			activity.application.showToast("签到成功");
		} else if (payAmountInfo.getReturnCode().equals("600")) {
			qiandaoButton.setText("已签到");
			activity.application.setLastModifiedTime(date+"");
			qiandaoButton.setClickable(false);
			//qiandaoButton.setBackgroundResource(R.color.unavailable_bg_color);
			activity.application.showToast("今天已经签到，不能重复签到");
		} else {
			activity.application.showToast(payAmountInfo.getReturnMsg());
		}
	}

	@Override
	protected void doBackGround() throws Exception {
		payAmountInfo = WebserviceUtils.addSignInfo(
				Integer.parseInt(activity.application.getCustID()), dateNowStr);
	}

	class checkSocre extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			String function = null;
			try {
				function = WebserviceUtils.checkScoreFunction(Integer
						.parseInt(params[0]));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return function;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				activity.application.setCustInt(result);
				jifen.setText("积分：" + result + "分");
			} else {
				activity.application.showToast("服务端无返回数据");
			}
		}
	}
}
