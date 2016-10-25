package com.bupt.chengde.ui.se;

import java.util.ArrayList;
import java.util.List;

import com.bupt.chengde.R;
import com.bupt.chengde.control.AdTask;
import com.bupt.chengde.control.AsyncTaskCallback;
import com.bupt.chengde.control.CancelLikeStoreFunctionTask;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.control.LikeStoreFunctionTask;
import com.bupt.chengde.control.LikeStoreTaskCallback;
import com.bupt.chengde.entity.CommonBaseInfo;
import com.bupt.chengde.entity.ResponseAdPoster;
import com.bupt.chengde.entity.ResponseBenPeoById;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.LogUtil;
import com.bupt.chengde.util.Utils;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.ListViewTop;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
//import com.bupt.chengde.widget.SharePopupWindow;
//import com.mob.tools.utils.UIHandler;
//import cn.sharesdk.framework.Platform;
//import cn.sharesdk.framework.PlatformActionListener;
//import cn.sharesdk.tencent.qq.QQClientNotExistException;
//import cn.sharesdk.wechat.utils.WechatClientNotExistException;

public class BianminDetailActivity extends BaseActivity
		implements OnClickListener, LikeStoreTaskCallback/*, PlatformActionListener, Handler.Callback */{
	private static final String TAG=BianminDetailActivity.class.getSimpleName();
	private WebView mWebView;
	private ProgressBar progress_bar;
	private View refresh;// 刷新按钮
	private CustomFrameLayout customFrameLayout;
	private LinearLayout operationLayout;
	
	private ImageView dialBtn, commentsBtn, shareBtn, collectBtn, likeBtn;
	private TextView title, addrTv, telTv, commentTV,errorTxt;
	private AlertDialog dialog;
//	SharePopupWindow popupWindow = null;
	
	private String url = "";
	private String custName;
	private String custId;
	private String modId;
	private String busiId;
//	private String busiName;
//	private int busiType;

	// 状态变量
	private int isLike = 0;// 0为未点赞状态 1为点赞状态
	private int isCollect = 0;// 0为未收藏状态1为收藏状态

	// 类型常量
	private static final int TYPE_LIKE = 1;
	private static final int TYPE_COLLECT = 2;

	// private CommonBaseInfo response;
	private ResponseBenPeoById responseDetail;
	private LikeStoreFunctionTask likeTask;
	private CancelLikeStoreFunctionTask cancelLikeTask;
	// 用于显示小广告
	private ListViewTop mListViewTop;
	private LinearLayout adConLayout;
	private AdTask task;
	private Context mContext;
	private List<ResponseAdPoster> adReturnList = new ArrayList<ResponseAdPoster>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bianmin_detail);
		mContext = BianminDetailActivity.this;
		modId = getIntent().getStringExtra(CodeConstants.MODULE_ID);
		busiId = getIntent().getStringExtra(CodeConstants.BUSI_ID);
