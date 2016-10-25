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
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bupt.chengde.R;
import com.bupt.chengde.util.GlideHelper;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;

public class Comm3Adapter extends BaseAdapter{
	private List<String> titleList;
	private List<String> detailList;
	private Context mContext;
	private List<String> picUrlList;
	LayoutInflater inflater;
	//private ImageLoader imageLoader;
	//private DisplayImageOptions options;
	
	public Comm3Adapter(List<String> titleList,List<String> detailList,List<String> picUrlList,Context context){
		this.mContext = context;
		this.titleList = titleList;
		this.detailList = detailList;
		this.picUrlList=picUrlList;
		
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder holder = null;
		if (convertView == null) {
			holder = new viewHolder();
			convertView = inflater.inflate(R.layout.comm_item3, null,false);
			holder.titleTextView = (TextView) convertView.findViewById(R.id.comm_title);
			holder.detailTextView = (TextView) convertView.findViewById(R.id.comm_detail);
			holder.imageView = (ImageView) convertView.findViewById(R.id.comm_img);
			convertView.setTag(holder);
		}else {
			holder = (viewHolder) convertView.getTag();
		}
		holder.titleTextView.setText(titleList.get(position));
		if (detailList.get(position).equals("null")) {
			holder.detailTextView.setVisibility(View.GONE);
		} else {
			holder.detailTextView.setVisibility(View.VISIBLE);			
			holder.detailTextView.setText(detailList.get(position));
		}

		GlideHelper.showImageWithUrl(mContext,picUrlList.get(position),holder.imageView);
		return convertView;
	}

	public class viewHolder {
		private TextView titleTextView,detailTextView;
		private ImageView imageView;
	}
	
}
