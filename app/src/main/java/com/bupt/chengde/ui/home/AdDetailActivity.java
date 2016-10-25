package com.bupt.chengde.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseAdPoster;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.Utils;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;

public class AdDetailActivity extends BaseActivity implements OnClickListener {
	private View refresh;// 刷新按钮
	private TextView errorTxt;
	private CustomFrameLayout customFrameLayout;
	private WebView mWebView;
	private ProgressBar progress_bar;
	private TextView titleTxt;
	private String url="";
/*
	ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.default_ad_big)
			.showImageOnFail(R.drawable.default_ad_big)
			.resetViewBeforeLoading(true)
			.cacheOnDisc(true).build();
*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ad_detail);
		url=CodeConstants.HTML_URL_PREFIX+getIntent().getStringExtra(CodeConstants.URL);
		initView();
	}

	private void initView() {
		titleTxt = (TextView) findViewById(R.id.top_name);
		titleTxt.setText("广告");
		findViewById(R.id.top_back).setOnClickListener(this);
		
		errorTxt = (TextView) findViewById(R.id.error_txt);
		progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
		customFrameLayout = (CustomFrameLayout) findViewById(R.id.web_frame);
		customFrameLayout.setList(new int[] { R.id.common_web, R.id.common_net_error });
		refresh = findViewById(R.id.error_btn);
		mWebView = (WebView) findViewById(R.id.common_web);
		mWebView.getSettings().setDefaultTextEncodingName("utf-8");
		mWebView.getSettings().setJavaScriptEnabled(true);
		//mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		MyWebviewClient mWebClient = new MyWebviewClient();
		mWebView.setWebChromeClient(mWebClient);
		mWebView.setWebViewClient(new WebViewClient());
		mWebView.loadUrl(url);
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
	class MyWebviewClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				progress_bar.setVisibility(View.GONE);
				// 判断有无网络
				if (!Utils.isNetworkAvailable(AdDetailActivity.this)) {
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
}
