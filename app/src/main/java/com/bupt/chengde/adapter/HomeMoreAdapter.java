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
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.util.GlideHelper;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeMoreAdapter extends BaseAdapter {
	private Context mContext;
	private List<String> moreTitleList;
	private List<String> morePosterUrlList;
	private List<String> moreDateList;
	private List<String> moreDeTailList;
	LayoutInflater inflater;

	public HomeMoreAdapter(List<String> moreTitleList,
			List<String> morePosterUrlList, List<String> moreDateList,List<String> moreDeTailList,
			Context context) {
		this.mContext = context;
		this.moreTitleList = moreTitleList;
		this.morePosterUrlList = morePosterUrlList;
		this.moreDateList = moreDateList;
		this.moreDeTailList = moreDeTailList;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return moreTitleList.size();
	}

	@Override
	public Object getItem(int position) {
		return moreTitleList.get(position);
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
			convertView = inflater.inflate(R.layout.home_item, null);
			holder.logoImageView = (ImageView) convertView
					.findViewById(R.id.home_logo);
			holder.title = (TextView) convertView.findViewById(R.id.home_title);
			holder.brief = (TextView) convertView.findViewById(R.id.home_de);
			holder.time = (TextView) convertView.findViewById(R.id.home_time);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		holder.title.setText(moreTitleList.get(position));
		if (moreDeTailList.get(position).endsWith("无摘要 ！")) {			
			holder.brief.setVisibility(View.GONE);
		} else {
			holder.brief.setVisibility(View.VISIBLE);
			holder.brief.setText(moreDeTailList.get(position));
		}
		holder.time.setText(moreDateList.get(position));
		GlideHelper.showImageWithUrl(mContext,morePosterUrlList.get(position),holder.logoImageView);
		return convertView;
	}

	public class viewHolder {
		private ImageView logoImageView;
		private TextView title, brief, time;
	}

}
