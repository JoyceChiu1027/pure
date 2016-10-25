package com.bupt.chengde.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.entity.ResponseMainMenu;

public class HomeMainMenuAdapter extends BaseAdapter {
	private Context mContext;
	private List<ResponseMainMenu> mDatas;
	private LayoutInflater inflater;

	public HomeMainMenuAdapter(Context context, List<ResponseMainMenu> datas) {
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
	public Object getItem(int position) {
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
			view = inflater.inflate(R.layout.yyt_grid_item, null);
			holder = new ViewHolder();
			holder.picTv = (TextView) view.findViewById(R.id.pic);
			holder.nameTv = (TextView) view.findViewById(R.id.name);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.picTv.setBackgroundResource(mDatas.get(position).getResId());
		holder.nameTv.setText(mDatas.get(position).getMainMenuName());
		return view;
	}
   
	public void refreshData() {
       mDatas.clear();
       notifyDataSetChanged();
      // notifyDataSetInvalidated();
	}

	class ViewHolder {
		TextView picTv;
		TextView nameTv;
	}
}
