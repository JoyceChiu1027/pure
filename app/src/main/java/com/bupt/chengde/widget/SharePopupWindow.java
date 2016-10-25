package com.bupt.chengde.widget;

//import java.util.logging.LogManager;
//
//import org.kobjects.util.Util;
//
//import com.bupt.chengde.R;
//import com.bupt.chengde.adapter.ShareAdapter;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.ColorDrawable;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.GridView;
//import android.widget.LinearLayout;
import android.widget.PopupWindow;
//import android.widget.Space;
//import android.widget.Toast;
//
//import cn.sharesdk.framework.Platform;
//import cn.sharesdk.framework.Platform.ShareParams;
//import cn.sharesdk.framework.PlatformActionListener;
//import cn.sharesdk.framework.ShareSDK;
//import cn.sharesdk.sina.weibo.SinaWeibo;
//import cn.sharesdk.system.email.Email;
//import cn.sharesdk.system.text.ShortMessage;
//import cn.sharesdk.tencent.qq.QQ;
//import cn.sharesdk.tencent.qzone.QZone;
//import cn.sharesdk.tencent.weibo.TencentWeibo;
//import cn.sharesdk.wechat.friends.Wechat;
//import cn.sharesdk.wechat.moments.WechatMoments;
/**
 * 分享对话框
 */
