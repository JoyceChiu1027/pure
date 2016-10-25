package com.bupt.chengde.ui.se;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.JiemuAdapter;
import com.bupt.chengde.entity.ResponseChannelId;
import com.bupt.chengde.entity.ResponseProgramInfo;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.LogUtil;
import com.bupt.chengde.util.WebserviceUtils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 节目预告展示
 * @author wyf
 *
 */
public class JiemuThreeActivity extends BaseActivity {

	private ResponseChannelId responseChannelInfo;
    private List<ResponseProgramInfo> list;
    private ListView listView;
    private TextView[] textViews = new TextView[5];
    private String phoneNo;
    private List<ResponseProgramInfo> returnList = new ArrayList<ResponseProgramInfo>();
    private JiemuAdapter adapter;
    public static SimpleDateFormat  sdf= new SimpleDateFormat("yyyy年MM月dd日");
    public static SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
    private static final String TAG=JiemuThreeActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jiemu_three);
		phoneNo=application.getCustPhone();
		LogUtil.e(TAG, "application.getCustPhone()="+application.getCustPhone());
		initView();
        runHandler();
	}
	

	 @Override
	    protected void doForeGround() throws Exception {
		 Log.d(TAG,"returnList.size="+returnList.size());
	        if (list.size() == 0){
	            application.showToast("没有数据");
	        }
	        adapter.notifyDataSetChanged();

	    }

	

	    @Override
	    protected void doBackGround() throws Exception {
	        String date;
	        if (textViews[1].getText().toString().equals(today)){
	            date = dateNowStr;
	        }else{
	            date = textViews[1].getText().toString();
	        }
	        String dateformat=sd.format(sdf.parse(date));
	        list = WebserviceUtils.getProgramInfo(responseChannelInfo.getChannelId(), dateformat,phoneNo);
	        returnList.clear();
	        returnList.addAll(list);
	    }

	    String dateNowStr;
	    String today = "今天";

	    @SuppressLint("SimpleDateFormat")
		private void initView() {
	        Date d = new Date();
	        //SimpleDateFormat sd = new SimpleDateFormat("yyyy年MM月dd日");
	        dateNowStr = sdf.format(d);
	        responseChannelInfo = (ResponseChannelId) getIntent().getSerializableExtra("object");
	        System.out.println(responseChannelInfo.toString());
	        listView = (ListView) findViewById(R.id.listviewid);
	        textViews[0] = (TextView) findViewById(R.id.yesterday);
	        textViews[1] = (TextView) findViewById(R.id.today);
	        textViews[2] = (TextView) findViewById(R.id.tommorw);
	        textViews[3] = (TextView) findViewById(R.id.back);
	        textViews[4] = (TextView) findViewById(R.id.next);

	        textViews[1].setText(today);
	        textViews[0].setText(addDay(dateNowStr, -1));
	        textViews[2].setText(addDay(dateNowStr, 1));
            textViews[0].setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 for (int i = 0; i < 3; i++) {
		                    String curr = textViews[i].getText().toString();
		                    if (curr.equals(today)) {
		                        textViews[i].setText(addDay(dateNowStr, -1));
		                    } else {
		                        if (addDay(curr,-1).equals(dateNowStr)){
		                            textViews[i].setText(today);
		                        }else{
		                            textViews[i].setText(addDay(curr, -1));
		                        }
		                    }

		                }
		                runHandler();
				}
			});
            textViews[2].setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 for (int i = 0; i < 3; i++) {
		                    String curr = textViews[i].getText().toString();
		                    if (curr.equals(today)) {
		                        textViews[i].setText(addDay(dateNowStr, 1));
		                    } else {
		                        if (addDay(curr,1).equals(dateNowStr)){
		                            textViews[i].setText(today);
		                        }else{
		                            textViews[i].setText(addDay(curr, 1));
		                        }

		                    }
		                }
		                runHandler();
				}
			});
	        textViews[3].setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                for (int i = 0; i < 3; i++) {
	                    String curr = textViews[i].getText().toString();
	                    if (curr.equals(today)) {
	                        textViews[i].setText(addDay(dateNowStr, -1));
	                    } else {
	                        if (addDay(curr,-1).equals(dateNowStr)){
	                            textViews[i].setText(today);
	                        }else{
	                            textViews[i].setText(addDay(curr, -1));
	                        }
	                    }

	                }
	                runHandler();
	            }
	        });
	        textViews[4].setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                for (int i = 0; i < 3; i++) {
	                    String curr = textViews[i].getText().toString();
	                    if (curr.equals(today)) {
	                        textViews[i].setText(addDay(dateNowStr, 1));
	                    } else {
	                        if (addDay(curr,1).equals(dateNowStr)){
	                            textViews[i].setText(today);
	                        }else{
	                            textViews[i].setText(addDay(curr, 1));
	                        }

	                    }
	                }
	                runHandler();
	            }
	        });

	        ((TextView) findViewById(R.id.top_name)).setText(responseChannelInfo.getChannelName());
	        findViewById(R.id.top_back).setOnClickListener(new View.OnClickListener() {

	            @Override
	            public void onClick(View arg0) {
	                finish();
	            }
	        });
	        adapter = new JiemuAdapter(activity, returnList,phoneNo);
	        listView.setAdapter(adapter);
	    }

	    @SuppressLint("SimpleDateFormat")
		public static String addDay(String s, int n) {
	        String str_riq;
	        try {
	            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	            Calendar cd = Calendar.getInstance();
	            cd.setTime(sdf.parse(s));
	            cd.add(Calendar.DATE, n);// 增加一天
	            // cd.add(Calendar.MONTH, n);//增加一个月
	            str_riq = sdf.format(cd.getTime());
	            return str_riq;

	        } catch (Exception e) {
	            return null;
	        }
	    }
	    
