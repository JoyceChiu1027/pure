package com.bupt.chengde.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.entity.ResponseBenPeo;
import com.bupt.chengde.util.GlideHelper;

import java.util.HashMap;
import java.util.List;

public class BianminAdapter extends BaseAdapter {

    LayoutInflater inflater;
    private List<HashMap<String, String>> titleList;
    private List<ResponseBenPeo> mDatas;
    private Context mContext;

    public BianminAdapter(Context context, List<ResponseBenPeo> datas) {
        this.mContext = context;
        //this.titleList = titleList;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
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
            convertView = inflater.inflate(R.layout.bianmin_item, null, false);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.bian_title);
            holder.telTextView = (TextView) convertView.findViewById(R.id.bian_tel);
            holder.addTextView = (TextView) convertView.findViewById(R.id.bian_add);
            holder.imageView = (ImageView) convertView.findViewById(R.id.bian_img);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }

        holder.titleTextView.setText(mDatas.get(position).getBenPeoName());
        holder.telTextView.setText(mDatas.get(position).getBenPeoPhone());
        holder.addTextView.setText(mDatas.get(position).getBenPeoAddr());
        GlideHelper.showImageWithUrl(mContext, mDatas.get(position).getBenPeoUrl(), holder.imageView);

        return convertView;
    }

    public class viewHolder {
        private TextView titleTextView, telTextView, addTextView;
        private ImageView imageView;
    }

}
