package com.bupt.chengde.ui.yyt;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.YytGridAdapter;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.ui.BaseFragment;

import com.bupt.chengde.widget.NoScrollGridView;

/**
 * @author wyf
 * @类 :营业厅
 * @version 1.0
 */
public class YytFragment extends BaseFragment {

	private View view;
	private NoScrollGridView gridView;

	private YytGridAdapter adapter;

	private List<String> nameList;
	private List<Integer> integers;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_yyt, container, false);
		gridView = (NoScrollGridView) view.findViewById(R.id.yyt_gridViewId);
		getData();
		adapter = new YytGridAdapter(nameList, integers, activity);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				switch (position) {
				case 0:// 电视业务
					intent.setClass(getActivity(), TvYewuActivity.class);
					//intent.putExtra(CodeConstants.TYPE, 1);
					break;
				case 1:// 新装业务
					intent.setClass(getActivity(), TvYewuDeActivity.class);
					intent.putExtra(CodeConstants.TYPE, 3);
					break;
				case 2:// 优惠活动
					intent.setClass(getActivity(), SalesActiveActivity.class);
					intent.putExtra(CodeConstants.TYPE, 4);
					break;
				case 3:// 宽带业务
					intent.setClass(getActivity(), TvYewuDeActivity.class);
					intent.putExtra(CodeConstants.TYPE, 2);
					break;
				case 4://故障报修
					intent.setClass(getActivity(), GuZhangActivity.class);
					break;
				case 5://营业厅网点
					intent.setClass(getActivity(), YytAddrInfoActivity.class);
					break;
				case 6://在线调查
					intent.setClass(getActivity(), InvestigationActivity.class);
					break;

				default:
					break;
				}
				if (intent!=null) {
					startActivity(intent);
				}
				
			}

		});
		return view;
	}

	private void getData() {
		nameList = new ArrayList<String>();
		nameList.add("电视业务");
		nameList.add("新装业务");
		nameList.add("优惠活动");
		nameList.add("宽带业务");
		nameList.add("故障报修");
		nameList.add("营业网点");
		nameList.add("在线调查");
		// nameList.add("余额查询");
		// nameList.add("充值缴费");

		integers = new ArrayList<Integer>();
		/*
		 * integers.add(R.drawable.yingyeting_tv);
		 * integers.add(R.drawable.yingyeting_kuandai);
		 * integers.add(R.drawable.yingyeting_xinzhuang);
		 * integers.add(R.drawable.yingyeting_guzhang);
		 * integers.add(R.drawable.yingyeting_youhui);
		 * integers.add(R.drawable.yingyeting_addr);
		 * integers.add(R.drawable.yingyeting_diaocha);
		 */
		integers.add(R.drawable.business_tv_icon);
		integers.add(R.drawable.business_new_icon);
		integers.add(R.drawable.business_hui_icon);
		integers.add(R.drawable.business_mac_icon);
		integers.add(R.drawable.business_fix_icon);
		integers.add(R.drawable.business_addr_icon);
		integers.add(R.drawable.business_survey_icon);

		// integers.add(R.drawable.yingyeting_yue);
		// integers.add(R.drawable.yingyeting_chongzhi);
	}

}
