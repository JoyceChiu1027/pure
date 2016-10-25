package com.bupt.chengde.adapter;

import java.util.List;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bupt.chengde.R;
import com.bupt.chengde.entity.ResponseAppUseGuide;
import com.bupt.chengde.util.GlideHelper;
import com.bupt.chengde.util.LogUtil;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GuideAdapter extends BaseAdapter {
    private static final String TAG=GuideAdapter.class.getSimpleName();
	private Context mContext;
	private List<ResponseAppUseGuide> mDatas;
	private LayoutInflater inflater;
//	private ImageLoader imageLoader;
//	private DisplayImageOptions options;
	public GuideAdapter(Context context, List<ResponseAppUseGuide> datas) {
		this.mContext = context;
		this.mDatas = datas;
		inflater = LayoutInflater.from(mContext);
//		imageLoader = ImageLoader.getInstance();
//		options = new DisplayImageOptions.Builder()
//				.cacheInMemory(true)
//				.cacheOnDisk(true)
//				.showImageForEmptyUri(R.drawable.default_ad_big)
//				.showImageOnLoading(R.drawable.default_ad_big)
//				.showImageOnFail(R.drawable.default_ad_big)
//				.build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		LogUtil.w(TAG, "getCount is called and count="+mDatas.size());
		return mDatas.size();
	}

	@Override
	public ResponseAppUseGuide getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		com.bupt.chengde.util.LogUtil.e(TAG, "getView mdatas.size="+mDatas.size());
		ViewHolder holder;
		if (convertView==null) {
			convertView=inflater.inflate(R.layout.item_guide, null);
			holder=new ViewHolder();
			holder.title=(TextView) convertView.findViewById(R.id.guide_title);
			holder.content=(TextView) convertView.findViewById(R.id.guide_content);
			holder.imageView=(ImageView) convertView.findViewById(R.id.guide_img);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		ResponseAppUseGuide data=mDatas.get(position);
		holder.title.setText(data.getGuideTitle());
		holder.content.setText(data.getGuideContent());
		GlideHelper.showImageWithUrl(mContext,data.getGuideUrl().trim(),holder.imageView);
		//imageLoader.displayImage(CodeConstants.PIC_URL_PREFIX+data.getGuideUrl().trim(), holder.img, options);
//		Glide.with(mContext)
//	     .load(CodeConstants.PIC_URL_PREFIX+data.getGuideUrl().trim())
//	     .placeholder(R.drawable.default_ad_big)
//	     .error(R.drawable.default_ad_big)
//	     .crossFade()
//	     .diskCacheStrategy(DiskCacheStrategy.RESULT)
//	     .into(holder.imageView);
		return convertView;
	}

	class ViewHolder {
		TextView title;
		TextView content;
		ImageView imageView;
	}

}
