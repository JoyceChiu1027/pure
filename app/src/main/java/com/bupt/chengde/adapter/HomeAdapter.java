package com.bupt.chengde.adapter;

import java.util.List;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.HomeBean;
import com.bupt.chengde.util.GlideHelper;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeAdapter extends BaseAdapter {

	private Context mContext;
	private List<HomeBean> mBeans;
	
	LayoutInflater inflater;
	viewHolder holder;
//	private ImageLoader imageLoader;
//	private DisplayImageOptions options;
	public HomeAdapter(Context context, List<HomeBean> beans) {
		super();
		this.mContext = context;
		this.mBeans = beans;
		inflater = LayoutInflater.from(mContext);
//		imageLoader = ImageLoader.getInstance();
//		options = new DisplayImageOptions.Builder().cacheInMemory(true)
//				.cacheOnDisk(true)
//				.showImageOnLoading(R.drawable.default_title)
//				.showImageForEmptyUri(R.drawable.default_title)
//				.showImageOnFail(R.drawable.default_title).build();
	}

	@Override
	public int getCount() {
		return mBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return mBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		holder = null;
		if (convertView == null) {
			holder = new viewHolder();
			convertView = inflater.inflate(R.layout.home_item, null);
			holder.imageView = (ImageView) convertView.findViewById(R.id.home_logo);
			holder.title = (TextView) convertView.findViewById(R.id.home_title);
			holder.detial = (TextView) convertView.findViewById(R.id.home_de);
			holder.time = (TextView) convertView.findViewById(R.id.home_time);
			convertView.setTag(holder);
		}else {
			holder = (viewHolder) convertView.getTag();
		}
		
		//holder.logoImageView.setImageResource(mBeans.get(position).getImgUrl());
		//imageLoader.displayImage(CodeConstants.PIC_URL_PREFIX+mBeans.get(position).getImgUrl(), holder.logoImageView, options);
		holder.title.setText(mBeans.get(position).getTitle());
		holder.time.setText(mBeans.get(position).getTime());
		if (mBeans.get(position).getDetail().equals("无摘要 ！")) {
			holder.detial.setVisibility(View.GONE);
		} else {
			holder.detial.setVisibility(View.VISIBLE);
			holder.detial.setText(mBeans.get(position).getDetail());
		}
		GlideHelper.showImageWithUrl(mContext,mBeans.get(position).getImgUrl(),holder.imageView);

//		Glide.with(mContext)
//	     .load(CodeConstants.PIC_URL_PREFIX+mBeans.get(position).getImgUrl())
//	     .placeholder(R.drawable.default_title)
//	     .error(R.drawable.default_title)
//	     .crossFade()
//	     .diskCacheStrategy(DiskCacheStrategy.RESULT)
//	     .into(holder.imageView);
		return convertView;
	}

	public class viewHolder{
		private ImageView imageView;
		private TextView title,detial,time;
	}
	
}
