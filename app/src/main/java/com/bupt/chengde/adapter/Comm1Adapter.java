package com.bupt.chengde.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bupt.chengde.R;
import com.bupt.chengde.util.GlideHelper;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;

public class Comm1Adapter extends BaseAdapter{
	private List<String> titleList;
	private Context mContext;
	private List<String> urlList;
	LayoutInflater inflater;
//	private ImageLoader imageLoader;
//	private DisplayImageOptions options;
	
	public Comm1Adapter(List<String> titleList,List<String> urlList,Context context){
		this.mContext = context;
		this.titleList = titleList;
		this.urlList=urlList;
		inflater = LayoutInflater.from(mContext);
//		imageLoader = ImageLoader.getInstance();
//		options = new DisplayImageOptions.Builder()
//				.cacheInMemory(true)
//				.cacheOnDisk(true)
//				.showImageOnLoading(R.drawable.default_title)
//				.showImageForEmptyUri(R.drawable.default_title)
//				.showImageOnFail(R.drawable.default_title)
//				.build();
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder holder = null;
		if (convertView == null) {
			holder = new viewHolder();
			convertView = inflater.inflate(R.layout.comm_item1, null,false);
			holder.titleTextView = (TextView) convertView.findViewById(R.id.comm_title);
			holder.imageView = (ImageView) convertView.findViewById(R.id.comm_img);
			convertView.setTag(holder);
		}else {
			holder = (viewHolder) convertView.getTag();
		}
		holder.titleTextView.setText(titleList.get(position));
		GlideHelper.showImageWithUrl(mContext,urlList.get(position),holder.imageView);

		return convertView;
	}

	public class viewHolder {
		private TextView titleTextView;
		private ImageView imageView;
	}
	
}
