package com.bupt.chengde.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author wyf
 * @类 :自定义ListView，取消其滑动效果
 * @version 1.0
 */
public class NoScrollListView extends ListView{

	public NoScrollListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// 自动生成的构造函数存根
	}

	public NoScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollListView(Context context) {
		super(context);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 自动生成的方法存根
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
