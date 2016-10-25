package com.bupt.chengde.ui.se;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.w3c.dom.Text;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.LiveAdapter;
import com.bupt.chengde.control.AlarmReceiver;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseChannelId;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.WebserviceUtils;

/**
 * 
 * @author wyf
 * @类 : 节目预告
 * @version 1.0
 */
public class JiemuYugaoActivity extends BaseActivity {
	private static final String TAG = "JiemuYugaoActivity";
	private ListView listView;
	private List<ResponseChannelId> datas = new ArrayList<ResponseChannelId>();
	private List<ResponseChannelId> list;
	
	private LiveAdapter liveAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jiemu_yugao);
	
		initView();
		
		runHandler();
	}

	@Override
	protected void doBackGround() throws Exception {
		//list = WebserviceUtils.getChannel();
	    String[] names={"央视","卫视","其他","高清","河北","承德"};
		//list.add(object)
	    
	    for (int i = 0; i < names.length; i++) {
			ResponseChannelId response=new ResponseChannelId();
			response.setChannelName(names[i]);
			datas.add(response);
		}
	}
	
	@Override
	protected void doForeGround() throws Exception {
		liveAdapter.notifyDataSetChanged();
	}
	
	private void initView() {
//		orderBtn=(Button) findViewById(R.id.order_jiemu_btn);
//		orderBtn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				reservation(10);
//			}
//		});
		((TextView) findViewById(R.id.top_name)).setText("节目预告");
		findViewById(R.id.top_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		listView = (ListView) findViewById(R.id.jiemu_list);

		// 初始化一级频道列表
		liveAdapter = new LiveAdapter(JiemuYugaoActivity.this, datas);
		listView.setAdapter(liveAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(JiemuYugaoActivity.this,JiemuTwoActivity.class);
				intent.putExtra("object", datas.get(position));
				startActivity(intent);
			}
		});
	}
   private void reservation(int time){
	   application.showToast("将在10秒后发送通知！");
	   AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
	   Intent intent=new Intent(this, AlarmReceiver.class);
	   intent.putExtra(CodeConstants.BUSI_ID, "hahahha");
	   intent.putExtra(CodeConstants.TYPE, "");
	   PendingIntent sender=PendingIntent.getBroadcast(this, 0, intent, 0);
	   Calendar calendar=Calendar.getInstance();
	   calendar.setTimeInMillis(System.currentTimeMillis());
	   calendar.add(Calendar.SECOND, time);
	   alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
	  
   }
}
