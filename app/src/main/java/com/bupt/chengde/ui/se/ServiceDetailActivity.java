package com.bupt.chengde.ui.se;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

//import cn.sharesdk.framework.Platform;
//import cn.sharesdk.framework.PlatformActionListener;
//import cn.sharesdk.tencent.qq.QQClientNotExistException;
//import cn.sharesdk.wechat.utils.WechatClientNotExistException;

import com.bupt.chengde.R;
import com.bupt.chengde.control.AdTask;
import com.bupt.chengde.control.AsyncTaskCallback;
import com.bupt.chengde.control.CancelLikeStoreFunctionTask;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.control.LikeStoreFunctionTask;
import com.bupt.chengde.control.LikeStoreTaskCallback;
import com.bupt.chengde.entity.CommonBaseInfo;
import com.bupt.chengde.entity.ResponseAdPoster;
import com.bupt.chengde.entity.ResponseServiceDetail;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.LogUtil;
import com.bupt.chengde.util.Utils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.ListViewTop;
//import com.bupt.chengde.widget.SharePopupWindow;
//import com.umeng.socialize.ShareAction;
//import com.umeng.socialize.UMShareAPI;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.media.UMImage;

public class ServiceDetailActivity extends BaseActivity implements
        OnClickListener, LikeStoreTaskCallback/*,PlatformActionListener*/ {

    private static final String TAG = ServiceDetailActivity.class
            .getSimpleName();
    // 类型常量
    private static final int TYPE_LIKE = 1;
    private static final int TYPE_COLLECT = 2;

    //SharePopupWindow popupWindow = null;
    private WebView mWebView;
    private ImageButton commentBtn, shareBtn, collectBtn, likeBtn, dialBtn;
    private TextView titleTv, errorTxt, commentTv, addrTv, telTv;
    private ProgressBar progress_bar;
    private View refresh;// 刷新按钮
    private CustomFrameLayout customFrameLayout;
    private Context mContext;
    private LinearLayout operationLayout;
    private LinearLayout bianLayout;
    // 字符串变量，存放返回的string或传递的参数
    private String url = "";
    private String custName;
    private String custId;
    private String modId;
    private String busiId;
    private String moduleType;
    private String brief = "";
    private String title = "";
    private String titleImageUrl = "";
    // 状态变量
    private int isLike = 0;// 0为未点赞状态 1为点赞状态
    private int isCollect = 0;// 0为未收藏状态1为收藏状态
    // private CommonBaseInfo response;
    // private ResponseTravelDetail responseDetail;
    private ResponseServiceDetail responseDetail;
    private AlertDialog dialog;
    private LikeStoreFunctionTask likeTask;
    private CancelLikeStoreFunctionTask cancelLikeTask;

    // 用于显示小广告
    private ListViewTop mListViewTop;
    private LinearLayout adConLayout;
    private AdTask task;
    private List<ResponseAdPoster> adReturnList = new ArrayList<ResponseAdPoster>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        com.bupt.chengde.util.LogUtil.e(TAG, "onCreate is called");
        setContentView(R.layout.activity_service_detail_layout);
        mContext = ServiceDetailActivity.this;
        custId = application.getCustID();
        custName = application.getCustName();
        modId = getIntent().getStringExtra(CodeConstants.MODULE_ID);
        moduleType = getIntent().getStringExtra(CodeConstants.MODULE_TYPE);
        busiId = getIntent().getStringExtra(CodeConstants.BUSI_ID);
        titleImageUrl = getIntent().getStringExtra(CodeConstants.IMG_URL_KEY);
        title = getIntent().getStringExtra(CodeConstants.TITLE_KEY);
        brief = getIntent().getStringExtra(CodeConstants.BRIEF_KEY);
        com.bupt.chengde.util.LogUtil.i(TAG, "modid=" + modId + "  moduletype=" + moduleType + " title=" + title + " brief=" + brief);
        initView();
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
        task.execute(moduleType);
        runHandler();
    }

    private void initView() {
        titleTv = (TextView) findViewById(R.id.top_name);
        titleTv.setText("详情");
        operationLayout = (LinearLayout) findViewById(R.id.operation_layout);
        adConLayout = (LinearLayout) findViewById(R.id.ad_con_layout);
        errorTxt = (TextView) findViewById(R.id.error_txt);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        commentBtn = (ImageButton) findViewById(R.id.comments_btn);
        shareBtn = (ImageButton) findViewById(R.id.share_btn);
        collectBtn = (ImageButton) findViewById(R.id.collect_btn);
        likeBtn = (ImageButton) findViewById(R.id.like_btn);
        commentTv = (TextView) findViewById(R.id.comment_bg);
        customFrameLayout = (CustomFrameLayout) findViewById(R.id.web_frame);
        customFrameLayout.setList(new int[]{R.id.common_web,
                R.id.common_net_error});
        refresh = findViewById(R.id.error_btn);
        errorTxt = (TextView) findViewById(R.id.error_txt);
        mWebView = (WebView) findViewById(R.id.common_web);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        MyWebviewClient mWebClient = new MyWebviewClient();
        mWebView.setWebChromeClient(mWebClient);
        mWebView.setWebViewClient(new WebViewClient());
        if (CodeConstants.BEN.equals(moduleType)) {
            bianLayout = (LinearLayout) findViewById(R.id.bian_layout);
            addrTv = (TextView) findViewById(R.id.bian_addr);
            telTv = (TextView) findViewById(R.id.bian_tel);
            dialBtn = (ImageButton) findViewById(R.id.bian_dial_iv);
        }
        initListener();
    }

    private void initListener() {
        commentBtn.setOnClickListener(this);
        collectBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);
        likeBtn.setOnClickListener(this);
        commentTv.setOnClickListener(this);
        refresh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                customFrameLayout.show(R.id.common_web);

            }
        });
        findViewById(R.id.top_back).setOnClickListener(this);
    }

    @Override
    protected void doBackGround() throws Exception {
        if (TextUtils.isEmpty(custId) || custId.equals("null")) {
            custId = "";
        }
        responseDetail = null;
        if (CodeConstants.NEWS.equals(moduleType)) {
           // responseDetail = WebserviceUtils.getNewsDetail(busiId, custId, modId);
        } else if (CodeConstants.BEN.equals(moduleType)) {
          //  responseDetail = WebserviceUtils.getBenPeoById(custId, busiId, modId);
        } else if (CodeConstants.TRAVEL.equals(moduleType)) {
          //  responseDetail = WebserviceUtils.getTravelDetail(busiId, custId, modId);
        } else if (CodeConstants.PHOTO.equals(moduleType)) {
          //  responseDetail = WebserviceUtils.getPhotoDetail(busiId, custId,  modId);
        } else if (CodeConstants.MAIN_AD.equals(moduleType)) {
           // responseDetail = WebserviceUtils.getMainDetailInfo(custId, modId, busiId);
        } else {
            responseDetail = new ResponseServiceDetail();
            responseDetail.setReturnCode(-1);
            responseDetail.setReturnMessage("系统错误！");
        }
    }

    @Override
    protected void doForeGround() throws Exception {
        if (responseDetail != null) {
            if (responseDetail.getReturnCode() == 0) {
                if (CodeConstants.BEN.equals(moduleType)) {
                    if (!TextUtils.isEmpty(responseDetail.getAddress()) && !responseDetail.getAddress().equals("null")) {
                        addrTv.setText("地址：" + responseDetail.getAddress());

                    }
                    if (!TextUtils.isEmpty(responseDetail.getPhone())
                            && !responseDetail.getPhone().equals("null")) {
                        telTv.setText("电话：" + responseDetail.getPhone());
                    }
                    bianLayout.setVisibility(View.VISIBLE);
                    dialBtn.setOnClickListener(this);
                }


                isLike = responseDetail.getIsLike();
                isCollect = responseDetail.getIsStore();
                url = responseDetail.getUrl();
                if (!TextUtils.isEmpty(url) && !url.equals("null")) {
                    url = CodeConstants.HTML_URL_PREFIX + url;
                }
                //url="http://cnews.chinadaily.com.cn/2016-04/16/content_24591048.html";
                setDrawableRes();
            } else {

                Toast.makeText(mContext, responseDetail.getReturnMessage() + "", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(mContext, "网络请求超时", Toast.LENGTH_SHORT).show();
        }
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

            case R.id.comments_btn:
                toCommentListActivity();
                break;

            case R.id.comment_bg:
                createCommentActivity();
                break;

            case R.id.like_btn:

                likeFunction();
                break;

            case R.id.collect_btn:

                storeFunction();

                break;
            case R.id.share_btn:
              //share();
            /*if (!URLUtil.isValidUrl(url)) {
                application.showToast("没有可分享的地址！");
				return;
			}*/
                // share();
                //application.showToast("该功能稍后上线");
                break;
            case R.id.bian_dial_iv:
                showCallDialog(telTv.getText().toString().trim());
                break;
            default:

                break;

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

    private void setDrawableRes() {
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

    // 操作点赞按钮前先验证是否登录
    private void likeFunction() {
        int type = TYPE_LIKE;
        com.bupt.chengde.util.LogUtil.d(TAG, "application.isLogin()=" + application.isLogin());
        if (application.isLogin()) {
            if (isLike == 0) {// 为0时表示现在是未点赞状态，点击按钮发送点赞请求
                likeTask = new LikeStoreFunctionTask(activity, type);
                likeTask.execute(custName, custId, modId, busiId);
                likeTask.setCallback(this);
            } else {
                cancelLikeTask = new CancelLikeStoreFunctionTask(activity, type);
                cancelLikeTask.execute(custId, modId, busiId);
                cancelLikeTask.setCallback(this);
            }

        } else {
            Toast.makeText(mContext, "操作前请登录", Toast.LENGTH_SHORT).show();
        }
    }

    private void storeFunction() {
        int type = TYPE_COLLECT;
        if (application.isLogin()) {
            if (isCollect == 0) {
                likeTask = new LikeStoreFunctionTask(activity, type);
                likeTask.execute(custName, custId, modId, busiId);
                likeTask.setCallback(this);
            } else {
                cancelLikeTask = new CancelLikeStoreFunctionTask(activity, type);
                cancelLikeTask.execute(custId, modId, busiId);
                cancelLikeTask.setCallback(this);
            }
        } else {
            Toast.makeText(mContext, "操作前请登录", Toast.LENGTH_SHORT).show();
        }
    }

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
        Toast.makeText(mContext, response.getReturnMsg(), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onNetError() {
        // TODO Auto-generated method stub
        Toast.makeText(mContext, "网络连接超时！", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        //UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        com.bupt.chengde.util.LogUtil.d("result", "onActivityResult");
    }

//    private void share() {
//        new ShareAction(ServiceDetailActivity.this)
//                .setDisplayList( SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN_FAVORITE,
//                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,SHARE_MEDIA.SINA, SHARE_MEDIA.SMS, SHARE_MEDIA.EMAIL)
//                .withTitle(getResources().getString(R.string.app_name))
//                .withText(title+"——来自承德96888的分享")
//                .withMedia(new UMImage(mContext,CodeConstants.PIC_URL_PREFIX+titleImageUrl))
//                .withTargetUrl(url)
//                .open();
//
//    }

    class MyWebviewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progress_bar.setVisibility(View.GONE);
                // 判断有无网络
                if (!Utils.isNetworkAvailable(ServiceDetailActivity.this)) {
                    customFrameLayout.show(R.id.common_net_error);
                    refresh.setVisibility(View.VISIBLE);
                    refresh.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // mWebView.loadUrl(url);
                            mWebView.loadUrl(url);
                        }
                    });
                } else {
                    // 判断网络请求网址是否有效
                    if (!URLUtil.isValidUrl(url)) {
                        LogUtil.d(TAG, "url无效时url=" + url);
                        customFrameLayout.show(R.id.common_net_error);
                        errorTxt.setText("无效网址");
                    } else {
                        operationLayout.setVisibility(View.VISIBLE);
                        customFrameLayout.show(R.id.common_web);
                    }
                }
            } else {
                if (newProgress >= 60)
                    operationLayout.setVisibility(View.VISIBLE);
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