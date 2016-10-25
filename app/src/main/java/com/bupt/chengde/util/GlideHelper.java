package com.bupt.chengde.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;

/**
 * Created by zhaojie on 16/10/10.
 */

public class GlideHelper {

    public static void showAvatarWithUrl(@Nullable Context context, String url, @Nullable ImageView imageView) {
        if (url.startsWith("/bjcd")){
            Glide.with(context)
                    .load(CodeConstants.URL_PREFIX + url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.default_head_icon)
                    .error(R.drawable.default_head_icon)
                    .transform(new GlideCircleTransform(context))
                    .into(imageView);

        }else{
            Glide.with(context)
                    .load(CodeConstants.PIC_URL_PREFIX + url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.default_head_icon)
                    .error(R.drawable.default_head_icon)
                    .transform(new GlideCircleTransform(context))
                    .into(imageView);

        }
    }
     public static void showImageWithUrl(@Nullable Context context, String url, @Nullable ImageView imageView){
         Glide.with(context)
                 .load(CodeConstants.PIC_URL_PREFIX + url)
                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                 .placeholder(R.drawable.default_title)
                 .error(R.drawable.default_title)
                 .dontAnimate()
                 .into(imageView);

     }
    public static void stopLoadImg(Context context){
        Glide.with(context).pauseRequests();
    }
}
