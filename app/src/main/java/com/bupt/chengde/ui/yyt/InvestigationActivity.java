package com.bupt.chengde.ui.yyt;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.Utils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;

public class InvestigationActivity extends BaseActivity implements
		OnClickListener {
	private WebView mWebView;
	private PullToRefreshWebView mPullRefreshWebView;
	private TextView titleTv, errorTxt;
	private ProgressBar progress_bar;
	private View refresh;// 刷新按钮
	private CustomFrameLayout customFrameLayout;
	//private String url = "http://news.sohu.com/20160418/n444734655.shtml";
    private String url=CodeConstants.HTML_URL_PREFIX+"/OnlineSuvey.html";
	String custId="";
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_investigation);
		//url=getIntent().getStringExtra("url");
		//custId=application.getCustID();
		initView();
	}

	private void initView() {
		titleTv = (TextView) findViewById(R.id.top_name);
		titleTv.setText("在线调查");
		errorTxt = (TextView) findViewById(R.id.error_txt);
		progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
		customFrameLayout = (CustomFrameLayout) findViewById(R.id.web_frame);
		customFrameLayout.setList(new int[] { R.id.common_web,
				R.id.common_net_error });
		refresh = findViewById(R.id.error_btn);
		errorTxt = (TextView) findViewById(R.id.error_txt);
		mPullRefreshWebView = (PullToRefreshWebView) findViewById(R.id.common_web);
	
		mWebView = mPullRefreshWebView.getRefreshableView();
		//mWebView = (WebView) findViewById(R.id.common_web);
		mWebView.getSettings().setDefaultTextEncodingName("utf-8");
		mWebView.getSettings().setJavaScriptEnabled(true);
		MyWebviewClient mWebClient = new MyWebviewClient();
		mWebView.setWebChromeClient(mWebClient);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		/*url=url+"?uid="+custId;*/
		mWebView.loadUrl(url);
		// 设置Web视图
		findViewById(R.id.top_back).setOnClickListener(
				InvestigationActivity.this);

	}
	class MyWebviewClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				progress_bar.setVisibility(View.GONE);
				mPullRefreshWebView.onRefreshComplete();
				// 判断有无网络
				if (!Utils.isNetworkAvailable(InvestigationActivity.this)) {
					customFrameLayout.show(R.id.common_net_error);
					refresh.setVisibility(View.VISIBLE);
					refresh.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							mWebView.loadUrl(url);
						}
					});
				} else {
					// 判断网络请求网址是否有效
					if (!URLUtil.isValidUrl(url)) {
						customFrameLayout.show(R.id.common_net_error);
						errorTxt.setText("无效网址");
					} else {
						customFrameLayout.show(R.id.common_web);
					}
				}
			} else {
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
		default:

			break;
		}
	}
}
