package com.bupt.chengde.base;

import android.os.Bundle;

public interface BaseApp {

	public interface Activity {
		void initViews(int layoutResID);

		void initData(Bundle bundle);
	}

	public interface Fragment {

		void initViews();

		void initData(Bundle bundle);
	}

}
