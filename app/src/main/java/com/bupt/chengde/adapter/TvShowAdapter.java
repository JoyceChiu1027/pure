package com.bupt.chengde.adapter;

import java.util.List;
import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseBusinessInfo;
import com.bupt.chengde.util.GlideHelper;
//import com.bupt.chengde.util.WebserviceUtils;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TvShowAdapter extends BaseAdapter {

	private List<ResponseBusinessInfo> list;
	private Context mContext;

	LayoutInflater inflater;
//	ImageLoader imageLoader;
//	DisplayImageOptions options;

	public TvShowAdapter(List<ResponseBusinessInfo> list, Context context) {
		super();
		this.list = list;
		this.mContext = context;
		inflater = LayoutInflater.from(mContext);
//		// 创建默认的ImageLoader配置参数
//		ImageLoaderConfiguration configuration = ImageLoaderConfiguration
//				.createDefault(mcontext);
//		// Initialize ImageLoader with configuration.
//		ImageLoader.getInstance().init(configuration);
//		imageLoader = ImageLoader.getInstance();
//		options = new DisplayImageOptions.Builder()
//		         .cacheInMemory(true)
//				.cacheOnDisk(true)
//				.showImageOnLoading(R.drawable.default_title)
//				.showImageForEmptyUri(R.drawable.default_title)
//				.showImageOnFail(R.drawable.default_title).build();
	
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.tv_item, null, false);
			holder.imageView = (ImageView) convertView.findViewById(R.id.showImg);
			holder.tetileTextView = (TextView) convertView
					.findViewById(R.id.title);
			holder.detilTextView = (TextView) convertView.findViewById(R.id.detil);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ResponseBusinessInfo rTvBusiness = list.get(position);
		holder.tetileTextView.setText(rTvBusiness.getBusTitle());
		holder.detilTextView.setVisibility(View.GONE);
		//viewHolder.detilTextView.setText(rTvBusiness.getBusContent());
	//	imageLoader.displayImage(CodeConstants.PIC_URL_PREFIX+ rTvBusiness.getBusPicUrl(),viewHolder.imageView, options);
		GlideHelper.showImageWithUrl(mContext,rTvBusiness.getBusPicUrl(),holder.imageView);

//		Glide.with(mContext)
//	     .load(CodeConstants.PIC_URL_PREFIX+rTvBusiness.getBusPicUrl())
//	     .placeholder(R.drawable.default_title)
//	     .error(R.drawable.default_title)
//	     .crossFade()
//	     .diskCacheStrategy(DiskCacheStrategy.ALL)
//	     .into(holder.imageView);

		return convertView;
	}

	class ViewHolder {
		private ImageView imageView;
		private TextView tetileTextView;
		private TextView detilTextView;
	}

}
