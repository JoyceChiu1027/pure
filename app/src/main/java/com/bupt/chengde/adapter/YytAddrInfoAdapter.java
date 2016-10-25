package com.bupt.chengde.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.entity.ResponseYytAddrInfo;

/**
 * @author wyf
 * @类 :实体营业厅地址信息
 * @version 1.0
 */
public class YytAddrInfoAdapter extends BaseAdapter {

	private Context mcontext;
	private List<ResponseYytAddrInfo> list;
	LayoutInflater inflater;
	
	public YytAddrInfoAdapter(Context context, List<ResponseYytAddrInfo> list) {
		super();
		this.mcontext = context;
		this.list = list;
		inflater = LayoutInflater.from(mcontext);
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
	public View getView(int position,View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.yyt_item, null);
			holder.nameTextView = (TextView) convertView.findViewById(R.id.name);
			holder.phoneTextView= (TextView)convertView.findViewById(R.id.phone);
			holder.addrTextView = (TextView) convertView.findViewById(R.id.addres);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.nameTextView.setText(list.get(position).getYytName());
		holder.phoneTextView.setText("联系电话："+list.get(position).getPhoneNo());
		holder.addrTextView.setText("地\t\t\t址："+list.get(position).getAddr());
		return convertView;
	}

	public class ViewHolder {
		private TextView nameTextView;
		private TextView addrTextView;
		private TextView phoneTextView;
	}
	
}
