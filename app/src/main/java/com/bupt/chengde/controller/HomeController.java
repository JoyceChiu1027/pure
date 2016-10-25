package com.bupt.chengde.controller;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.bupt.chengde.ui.home.HomeFragment;

public class HomeController implements OnItemClickListener {
	private static final String TAG = "HomeController";
	private HomeFragment mContext;

	public HomeController(HomeFragment context, View root) {
		mContext = context;
	}
    
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
       
	}

}
