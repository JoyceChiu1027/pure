package com.bupt.chengde.adapter;

import java.util.List;

import com.bupt.chengde.R;
//import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.Category;
import com.bupt.chengde.entity.ResponsePhotographer;
import com.bupt.chengde.util.GlideHelper;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotographerListAdapter extends BaseAdapter {

	private static final int TYPE_CATEGORY_ITEM = 0;
	private static final int TYPE_ITEM = 1;

	private List<Category> mDatas;

	private Context mContext;
	private LayoutInflater inflater;
	//private ImageLoader imageLoader;
	//private DisplayImageOptions options;

	public PhotographerListAdapter(Context context, List<Category> datas) {
		this.mDatas = datas;
		this.mContext = context;
		inflater = LayoutInflater.from(mContext);
//		imageLoader = ImageLoader.getInstance();
//		options = new DisplayImageOptions.Builder().cacheInMemory(true)
//				.cacheOnDisk(true)
//				.showImageOnLoading(R.drawable.default_head_icon)
//				.showImageForEmptyUri(R.drawable.default_head_icon)
//				.showImageOnFail(R.drawable.default_head_icon)
//				.displayer(new CircleBitmapDisplayer())
//				.build();
		
	}

	@Override
	public int getCount() {
		int count = 0;

		if (null != mDatas) {
			// 所有分类中item的总和是ListVIew Item的总个数
			for (Category category : mDatas) {
				count += category.getItemCount();
			}
		}

		return count;
	}

	@Override
	public Object getItem(int position) {
		// 异常情况处理
		if (null == mDatas || position < 0 || position > getCount()) {
			return null;
		}

		// 同一分类内，第一个元素的索引值
		int categroyFirstIndex = 0;

		for (Category category : mDatas) {
			int size = category.getItemCount();//加上分类项的总长度
			// 在当前分类中的索引值
			int categoryIndex = position - categroyFirstIndex;
			// item在当前分类内
			if (categoryIndex < size) {
				return category.getItem(categoryIndex);
			}

			// 索引移动到当前分类结尾，即下一个分类第一个元素索引
			categroyFirstIndex += size;
			Log.d("PhotograperListAdapter","categroyFirstIndex="+categroyFirstIndex);
		}

		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		int itemViewType = getItemViewType(position);
		switch (itemViewType) {
		case TYPE_CATEGORY_ITEM:
			if (null == convertView) {
				convertView = inflater.inflate(R.layout.photographer_listview_item_header,
						null);
			}

			TextView textView = (TextView) convertView
					.findViewById(R.id.header);
			String itemValue = (String) getItem(position);
			textView.setText(itemValue);
			break;
		case TYPE_ITEM:
			ViewHolder holder = null;
			if (null == convertView) {

				convertView = inflater.inflate(R.layout.photographer_listview_item, null);

				holder = new ViewHolder();
				holder.content = (TextView) convertView
						.findViewById(R.id.content);
				holder.contentIcon = (ImageView) convertView
						.findViewById(R.id.content_icon);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// 绑定数据
			ResponsePhotographer response=(ResponsePhotographer) getItem(position);
			holder.content.setText(response.getPhotographerName());
			GlideHelper.showAvatarWithUrl(mContext,response.getPhotographerUrl(),holder.contentIcon);
			//viewHolder.contentIcon.setImageResource(R.drawable.default_head_icon);
			//imageLoader.displayImage(CodeConstants.PIC_URL_PREFIX+response.getPhotographerUrl(), viewHolder.contentIcon,options);
//			Glide.with(mContext)
//		     .load(CodeConstants.PIC_URL_PREFIX+response.getPhotographerUrl())
//		     .transform(new GlideCircleTransform(mContext))
//		     .placeholder(R.drawable.default_head_icon)
//		     .error(R.drawable.default_head_icon)
//		     .diskCacheStrategy(DiskCacheStrategy.ALL)
//		     .into(holder.contentIcon);
			break;
		}
		return convertView;
	}

	@Override
	public int getItemViewType(int position) {
		// 异常情况处理
		if (null == mDatas || position < 0 || position > getCount()) {
			return TYPE_ITEM;
		}

		int categroyFirstIndex = 0;

		for (Category category : mDatas) {
			int size = category.getItemCount();
			// 在当前分类中的索引值
			int categoryIndex = position - categroyFirstIndex;
			if (categoryIndex == 0) {
				return TYPE_CATEGORY_ITEM;
			}

			categroyFirstIndex += size;
		}

		return TYPE_ITEM;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return getItemViewType(position)!=TYPE_CATEGORY_ITEM;
	}

	private class ViewHolder {
		TextView content;
		ImageView contentIcon;
	}

}
