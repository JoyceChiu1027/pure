package com.bupt.chengde.adapter;

import java.util.List;

import com.bupt.chengde.R;
import com.bupt.chengde.entity.ResponseBusiType;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TvYewuAdapter extends BaseAdapter {

	private Context mContext;
	private List<ResponseBusiType> mlist;
	private List<Integer> ids;
	
	public TvYewuAdapter(Context context, List<ResponseBusiType> list,List<Integer> ids) {
		super();
		this.mContext = context;
		this.mlist = list;
		this.ids = ids;
	}

	@Override
	public int getCount() {
		return mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holderView;
		if (convertView == null) {
			holderView = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ye, null,false);
			holderView.textView = (TextView) convertView.findViewById(R.id.teId);
			holderView.imageView = (ImageView) convertView.findViewById(R.id.imgId);
			convertView.setTag(holderView);
		}else {
			holderView = (ViewHolder) convertView.getTag();
		}
		if (position > 4) {
			holderView.textView.setVisibility(View.GONE);
			holderView.imageView.setVisibility(View.GONE);
		}else {
			holderView.textView.setText(mlist.get(position).getTypeName());
			holderView.imageView.setBackgroundResource(ids.get(position));
		}
		
		return convertView;
	}

	public class ViewHolder{
		private TextView textView;
		private ImageView imageView;
	}
	
}
