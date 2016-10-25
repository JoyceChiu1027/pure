package com.bupt.chengde.adapter;

import java.util.List;

import com.bupt.chengde.R;
import com.bupt.chengde.entity.ResponseMacBusiness;
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

/**
 * @author wyf
 * @类 :宽带列表显示
 * @version 1.0
 */
public class MacBusinessAdapter extends BaseAdapter {

	private Context mContext;
	private List<ResponseMacBusiness> list;
	
	LayoutInflater inflater;
//	ImageLoader imageLoader;
//	DisplayImageOptions options;
	
	public MacBusinessAdapter(Context context, List<ResponseMacBusiness> list) {
		super();
		this.mContext = context;
		this.list = list;
		inflater = LayoutInflater.from(mContext);
//		imageLoader = ImageLoader.getInstance();
//		options = new DisplayImageOptions.Builder().cacheInMemory(true)
//				.cacheOnDisc(true).showImageForEmptyUri(R.drawable.default_title)
//				.showStubImage(R.drawable.default_title)
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
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.tv_item, null,false);
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.showImg);
			viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.title);
			viewHolder.briefTextView = (TextView) convertView.findViewById(R.id.detil);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ResponseMacBusiness rTvBusiness = list.get(position);
		viewHolder.titleTextView.setText(rTvBusiness.getMbTitle());
		viewHolder.briefTextView.setText(rTvBusiness.getSubContent());
		GlideHelper.showImageWithUrl(mContext,rTvBusiness.getMbPicUrl(),viewHolder.imageView);

		//imageLoader.displayImage(WebserviceUtils.PIC_URL+list.get(arg0).getMbPicUrl(), viewHolder.imageView, options);
		return convertView;
	}

	class ViewHolder {
		private ImageView imageView;
		private TextView titleTextView;
		private TextView briefTextView;
	}
	
}
