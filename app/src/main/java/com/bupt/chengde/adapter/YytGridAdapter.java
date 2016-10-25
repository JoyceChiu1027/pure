package com.bupt.chengde.adapter;

import java.util.List;

import com.bupt.chengde.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class YytGridAdapter extends BaseAdapter {

	private List<String> itemList;
	private List<Integer> idList;
	private Context mContext;
	
	private LayoutInflater layoutInflater;
	
	public YytGridAdapter(List<String> itemList,List<Integer> idList,Context context) {
		super();
		this.itemList = itemList;
		this.mContext = context;
		this.idList = idList;
		layoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			
			convertView = layoutInflater.inflate(R.layout.yyt_grid_item, null,false);
			holder=new ViewHolder();
			holder.nameTextView = (TextView) convertView.findViewById(R.id.name);
			holder.picTextView = (TextView) convertView.findViewById(R.id.pic);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.nameTextView.setText(itemList.get(position));
		holder.picTextView.setBackgroundResource(idList.get(position));
		return convertView;
	}

    class ViewHolder{
		private TextView nameTextView,picTextView;
	}
	
}
