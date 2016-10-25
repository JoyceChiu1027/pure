package com.bupt.chengde.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.entity.ResponseChannelId;

public class SecondChannelListAdapter extends BaseAdapter {
	private Context mContext;
	private List<ResponseChannelId> mDatas;
	private LayoutInflater inflater;

	public SecondChannelListAdapter(Context context,
			List<ResponseChannelId> datas) {
		mContext = context;
		mDatas = datas;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public ResponseChannelId getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.item3, null);
			holder=new ViewHolder();
			holder.name=(TextView) view.findViewById(R.id.textId);
		    view.setTag(holder);
		}else{
			holder=(ViewHolder) view.getTag();
		}
		holder.name.setText(mDatas.get(position).getChannelName());
		return view;
	}

	class ViewHolder {
		TextView name;
	}
}
