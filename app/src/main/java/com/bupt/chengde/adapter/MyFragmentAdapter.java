package com.bupt.chengde.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;
	
	public MyFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	public MyFragmentAdapter(FragmentManager fm,List<Fragment> fragments){
		super(fm);
		this.fragments = fragments;
	}
	
	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments == null ? 0 : fragments.size();
	}

}