//	    private OnClickListener reservationListener = new OnClickListener() {  
//	  	  
//	        @Override  
//	        public void onClick(View v) {  
//	            int id = v.getId();  
//	            if (id == R.id.program_reserved) {  
//	                // 点击了文件名  
//	                ResponseProgramInfo tag = (ResponseProgramInfo) v.getTag();  
//	                int state = tag.getStatus();  
//	               /* if (state == AttachmentInfo.UP_LOADING  
//	                        || state == AttachmentInfo.DOWN_LOADING  
//	                        || state == AttachmentInfo.ALREADY_SAVE  
//	                        || state == AttachmentInfo.NOT_DOWNLOAD  
//	                        || state == AttachmentInfo.DOWN_LOADING_SUCCESS  
//	                        || state == AttachmentInfo.DOWN_LOADING_FAIL  
//	                        || state == AttachmentInfo.UP_LOADING_FAIL  
//	                        || state == AttachmentInfo.UP_LOADING_SUCCESS) {  
//	                    // 正在上传,正在下载，已和服务器同步状态，未下载，不允许重命名  
//	                    return;  
//	                } */ 
//	                if (state==1) {
//						
//					}else{
//						
//					}
//	               // showChangeFileNameDialog(tag);  
//	            } else if ((id == R.id.file_list_item_upordownLoad)  
//	                    || (id == R.id.file_list_item_operationFrameLayout)) {  
//	                // 点击了操作图标,或者整个操作布局  
//	                // 获取绑定的对象  
//	                AttachmentInfo tag = (AttachmentInfo) v.getTag();  
//	                // 上传相关只有未上传和上传失败才显示操作按钮  
//	                int state = tag.state;  
//	                switch (state) {  
//	                // 按钮主要出发积极操作  
//	                case AttachmentInfo.NOT_UPLOAD:  
//	                    // 未上传，点击表示准备上传，加入上传队列  
//	                    tag.state = AttachmentInfo.PREPARE_UPLOAD;  
//	                    // 发送一个上传文件的消息  
//	                    handler.sendEmptyMessage(TO_UPLOAD_FILE);  
//	                    break;  
//	                case AttachmentInfo.UP_LOADING_FAIL:  
//	                    // 上传失败,点击表示重新上传  
//	                    tag.state = AttachmentInfo.PREPARE_UPLOAD;  
//	                    handler.sendEmptyMessage(TO_UPLOAD_FILE);  
//	                    break;  
//	                case AttachmentInfo.NOT_DOWNLOAD:  
//	                    // 未下载  
//	                    tag.state = AttachmentInfo.PREPARE_DOWNLOAD;  
//	                    handler.sendEmptyMessage(TO_DOWNLOAD_FILE);  
//	                    break;  
//	                case AttachmentInfo.DOWN_LOADING_FAIL:  
//	                    // 下载失败  
//	                    tag.state = AttachmentInfo.PREPARE_DOWNLOAD;  
//	                    handler.sendEmptyMessage(TO_DOWNLOAD_FILE);  
//	                    break;  
//	                default:  
//	                    break;  
//	                }  
//	            }  
//	            attachmentAdapter.notifyDataSetChanged();  
//	            fileInfoListView.invalidate();  
//	        }  
//	    };  
	  
}
