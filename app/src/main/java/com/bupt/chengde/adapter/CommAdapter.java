package com.bupt.chengde.adapter;

import java.util.List;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bupt.chengde.R;
import com.bupt.chengde.entity.ResponseNews;
import com.bupt.chengde.util.GlideHelper;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommAdapter extends BaseAdapter {

	private List<String> titleList;
	private List<ResponseNews> mDatas;
	private Context mContext;

	LayoutInflater inflater;
	//ImageLoader imageLoader;
	//DisplayImageOptions options;

	public CommAdapter(/* List<String> titleList, */List<ResponseNews> datas, Context context) {
		this.mContext = context;
		/* this.titleList = titleList; */
		this.mDatas = datas;
		inflater = LayoutInflater.from(mContext);
//		imageLoader = ImageLoader.getInstance();
//		options = new DisplayImageOptions.Builder()
//				.cacheOnDisk(true)
//				.showImageOnLoading(R.drawable.default_title)
//				.showImageForEmptyUri(R.drawable.default_title)
//				.showImageOnFail(R.drawable.default_title)
//				.resetViewBeforeLoading(true).build();

	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
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
			convertView = inflater.inflate(R.layout.comm_item, null, false);
			holder.titleTextView = (TextView) convertView.findViewById(R.id.comm_title);
			holder.imageView = (ImageView) convertView.findViewById(R.id.comm_img);
			holder.brief = (TextView) convertView.findViewById(R.id.comm_detail);
			holder.time = (TextView) convertView.findViewById(R.id.comm_time);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}

		ResponseNews response = mDatas.get(position);
		if (TextUtils.isEmpty(response.getNewsName()) || response.getNewsName().equals("null")) {
			response.setNewsName("");
		}
		if (TextUtils.isEmpty(response.getContent()) || response.getContent().equals("null")) {
			response.setContent("");
		}
		holder.titleTextView.setText(response.getNewsName());
		holder.time.setText(mDatas.get(position).getNewsDate());
		if (mDatas.get(position).getContent().equals("null")) {
			holder.brief.setVisibility(View.GONE);
		} else {
			holder.brief.setVisibility(View.VISIBLE);
			holder.brief.setText(mDatas.get(position).getContent());
		}
		GlideHelper.showImageWithUrl(mContext,mDatas.get(position).getNewsPosterUrl(),holder.imageView);

		//imageLoader.displayImage(CodeConstants.PIC_URL_PREFIX + mDatas.get(position).getNewsPosterUrl(),holder.imageView, options);
//		Glide.with(mContext)
//	     .load(CodeConstants.PIC_URL_PREFIX+ mDatas.get(position).getNewsPosterUrl())
//	     .placeholder(R.drawable.default_title)
//	     .error(R.drawable.default_title)
//	     .diskCacheStrategy(DiskCacheStrategy.ALL)
//	     .into(holder.imageView);
		return convertView;
	}

	public class viewHolder {
		TextView titleTextView;
		ImageView imageView;
		TextView time;
		TextView brief;
	}

}
