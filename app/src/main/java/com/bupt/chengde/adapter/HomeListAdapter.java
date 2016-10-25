package com.bupt.chengde.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.HomeBean;
import com.bupt.chengde.entity.ResponseAdPoster;
import com.bupt.chengde.ui.home.HomeListDetailActivity;
import com.bupt.chengde.ui.home.MoreListActivity;
import com.bupt.chengde.widget.ListViewTop;
import com.bupt.chengde.widget.NoScrollListView;

/**
 * @author wyf
 * @类 :首页listView的adapter
 * @version 1.0
 */
public class HomeListAdapter extends BaseAdapter {
	private static final String TAG = HomeListAdapter.class.getSimpleName();
	private Context mContext;
	private List<List<HomeBean>> mLists;
	private List<String> mNameList;
	private List<ResponseAdPoster> mAdLists;
	LayoutInflater inflater;
	viewHolder holder;

	public HomeListAdapter(Context context, List<List<HomeBean>> lists,
			List<String> nameList, List<ResponseAdPoster> adLists) {
		this.mContext = context;
		this.mLists = lists;
		this.mNameList = nameList;
		this.mAdLists = adLists;
	
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mLists.size();
	}

	@Override
	public Object getItem(int position) {
		return mLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		holder = null;
		if (convertView == null) {
			holder = new viewHolder();
			convertView = inflater.inflate(R.layout.home_listview_item, null);
			holder.nameTextView = (TextView) convertView
					.findViewById(R.id.home_name);
			holder.moreButton = (Button) convertView
					.findViewById(R.id.home_more);
			holder.listView = (NoScrollListView) convertView
					.findViewById(R.id.home_listview);
			holder.conLayout = (LinearLayout) convertView
					.findViewById(R.id.home_con);
			
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
			//holder.listViewTop.stopScroll();
		}
		holder.nameTextView.setText(mNameList.get(position));
		holder.conLayout.removeAllViews();
		
		if (mAdLists.size()>0) {
		    if (position==0) {
		    	holder.conLayout.setVisibility(View.VISIBLE);
		    	holder.listViewTop=new ListViewTop(mContext);
		    	//ListViewTop listViewTop = new ListViewTop(mContext);
		    	holder.conLayout.addView(holder.listViewTop);
		    	if (mAdLists.size()<=5) {
		    		holder.listViewTop.initData(2, mAdLists);
				}else if (mAdLists.size()>5) {
					holder.listViewTop.initData(2, mAdLists.subList(0, 5));
				}
			}else if (position==1) {
				if (mAdLists.size() > 5) {
					holder.conLayout.setVisibility(View.VISIBLE);
					holder.listViewTop=new ListViewTop(mContext);
					//ListViewTop listViewTop = new ListViewTop(mContext);
					holder.conLayout.addView(holder.listViewTop);
					holder.listViewTop.initData(2, mAdLists.subList(5, mAdLists.size()));
				} else {
					holder.conLayout.setVisibility(View.GONE);
				}
			}else if (position==2) {
				holder.conLayout.setVisibility(View.GONE);
			}
		}else{
			holder.conLayout.setVisibility(View.GONE);
		}
		
		HomeAdapter homeAdapter = new HomeAdapter(mContext,
				mLists.get(position));
		holder.listView.setAdapter(homeAdapter);
		final List<HomeBean> list = mLists.get(position);
		holder.listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int p,
					long id) {
				// Toast.makeText(mContext, list.get(position).getTitle(),
				// Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(mContext, HomeListDetailActivity.class);
				intent.putExtra(CodeConstants.BUSI_ID, list.get(p).getId() + "");
				intent.putExtra(CodeConstants.HOME_LIST_TYPE, list.get(p)
						.getType());
				// (type: 1推荐新闻，2每日惠，3热门活动；)
				int type = list.get(p).getType();
				if (type == 1) {
					intent.putExtra(CodeConstants.MODULE_ID,
							CodeConstants.MAIN_NEWS);
				} else if (type == 2) {
					intent.putExtra(CodeConstants.MODULE_ID,
							CodeConstants.MAIN_SALES);
				} else if (type == 3) {
					intent.putExtra(CodeConstants.MODULE_ID,
							CodeConstants.MAIN_HOT);
				}
				mContext.startActivity(intent);
			}
		});
		final String nameString = mNameList.get(position);
		holder.moreButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.putExtra(CodeConstants.HOME_LIST_TYPE,
						mLists.get(position).get(0).getType());
				intent.setClass(mContext, MoreListActivity.class);
				mContext.startActivity(intent);
			}
		});
		return convertView;
		/*if (position ==0) {
			holder.conLayout.setVisibility(View.VISIBLE);
			else{
				ListViewTop listviewTop = new ListViewTop(mContext);
				holder.conLayout.addView(listviewTop);
				if (mAdLists.size()>0&&mAdLists.size()<5) {
					listviewTop.initData(2, mAdLists);
				} else if (mAdLists.size() > 5) {
					listviewTop.initData(2, mAdLists.subList(0, 5));
					
				}
			}
			

		} else if (position == 1) {
			if (mAdLists.size() > 5) {
				holder.conLayout.setVisibility(View.VISIBLE);
				ListViewTop listviewTop = new ListViewTop(mContext);
				holder.conLayout.addView(listviewTop);
				listviewTop.initData(2, mAdLists.subList(5, mAdLists.size()));
			} else {
				holder.conLayout.setVisibility(View.GONE);
			}
		} else if (position ==2) {
			holder.conLayout.setVisibility(View.GONE);
		}*/

		/*
		 * //方法一，先判断位置 if (arg0==0) { if
		 * (mAdLists.size()>5&&mAdLists.size()<=10) { listViewTop.initData(2,
		 * mAdLists.subList(5, mAdLists.size()-1));
		 * holder.conLayout.addView(listViewTop); }else{ listViewTop.initData(2,
		 * mAdLists.subList(5, 9)); holder.conLayout.addView(listViewTop); }
		 * 
		 * } if (arg0==1) { if (mAdLists.size()>10) { listViewTop.initData(2,
		 * mAdLists.subList(10,mAdLists.size()-1));
		 * holder.conLayout.addView(listViewTop); } }
		 */
		// 方法2 先判断广告数据的长度，超过5条后才 初始化广告条
		/*
		 * if (mAdLists.size()>5&&mAdLists.size()<=10) { if (position==0) {
		 * listViewTop.initData(2, mAdLists.subList(5, mAdLists.size()));
		 * holder.conLayout.addView(listViewTop); } }else if
		 * (mAdLists.size()>10) { if (position==0) { listViewTop.initData(2,
		 * mAdLists.subList(5, 10)); }else if (position==1) {
		 * listViewTop.initData(2, mAdLists.subList(10, mAdLists.size())); }
		 * holder.conLayout.addView(listViewTop); }
		 */

		/*
		 * List<Integer> idList2 = new ArrayList<Integer>();
		 * idList2.add(R.drawable.lun2); idList2.add(R.drawable.lun4);
		 * idList2.add(R.drawable.lun3);
		 */
		// listViewTop.initData(idList2,2);
		// listViewTop
		// holder.conLayout.addView(listViewTop);
		// }

		
	}

	public class viewHolder {
		private TextView nameTextView;
		private Button moreButton;
		private NoScrollListView listView;
		private LinearLayout conLayout;
		private ListViewTop listViewTop;
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generaated method stub
		super.notifyDataSetChanged();
	}
}
