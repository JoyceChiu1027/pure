package com.bupt.chengde.ui.yyt;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.TvYewuAdapter;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseBusiType;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.XListView;

/**
 * 电视业务分类列表页
 * 
 * @author zhaojie
 * 
 */
public class TvYewuActivity extends BaseActivity implements OnClickListener{

	private List<ResponseBusiType> responseBusiTypes, list;
	private TvYewuAdapter adapter;
	private List<Integer> ids;
	private XListView mXListView;
	private TextView nameTextView;
	private String type="1";
	
    private Context mContext; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tv_yewu);
        mContext=TvYewuActivity.this;
		initView();
		runHandler();
		mXListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//application.showToast("position="+position);
				Intent intent = new Intent(activity, TvYewuDeActivity.class);
				intent.putExtra("name", responseBusiTypes.get(position-1)
						.getTypeName());
				intent.putExtra(CodeConstants.TYPE, 1);//电视业务
				intent.putExtra(CodeConstants.TYPE_ID, responseBusiTypes.get(position-1).getTypeId());
				startActivity(intent);
			}
		});
		
	}

	private void initView() {
		mXListView = (XListView) findViewById(R.id.tvListViewId);
		mXListView.setPullRefreshEnable(false);
		mXListView.setPullLoadEnable(false);
		nameTextView = (TextView) findViewById(R.id.top_name);
		nameTextView.setText("电视业务");
		findViewById(R.id.top_back).setOnClickListener(this);
		responseBusiTypes=new ArrayList<ResponseBusiType>();
		ids=new ArrayList<Integer>();
		ids.add(R.drawable.tv_1);
		ids.add(R.drawable.tv_2);
		ids.add(R.drawable.tv_3);
	}	

	@Override
	protected void doBackGround() throws Exception {
		list = WebserviceUtils.getBusiType(type);
		/*String[] typeIds={"1","2","3"};
		String[] typeNames={"高清节目产品","标清节目产品","电视点播回看"};
		for (int i = 0; i < 3; i++) {
			ResponseBusiType response=new ResponseBusiType();
			response.setTypeId(typeIds[i]);
			response.setTypeName(typeNames[i]);
			responseBusiTypes.add(response);
		}*/
	}

	@Override
	protected void doForeGround() throws Exception {
		
		if (list!=null) {
			if (list.size()>0) {
				responseBusiTypes.addAll(list);
				Log.d("TvYewuActivity", "list.size="+list.size());
				if (adapter==null) {
					adapter=new TvYewuAdapter(mContext, responseBusiTypes, ids);
					mXListView.setAdapter(adapter);
				}else{
					adapter.notifyDataSetChanged();
				}
			}else{
				 Toast.makeText(mContext, "服务器返回无数据！", Toast.LENGTH_SHORT).show();
			}
			
			
		}else{
			//mXListView.setPullRefreshEnable(true);
		    Toast.makeText(mContext, "网络连接异常", Toast.LENGTH_SHORT).show();
		}
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_back:
			finish();
			break;
		
		default:
			break;
		}
	}
}
