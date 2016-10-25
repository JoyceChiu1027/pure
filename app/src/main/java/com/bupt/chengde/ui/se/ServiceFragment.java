package com.bupt.chengde.ui.se;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.YytGridAdapter;
import com.bupt.chengde.ui.BaseFragment;
import com.bupt.chengde.widget.NoScrollGridView;

/**
 * @author wyf
 * @类 :服务
 * @version 1.0
 */
public class ServiceFragment extends BaseFragment {
	
	private View view;
	private NoScrollGridView gridView;
	private YytGridAdapter adapter;
	
	private List<String> itemList;
	private List<Integer> idList;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_se, container,false);
		gridView = (NoScrollGridView) view.findViewById(R.id.service_gridViewId);
		getData();
		adapter = new YytGridAdapter(itemList,idList,activity);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//activity.application.showToast(itemList.get(arg2));
				 if (arg2 == 0) {
					activity.startActivity(new Intent(activity,NewsActivity .class));
				}else if (arg2 == 1) {
					activity.startActivity(new Intent(activity,BianminActivity.class));
				}else if (arg2 == 2) {
					
					activity.startActivity(new Intent(activity,TravelActivity .class));
				}else if (arg2==3) {
					
					activity.startActivity(new Intent(activity,PhotographActivity .class));
				}else if (arg2==4) {
					
					activity.startActivity(new Intent(activity,JiemuYugaoActivity.class));
				}
			}
		});
		return view;
	}

	private void getData() {
		idList = new ArrayList<Integer>();
		idList.add(R.drawable.service_news_icon);		
		idList.add(R.drawable.service_conv_icon);
		idList.add(R.drawable.service_travel_icon);
		idList.add(R.drawable.service_photo_icon);
		idList.add(R.drawable.service_preview_icon);
		itemList = new ArrayList<String>();
		itemList.add("新闻");
		itemList.add("便民");
		itemList.add("旅游");
		itemList.add("摄影");
		itemList.add("节目预告");
	}
}
