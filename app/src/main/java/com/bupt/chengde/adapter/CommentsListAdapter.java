package com.bupt.chengde.adapter;

import java.util.List;

import com.bupt.chengde.R;
import com.bupt.chengde.entity.ResponseDiscussList;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentsListAdapter extends BaseAdapter {
    private Context mContext;
    private List<ResponseDiscussList> mDatas;
    private LayoutInflater inflater;
    public CommentsListAdapter(Context context,List<ResponseDiscussList> datas){
    	this.mContext=context;
    	this.mDatas=datas;
    	inflater=LayoutInflater.from(mContext);
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public ResponseDiscussList getItem(int position) {
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
        ViewHolder holder;
		if (convertView == null) {
           convertView=inflater.inflate(R.layout.comments_list_item, null);
           holder=new ViewHolder();
           holder.commentor=(TextView) convertView.findViewById(R.id.commentor);
           holder.content=(TextView)convertView.findViewById(R.id.comment_content);
           holder.commentTime=(TextView) convertView.findViewById(R.id.comment_time);
           convertView.setTag(holder);
		} else {
           holder=(ViewHolder) convertView.getTag();
		}
		ResponseDiscussList item=mDatas.get(position);
		
		if (!TextUtils.isEmpty(item.getNickName())&&!item.getNickName().equals("null")) {
			holder.commentor.setText("用户  "+item.getNickName());
		}else{
			String phoneNo=item.getPhoneNo();
			String maskNo=phoneNo.substring(0,3)+"****"+phoneNo.substring(7,phoneNo.length());
			holder.commentor.setText("用户  "+maskNo);
		}
		//holder.commentor.setText(item.getDiscussCustId());
		holder.content.setText(item.getContent());
		holder.commentTime.setText(item.getDiscussDate());
		return convertView;
	}

	class ViewHolder {
     ImageView avatorImage;
     TextView commentor;
     TextView content;
     TextView commentTime;
	}
}
