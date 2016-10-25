package com.bupt.chengde.ui.me;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.MediaHandler;
/**
 * @author zhaojie 抽奖页面
 */
public class LotteryActivity extends BaseActivity {
	
	static String tag = "LotteryActivity";
	//private LoadingProgressDialog loadingDialog = null;
	private MediaHandler mediaHandler;

	private  ImageView imageView;
	private Handler getProgramInfoListHandler;

	static Bitmap bMapRotate;
	int streamId = -1;
	Dialog selectorDialog;
	private Button backBtn;
	private int degree = 0;
	boolean flagaward;

	int degreebuzhong;
	int degreezhong;
	// private String[] data={"","","",""};
	private String[] data = new String[5];

	int time = 3;
	boolean flag;
	boolean flagawarded = false;
	ImageView imageBeginImageView;
	ImageView dialogImageView;
	TextView dialogTextView;
	View view;
	String whataward;
	int[] degree1 = { 0, 2, 4, degree, 6 };
	int[] degree2 = { 0, 2, 4, 6 };
	private String phoneNo;
	String awardset = "";
	TextView awardsetTextView0;
	TextView awardsetTextView1;
	TextView awardsetTextView2;
	TextView awardsetTextView3;
	boolean flagisfirst = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lottery);
		initView();
		getProgramInfoListHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {

				if (msg.what == 0) {

					try {

					} catch (Exception e) {

						e.printStackTrace();
					}
				} else if (msg.what == 1) {

					if (data.length > 0 && data[0] != null)
						awardsetTextView0.setText(data[0]);
					if (data.length > 1 && data[1] != null)
						awardsetTextView1.setText(data[1]);
					if (data.length > 2 && data[2] != null)
						awardsetTextView2.setText(data[2]);
					if (data.length > 3 && data[3] != null)
						awardsetTextView3.setText(data[3]);
					// Log.d("data[0]", data[0] + "1");

					// }
					flagisfirst = false;

				} else {
					//dismissInfoListdialog();
					//ToastUtil.show(ScrollPlantActivity.this, "网络连接失败");
					//activity.
				}

			}
		};
		if (flagisfirst) {
			//loadingInfoList();
			Runnable r = new ConnectThreadForAwardSet(getProgramInfoListHandler);
			new Thread(r).start();
		}
	}
	private void initView(){
		imageBeginImageView = (ImageView) findViewById(R.id.iv_lottery_switch);
		bMapRotate = BitmapFactory.decodeResource(getResources(),
				R.drawable.plant_small_android);
		TextView titleTextView = (TextView) findViewById(R.id.top_name);
		titleTextView.setText("幸运大转盘");
		imageView = (ImageView) findViewById(R.id.roundTurnplateView_lottery_plant);
		imageView.setBackgroundResource(R.drawable.plant_small_android);
		awardsetTextView0 = (TextView) findViewById(R.id.awardset0);
		awardsetTextView1 = (TextView) findViewById(R.id.awardset1);
		awardsetTextView2 = (TextView) findViewById(R.id.awardset2);
		awardsetTextView3 = (TextView) findViewById(R.id.awardset3);
		initSound(this);
		if (data.length > 0 && data[0] != null)
			awardsetTextView0.setText(data[0]);
		if (data.length > 1 && data[1] != null)
			awardsetTextView1.setText(data[1]);
		if (data.length > 2 && data[2] != null)
			awardsetTextView2.setText(data[2]);
		if (data.length > 3 && data[3] != null)
			awardsetTextView3.setText(data[3]);
	}
	public void onClick(View paramView) {

		if (time > 0) {
			if (time == 3) {
				Runnable r = new ConnectThreadForProgramList(
						getProgramInfoListHandler);
				new Thread(r).start();
			}
			if (degree % 2 == 0) {
				flagaward = false;

				Random random = new Random();

				int index = random.nextInt(degree2.length);

				degreebuzhong = degree2[index];

				Animation rotateAnimation = initAnimation(degreebuzhong);

				this.imageView.startAnimation(rotateAnimation);
				time--;
			} else {

				Random random = new Random();

				int index = random.nextInt(degree1.length);
				if (time == 1 && !flagawarded) {
					degreezhong = degree;

				} else {
					degreezhong = degree1[index];
				}

				final Animation rotateAnimation = initAnimation(degreezhong);

				this.imageView.startAnimation(rotateAnimation);
				if (degree == degreezhong) {
					flagaward = true;
					flagawarded = true;
					if (degree == 1) {
						whataward = "三";
					} else if (degree == 3) {
						whataward = "二";
					} else if (degree == 5) {
						whataward = "一";
					} else if (degree == 7) {
						whataward = "特";
					}
					time--;
					degree1 = degree2;

				} else {
					flagaward = false;
					time--;

				}
			}
		} else {

			//initSelectorDialog(flagaward, time);
		}

	}

	
	private void initSound(Context paramContext) {
		this.mediaHandler = MediaHandler.getInstance(paramContext);
		this.mediaHandler.addSound(0, R.raw.loop);
		this.mediaHandler.addSound(1, R.raw.result);
	}
	private Animation initAnimation(int degreezhong2) {
		final Animation rotateAnimation = new RotateAnimation(0f,
				2520f + degreezhong2 * 45, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setDuration(5000);
		rotateAnimation.setFillAfter(true);
		rotateAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

				imageBeginImageView.setClickable(false);
				LotteryActivity.this.mediaHandler
						.stop(LotteryActivity.this.streamId);
				LotteryActivity.this.streamId = LotteryActivity.this.mediaHandler
						.play(0, 5);

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				imageBeginImageView.setClickable(true);
				//initSelectorDialog(flagaward, time);
				LotteryActivity.this.mediaHandler.stop(streamId);
				LotteryActivity.this.mediaHandler.play(1);

			}

		});
		return rotateAnimation;
	}
	
	private class ConnectThreadForProgramList extends Thread {

		Handler h;

		public ConnectThreadForProgramList(Handler h) {
			this.h = h;
		}

		@Override
		public void run() {
			try {
				String ip;
				String spec;
				// cityName=sp.getString(CodeConstants.CITY_NAME, "北京");
				/*phoneNo = AppUtil.getFromPref(CodeConstants.PHONENUMBER,
						ScrollPlantActivity.this);*/
				phoneNo="";
				// ip=sp.getString(CodeConstants.URL_PREFIX,
				// "http://buptguoan.gicp.net:7000");
				// Log.d("phoneNo=", phoneNo);
				// Log.d("ip=", ip);
				// spec=ip+"/gsyytApp/game/turntable.action?phone=";
				// URL url = new URL(spec+phoneNo);
				// URL url = new URL(
				// "http://192.168.10.242:7000/gsyytApp/game/turntable.action?phone="
				// + phoneNo);

				ip = CodeConstants.URL_PREFIX+CodeConstants.URL_DOMIN+ "/game/turntable.action?phone="
						+ phoneNo;
				Log.d("ip=", ip);
				URL url = new URL(ip);

				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				InputStreamReader is = null;

				if (conn.getResponseCode() == 200) {
					is = new InputStreamReader(conn.getInputStream());
					BufferedReader bufferedReader = new BufferedReader(is);

					String resultString = "";
					String resultlineString = null;

					while ((resultlineString = bufferedReader.readLine()) != null) {
						resultString += resultlineString;
					}

					degree = Integer.parseInt(resultString);
					Log.d(tag, resultString);
					System.out.println(resultString);
					h.sendEmptyMessage(0);
					is.close();
					conn.disconnect();

				}
			} catch (Exception e) {
				h.sendEmptyMessage(1);
			}
			h.sendEmptyMessage(0);
		}
	}

 class ConnectThreadForAwardSet extends Thread {

		Handler h;

		public ConnectThreadForAwardSet(Handler h) {
			this.h = h;
		}

		@Override
		public void run() {
			try {
				String ip;
				phoneNo ="";
				/*
				 * TelephonyManager mTelephonyMgr = (TelephonyManager)
				 * getSystemService(ScrollPlantActivity.this.TELEPHONY_SERVICE);
				 * String phoneString = mTelephonyMgr.getLine1Number();
				 */
				// URL url = new URL(
				// "http://58.20.130.152:7000/gsyytApp/game/relationName.action?gameCode=turntable");

				ip = CodeConstants.URL_PREFIX+CodeConstants.URL_DOMIN+ "/game/relationName.action?gameCode=turntable";
				Log.d("ip=", ip);
				URL url = new URL(ip);

				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				InputStreamReader is = null;

				if (conn.getResponseCode() == 200) {
					is = new InputStreamReader(conn.getInputStream());
					BufferedReader bufferedReader = new BufferedReader(is);

					String resultString = "";
					String resultlineString = null;

					while ((resultlineString = bufferedReader.readLine()) != null) {
						resultString += resultlineString;
					}

					data = resultString.split(";");
					Log.d(tag, resultString);

					is.close();
					conn.disconnect();

				}
			} catch (Exception e) {
				h.sendEmptyMessage(2);
			}
			h.sendEmptyMessage(1);
		}
	}

	protected void onResume() {
		super.onResume();

	}

	protected void onStop() {

		super.onStop();
	}

	protected void onDestroy() {
		Log.e("ScrollPlantActivity", "onDestroy");
		//setContentView(R.layout.view_null);
		super.onDestroy();
	}

/*	private void loadingInfoList() {

		loadingDialog = new LoadingProgressDialog(ScrollPlantActivity.this,
				"加载奖品明细中，请稍后...");
		if (loadingDialog != null) {
			loadingDialog.show();
		}
	}

	private void dismissInfoListdialog() {

		if (loadingDialog != null) {
			loadingDialog.dismiss();
			loadingDialog = null;
		}
	}*/
}
