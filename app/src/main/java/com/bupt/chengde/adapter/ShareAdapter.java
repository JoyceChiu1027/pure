package com.bupt.chengde.adapter;

import com.bupt.chengde.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 分享item
 */
public class ShareAdapter extends BaseAdapter {
    // 显示字
    private static String[] shareNames = new String[]{"QQ好友", "QQ空间", "微信好友", "朋友圈", "新浪微博","腾讯微博","邮件","短信"};
    // 显示logo
    private int[] shareIcons = new int[]{R.drawable.my_qq, R.drawable.my_qzone,
            R.drawable.my_wechat, R.drawable.my_wechatmoments, R.drawable.my_sinaweibo,R.drawable.my_tencentweibo,R.drawable.my_email,R.drawable.my_shortmessage};
    // 布局
    private LayoutInflater inflater;

//    private boolean isClick = false;

    public ShareAdapter(Context context) {
        inflater = LayoutInflater.from(context);
//        if (Utils.isApkInstalled(context, AppConstant.WEIXIN_NAME)) {
//            shareIcons[2] = R.drawable.third_weixin;
//            shareIcons[3] = R.drawable.third_wxfriend;
//            isClick = true;
//        } else {
//            shareIcons[2] = R.drawable.third_weixin_no;
//            shareIcons[3] = R.drawable.third_wxfriend_no;
//            isClick = false;
//        }
    }

    @Override
    public int getCount() {
        return shareNames.length;
    }

    @Override
    public Object getItem(int position) {
        return shareNames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    @Override
//    public boolean isEnabled(int position) {
//        if (position == 2 || position == 3) {
//            if (isClick) {
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            return true;
//        }
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_grid_share_item, parent, false);
        }
        ImageView shareIcon = (ImageView) convertView.findViewById(R.id.share_icon);
        TextView shareTitle = (TextView) convertView.findViewById(R.id.share_title);
        shareIcon.setImageResource(shareIcons[position]);
        shareTitle.setText(shareNames[position]);
        return convertView;
    }
}