public class SharePopupWindow extends PopupWindow {

//    private Context context;
//    private PlatformActionListener platformActionListener;
//    private ShareParams shareParams;
//
//    public SharePopupWindow(Context cx) {
//        this.context = cx;
//    }
//
//    public PlatformActionListener getPlatformActionListener() {
//        return platformActionListener;
//    }
//
//    public void setPlatformActionListener(
//            PlatformActionListener platformActionListener) {
//        this.platformActionListener = platformActionListener;
//    }
//
//    @SuppressLint("InflateParams")
//	public void showShareWindow() {
//        View view = LayoutInflater.from(context).inflate(R.layout.layout_share_layout,
//                null);
//        GridView gridView = (GridView) view.findViewById(R.id.share_gridview);
//        ShareAdapter adapter = new ShareAdapter(context);
//        gridView.setAdapter(adapter);
//
//        LinearLayout btn_cancel = (LinearLayout) view.findViewById(R.id.btn_cancel);
//        // 取消按钮
//        btn_cancel.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                // 销毁弹出框
//                dismiss();
//            }
//        });
//
//        // 设置SelectPicPopupWindow的View
//        this.setContentView(view);
//        // 设置SelectPicPopupWindow弹出窗体的宽
//        this.setWidth(LayoutParams.MATCH_PARENT);
//        // 设置SelectPicPopupWindow弹出窗体的高
//        this.setHeight(LayoutParams.WRAP_CONTENT);
//        // 设置SelectPicPopupWindow弹出窗体可点击
//        this.setFocusable(true);
//        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.PopupAnimation);
//        // 实例化一个ColorDrawable颜色为半透明
//        // ColorDrawable dw = new ColorDrawable(0xb0000000);
//        // 设置白底背景
//        ColorDrawable dw = new ColorDrawable(0xffffffff);
//        // 设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);
//        view.setBackgroundDrawable(dw);
//        gridView.setOnItemClickListener(new ShareItemClickListener(this));
//    }
//
//    private class ShareItemClickListener implements OnItemClickListener {
//        private PopupWindow pop;
//
//        public ShareItemClickListener(PopupWindow pop) {
//            this.pop = pop;
//        }
//
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position,
//                                long id) {
//            share(position);
//            pop.dismiss();
//        }
//    }
//
//    /**
//     * 分享
//     *
//     * @param position
//     */
//    public void share(int position) {
//        if (position == 0) {
//            qqShare(position);
//        } else if (position == 1) {
//            qqShare(position);
//        } else if (position == 2) {
//        	weiXinShare(position);
//        } else if (position == 3) {
//        	weiXinShare(position);
//        } else if (position == 4) {
//            weiBoShare(position);
//        } else if (position == 5) {
//			weiBoShare(position);
//		}else if (position == 6) {
//			weiXinOtherShare(position);
//		}else if (position == 7) {
//			weiXinOtherShare(position);
//		}
//    }
//
//    /**
//     * 初始化分享参数
//     */
//    public void initShareParams(String title, String content, String imagePath, String url) {
//        ShareParams sp = new ShareParams();
//        sp.setShareType(Platform.SHARE_TEXT);
//        sp.setShareType(Platform.SHARE_WEBPAGE);
//        sp.setTitle(title);
//        sp.setText(content);
//        sp.setUrl(url);
//        sp.setImageUrl(imagePath);
//        shareParams = sp;
//    }
//
//    /**
//     * 获取平台
//     *
//     * @param position
//     * @return
//     */
//    private String getPlatform(int position) {
//        String platform = "";
//        switch (position) {
//            case 0:
//                platform = QQ.NAME;
//                break;
//            case 1:
//                platform = QZone.NAME;
//                break;
//            case 2:
//                platform = Wechat.NAME;
//                break;
//            case 3:
//                platform = WechatMoments.NAME;
//                break;
//            case 4:
//                platform = SinaWeibo.NAME;
//                break;
//            case 5:
//                platform = TencentWeibo.NAME;
//                break;
//            case 6:
//                platform = Email.NAME;
//                break;
//            case 7:
//                platform = ShortMessage.NAME;
//                break;
//        }
//        return platform;
//    }
//
//
//
//    /**
//     * qq和qq空间的分享
//     */
//    private void qqShare(int position) {
//        ShareParams sp = new ShareParams();
//        sp.setTitle(shareParams.getTitle());
//        // 标题的超链接
//        sp.setTitleUrl(shareParams.getUrl());
//        sp.setText(shareParams.getText());
//        sp.setImageUrl(shareParams.getImageUrl());
//        sp.setComment("我对此分享内容的评论");
//        sp.setSite(shareParams.getTitle());
//        sp.setSiteUrl(shareParams.getUrl());
//        Platform platform = ShareSDK.getPlatform(context, getPlatform(position));
//        platform.SSOSetting(false);
//        if (platformActionListener != null) {
//            platform.setPlatformActionListener(platformActionListener);
//        }
//        platform.share(sp);
//    }
//
//
//    /**
//     * 微博分享
//     */
//    private void weiBoShare(int position) {
//        Platform plat;
//        plat = ShareSDK.getPlatform(context, getPlatform(position));
//        plat.SSOSetting(false);
//        if (platformActionListener != null) {
//            plat.setPlatformActionListener(platformActionListener);
//        }
//        ShareParams shareParams = new ShareParams();
//        shareParams.setShareType(Platform.SHARE_TEXT);
//        shareParams.setShareType(Platform.SHARE_WEBPAGE);
//        shareParams.setTitle(this.shareParams.getTitle());
//        shareParams.setTitleUrl(this.shareParams.getUrl());
//        shareParams.setText(this.shareParams.getTitle() + this.shareParams.getUrl());
//        shareParams.setUrl(this.shareParams.getUrl());
//        shareParams.setImageUrl(this.shareParams.getImageUrl());
//        plat.share(shareParams);
//    }
//
//    /**
//     * 微信另种分享
//     */
//    private void weiXinOtherShare(int position) {
//        Platform plat;
//        plat = ShareSDK.getPlatform(context, getPlatform(position));
//        plat.SSOSetting(false);
//        if (platformActionListener != null) {
//            plat.setPlatformActionListener(platformActionListener);
//        }
//        plat.share(shareParams);
//    }
//    /**
//     * 微信分享
//     */
//    private void weiXinShare(int position) {
//        Platform plat;
//        plat = ShareSDK.getPlatform(context, getPlatform(position));
//        plat.SSOSetting(false);
//        if (platformActionListener != null) {
//            plat.setPlatformActionListener(platformActionListener);
//        }
//        ShareParams sp1 = new ShareParams();
////        sp1.setShareType(Platform.SHARE_TEXT);
//        sp1.setShareType(Platform.SHARE_WEBPAGE);
//        sp1.setTitle("咱的分享"); // 标题的超链接
////        sp1.setTitleUrl("http://www.baidu.com");
//        sp1.setText("分享的文档");//内容
//        sp1.setImageUrl(this.shareParams.getImageUrl());//设置图片
//        sp1.setUrl(this.shareParams.getUrl());
//        plat.share(sp1);
//    }
//    /**
//     * 短信分享
//     */
//    private void shortMessageShare(int position) {
//    	ShareParams sp = new ShareParams();
//        sp.setTitle(shareParams.getTitle());
//        // 标题的超链接
//        sp.setTitleUrl(shareParams.getUrl());
//        sp.setText(shareParams.getText());
//        sp.setImageUrl(shareParams.getImageUrl());
//        sp.setComment("我对此分享内容的评论");
//        sp.setSite(shareParams.getTitle());
//        sp.setSiteUrl(shareParams.getUrl());
//        Platform platform = ShareSDK.getPlatform(context, getPlatform(position));
//        platform.SSOSetting(false);
//        if (platformActionListener != null) {
//            platform.setPlatformActionListener(platformActionListener);
//        }
//        platform.share(sp);
//    }
}