//		busiName = getIntent().getStringExtra(CodeConstants.BUSI_NAME);
//		busiType = getIntent().getIntExtra(CodeConstants.TYPE, 1);
		custId = application.getCustID();
		custName = application.getCustName();
		initView();
		runHandler();

		task = new AdTask(2, mContext, adReturnList);
		task.setCallback(new AsyncTaskCallback<List<ResponseAdPoster>>() {

			@Override
			public void onNetError() {
				adConLayout.removeAllViews();
				adConLayout.setVisibility(View.GONE);
			}

			@Override
			public void onFailed(List<ResponseAdPoster> response) {
				adConLayout.removeAllViews();
				adConLayout.setVisibility(View.GONE);
			}

			@Override
			public void onCompleted(List<ResponseAdPoster> response) {
				if (mListViewTop == null) {
					mListViewTop = new ListViewTop(mContext);
					mListViewTop.initData(2, response);
				} else {
					mListViewTop.notifyDataSetChanged(response);
				}
				adConLayout.removeAllViews();
				adConLayout.addView(mListViewTop);
				adConLayout.setVisibility(View.VISIBLE);
			}
		});
		task.execute(CodeConstants.BEN);
	}

	private void initView() {
		operationLayout=(LinearLayout) findViewById(R.id.operation_layout);
		title = (TextView) findViewById(R.id.top_name);
		title.setText("便民");
		adConLayout = (LinearLayout) findViewById(R.id.ad_con_layout);
		addrTv = (TextView) findViewById(R.id.bian_addr);
		telTv = (TextView) findViewById(R.id.bian_tel);
		commentTV = (TextView) findViewById(R.id.comment_bg);
		collectBtn = (ImageView) findViewById(R.id.collect_btn);
		likeBtn = (ImageView) findViewById(R.id.like_btn);
		shareBtn = (ImageView) findViewById(R.id.share_btn);
		dialBtn = (ImageView) findViewById(R.id.bian_dial_iv);
		commentsBtn = (ImageView) findViewById(R.id.comments_btn);
		progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
		customFrameLayout = (CustomFrameLayout) findViewById(R.id.web_frame);
		customFrameLayout.setList(new int[] { R.id.common_web,
				R.id.common_net_error });
		refresh = findViewById(R.id.error_btn);
		errorTxt = (TextView) findViewById(R.id.error_txt);
		mWebView = (WebView) findViewById(R.id.common_web);
		mWebView.getSettings().setDefaultTextEncodingName("utf-8");
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		MyWebviewClient mWebClient = new MyWebviewClient();
		mWebView.setWebChromeClient(mWebClient);
		mWebView.setWebViewClient(new WebViewClient());
		commentTV.setOnClickListener(this);
		collectBtn.setOnClickListener(this);
		shareBtn.setOnClickListener(this);
		likeBtn.setOnClickListener(this);
		dialBtn.setOnClickListener(this);
		commentsBtn.setOnClickListener(this);
		findViewById(R.id.top_back).setOnClickListener(this);
	}

	@Override
	protected void doBackGround() throws Exception {
		// TODO Auto-generated method stub
		super.doBackGround();
		if (TextUtils.isEmpty(custId) || custId.equals("null")) {
			custId = "";
		}
		responseDetail = WebserviceUtils.getBenPeoById(custId, busiId, modId);

	}

	@Override
	protected void doForeGround() throws Exception {
		if (responseDetail != null) {
			if (responseDetail.getReturnCode()==0) {
				
				if (!TextUtils.isEmpty(responseDetail.getBenPeoAddr()) && !responseDetail.getBenPeoAddr().equals("null")) {
					addrTv.setText("地址：" + responseDetail.getBenPeoAddr());
				}
				if (!TextUtils.isEmpty(responseDetail.getBenPeoPhone())
						&& !responseDetail.getBenPeoPhone().equals("null")) {
					telTv.setText( "电话："+responseDetail.getBenPeoPhone());
				}
				
				isLike = responseDetail.getIsLike();
				isCollect = responseDetail.getIsStore();
				url = responseDetail.getDetailUrl();
				if (!TextUtils.isEmpty(url)&&!url.equals("null")) {
					url=CodeConstants.HTML_URL_PREFIX+url;
				}
				LogUtil.i(TAG, "url="+url);
				setDrawalbleRes();
			} else {
				Toast.makeText(mContext, responseDetail.getReturnMessage()+"", Toast.LENGTH_SHORT).show();
			}

		} else {
			Toast.makeText(mContext, "网络请求超时", Toast.LENGTH_SHORT).show();
		}
	    mWebView.loadUrl(url);
	
	}
	class MyWebviewClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				progress_bar.setVisibility(View.GONE);
				// 判断有无网络
				if (!Utils.isNetworkAvailable(mContext)) {
					customFrameLayout.show(R.id.common_net_error);
					refresh.setVisibility(View.VISIBLE);
					refresh.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Toast.makeText(mContext, "正在刷新", Toast.LENGTH_SHORT).show();
							mWebView.loadUrl(url);
						}
					});
				} else {
					// 判断网络请求网址是否有效
					if (!URLUtil.isValidUrl(url)) {
						customFrameLayout.show(R.id.common_net_error);
						errorTxt.setText("无效网址");
						refresh.setVisibility(View.VISIBLE);
						refresh.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								runHandler();
							}
						});
					} else {
						customFrameLayout.show(R.id.common_web);
						operationLayout.setVisibility(View.VISIBLE);
					}
				}
			} else {
				if (newProgress>=70) {
					operationLayout.setVisibility(View.VISIBLE);
				}else{
					operationLayout.setVisibility(View.GONE);
				}
				customFrameLayout.show(R.id.common_web);
				progress_bar.setVisibility(View.VISIBLE);
				progress_bar.setProgress(newProgress);
			}
		}

		@Override
		public void onReceivedTitle(WebView view, String title) {
			// TODO Auto-generated method stub
			super.onReceivedTitle(view, title);
		}

	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.top_back:
			finish();
			break;
		case R.id.like_btn:
			likeFunction();
			break;
		case R.id.share_btn:
			//share();
			application.showToast("该功能稍后上线");
			break;
		case R.id.collect_btn:
			storeFunction();
			break;
		case R.id.comments_btn:
			toCommentListActivity();
			break;
		case R.id.bian_dial_iv:
			showCallDialog(telTv.getText().toString().trim());
			break;
		case R.id.comment_bg:
			createCommentActivity();
			break;
		default:
			break;
		}

	}


	private void setDrawalbleRes() {
		if (isLike == 1) {
			likeBtn.setBackgroundResource(R.drawable.like_btn_selected);
		} else {
			likeBtn.setBackgroundResource(R.drawable.like_btn_normal);
		}
		if (isCollect == 1) {
			collectBtn.setBackgroundResource(R.drawable.collect_btn_selected);

		} else {
			collectBtn.setBackgroundResource(R.drawable.collect_btn_normal);
		}
	}
	
	private void showCallDialog(final String phoneNum) {
		dialog = new Builder(this).create();
		dialog.show();
		Window window = dialog.getWindow();
		window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		window.setContentView(R.layout.fast_diallog);
		Button lianxiButton = (Button) window.findViewById(R.id.lianxi);
		lianxiButton.setText(phoneNum);
		lianxiButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
				startActivity(intent);
			}
		});
		Button cancleButton = (Button) window.findViewById(R.id.cancel);
		cancleButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
	}

	private void createCommentActivity() {
		if (application.isLogin()) {

			Intent intent = new Intent();
			intent.setClass(mContext, CommentActivity.class);
			intent.putExtra(CodeConstants.MODULE_ID, modId);
			intent.putExtra(CodeConstants.BUSI_ID, busiId);
			startActivity(intent);
		} else {
			Toast.makeText(mContext, "操作前请先登录!", Toast.LENGTH_SHORT).show();
		}
	}

	private void toCommentListActivity() {
		Intent intent = new Intent();
		intent.setClass(mContext, CommentListActivity.class);
		intent.putExtra(CodeConstants.MODULE_ID, modId);
		intent.putExtra(CodeConstants.BUSI_ID, busiId + "");
		startActivity(intent);
	}

	// 操作点赞按钮前先验证是否登录
	private void likeFunction() {
		int type = TYPE_LIKE;
		if (application.isLogin()) {
			if (isLike == 0) {// 为0时表示现在是未点赞状态，点击按钮发送点赞请求
				likeTask = new LikeStoreFunctionTask(activity, type);
				likeTask.execute(custName, custId, modId, busiId + "");
				likeTask.setCallback(this);
			} else {
				cancelLikeTask = new CancelLikeStoreFunctionTask(activity, type);
				cancelLikeTask.execute(custId, modId, busiId + "");
				cancelLikeTask.setCallback(this);
			}

		} else {
			Toast.makeText(mContext, "操作前请先登录!", Toast.LENGTH_SHORT).show();
		}
	}

	private void storeFunction() {
		int type = TYPE_COLLECT;
		if (application.isLogin()) {
			if (isCollect == 0) {
				likeTask = new LikeStoreFunctionTask(activity, type);
				likeTask.execute(custName, custId, modId, busiId + "");
				likeTask.setCallback(this);
			} else {
				cancelLikeTask = new CancelLikeStoreFunctionTask(activity, type);
				cancelLikeTask.execute(custId, modId, busiId + "");
				cancelLikeTask.setCallback(this);
			}
		} else {
			Toast.makeText(mContext, "操作前请先登录!", Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * 分享
	 */
//	@SuppressLint("InflateParams")
//	private void share() {
//		popupWindow = new SharePopupWindow(activity);
//		popupWindow.showShareWindow();
//		popupWindow.initShareParams("分享", "我是分享文本", "http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg",
//				"http://www.crystalnet.com.cn/");
//		popupWindow.setPlatformActionListener(this);
//		// 显示窗口 (设置layout在PopupWindow中显示的位置)
//		LayoutInflater factorys = LayoutInflater.from(activity);
//		final View textEntryView = factorys.inflate(R.layout.fragment_me, null);
//		popupWindow.showAtLocation(textEntryView.findViewById(R.id.pa), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0,
//				0);
//	}
//
//	/**
//	 * 分享完成
//	 */
//	@Override
//	public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//		System.out.println("--------------------完成---------------------");
//		Message msg = new Message();
//		msg.what = 2;
//		msg.arg1 = 1;
//		msg.arg2 = i;
//		msg.obj = platform;
//		UIHandler.sendMessage(msg, this);
//	}
//
//	/**
//	 * 分享错误
//	 */
//	@Override
//	public void onError(Platform platform, int action, Throwable throwable) {
//		System.out.println("--------------------错误---------------------");
//		Message msg = new Message();
//		msg.what = 1;
//		msg.arg1 = action;
//		msg.obj = throwable;
//		UIHandler.sendMessage(msg, this);
//	}
//
//	/**
//	 * 分享取消
//	 */
//	@Override
//	public void onCancel(Platform arg0, int arg1) {
//		System.out.println("--------------------取消---------------------");
//		Message msg = new Message();
//		msg.what = 0;
//		UIHandler.sendMessage(msg, this);
//	}
//
//	/**
//	 * 分享接受消息
//	 */
//	@Override
//	public boolean handleMessage(Message msg) {
//		int what = msg.what;
//		switch (what) {
//		case 0:
//			application.showToast("分享已取消");
//			break;
//		case 1:
//			String failtext;
//			if (msg.obj instanceof WechatClientNotExistException || msg.obj instanceof QQClientNotExistException
//					|| msg.obj instanceof WechatClientNotExistException) {
//				failtext = "版本过低或未安装客户端";
//			} else if (msg.obj instanceof java.lang.Throwable && msg.obj.toString() != null
//					&& msg.obj.toString().contains("prevent duplicate publication")) {
//				failtext = "请稍后发送";
//			} else if (msg.obj.toString().contains("error")) {
//				failtext = "分享失败";
//			} else {
//				failtext = "分享失败";
//			}
//			application.showToast(failtext);
//			break;
//		case 2:
//			application.showToast("分享成功");
//			break;
//		}
//		if (popupWindow != null) {
//			popupWindow.dismiss();
//		}
//		return false;
//	}

	@Override
	public void onLikeCompleted(CommonBaseInfo response) {
		// TODO Auto-generated method stub
		likeBtn.setBackgroundResource(R.drawable.like_btn_selected);
		isLike = 1;
		Toast.makeText(mContext, "点赞成功", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStoreCompleted(CommonBaseInfo response) {
		// TODO Auto-generated method stub
		collectBtn.setBackgroundResource(R.drawable.collect_btn_selected);
		isCollect = 1;
		Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCancelLikeCompleted(CommonBaseInfo response) {
		// TODO Auto-generated method stub
		likeBtn.setBackgroundResource(R.drawable.like_btn_normal);
		isLike = 0;
		Toast.makeText(mContext, "点赞取消", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCancelStoreCompleted(CommonBaseInfo response) {
		// TODO Auto-generated method stub
		collectBtn.setBackgroundResource(R.drawable.collect_btn_normal);
		isCollect = 0;
		Toast.makeText(mContext, "收藏取消", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onFailed(CommonBaseInfo response) {
		// TODO Auto-generated method stub
		Toast.makeText(mContext, response.getReturnMsg(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNetError() {
		// TODO Auto-generated method stub
		Toast.makeText(mContext, "网络连接超时！", Toast.LENGTH_SHORT).show();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (mListViewTop!=null) {
			mListViewTop.stopScroll();
		}
		super.onDestroy();
	}
}
