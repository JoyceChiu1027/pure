package com.bupt.chengde.adapter;

import java.util.List;

import com.bupt.chengde.R;
import com.bupt.chengde.entity.ResponseChannelId;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.text.style.TypefaceSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * @author wyf
 * @类 :直播页面的第一个适配器
 * @version 1.0
 */
public class LiveAdapter extends BaseAdapter {

	private Context mcontext;
	private List<ResponseChannelId> list;
	private LayoutInflater inflater;
//	public int index;
//  private  Typeface typeface;
	
	public LiveAdapter(Context context, List<ResponseChannelId> list) {
		this.mcontext = context;
		this.list = list;
		inflater = LayoutInflater.from(mcontext);
		//typeface=Typeface.createFromAsset(context.getAssets(), "kaiti.ttf");
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holderView = null;
		if (arg1 == null) {
			holderView = new ViewHolder();
			arg1 = inflater.inflate(R.layout.item1, null,false);
			holderView.textView = (TextView) arg1.findViewById(R.id.textId);
			//holderView.textView.setTypeface(typeface);
			arg1.setTag(holderView);
		}else {
			holderView = (ViewHolder) arg1.getTag();
		}
//		if (index == arg0) {
//			arg1.setBackgroundResource(R.drawable.list_bg);
//		}else {
//			arg1.setBackgroundResource(R.color.white);
//		}
		holderView.textView.setText(list.get(arg0).getChannelName());
		return arg1;
	}

	public class ViewHolder{
		private TextView textView;
	}
	
}
