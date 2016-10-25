package com.bupt.chengde.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.util.GlideHelper;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;

public class Comm2Adapter extends BaseAdapter {
	private List<String> titleList;
	private Context mContext;
	private List<String> urlList;
	LayoutInflater inflater;
	//private ImageLoader imageLoader;
	//private DisplayImageOptions options;

	public Comm2Adapter(List<String> titleList, List<String> urlList,
			Context context) {
		this.mContext = context;
		this.titleList = titleList;
		this.urlList = urlList;
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
		return titleList.size();
	}

	@Override
	public Object getItem(int position) {

		return titleList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder holder = null;
		if (convertView == null) {
			holder = new viewHolder();
			convertView = inflater.inflate(R.layout.comm_item2, null, false);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.comm_title2);
			holder.imageView = (ImageView) convertView.findViewById(R.id.comm_img2);
			convertView.setTag(holder);
		}else {
			holder = (viewHolder) convertView.getTag();
		}
		holder.tvTitle.setText(titleList.get(position));
		GlideHelper.showImageWithUrl(mContext,urlList.get(position),holder.imageView);
		return convertView;
	}

	public class viewHolder {
		private TextView tvTitle;
		private ImageView imageView;
	}
}
