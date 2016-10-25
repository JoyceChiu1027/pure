package com.bupt.chengde.ui.se;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.CommonBaseInfo;
import com.bupt.chengde.entity.DiscussCountSignResponse;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.LogUtil;
import com.bupt.chengde.util.WebserviceUtils;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CommentActivity extends BaseActivity implements OnClickListener{
	private static final String TAG = CommentActivity.class.getSimpleName();
	private Context mContext;
	private EditText commentEt;
	private TextView cancelTv, sendTv;
	private String content;
	private String busiId;
	private String modId;
	private String custId;
	private String custName;
	private CommonBaseInfo response;
	private static final int TYPE_COMMENT = 3;
	private int ha=0;
	private String newdate;
	private DiscussCountSignResponse discussResponse= null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_comment);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mContext = CommentActivity.this;
		modId = getIntent().getStringExtra(CodeConstants.MODULE_ID);
		busiId = getIntent().getStringExtra(CodeConstants.BUSI_ID);
		custName = application.getCustName();
		custId = application.getCustID();
		com.bupt.chengde.util.LogUtil.d(TAG," custName="+custName+" custid="+custId+" modId="+modId+" busiId="+busiId);
		initView();

	}

	private void initView() {
		commentEt = (EditText) findViewById(R.id.comment_input);
		sendTv = (TextView) findViewById(R.id.send_comment_btn);
		cancelTv = (TextView) findViewById(R.id.cancel_comment_btn);
		sendTv.setOnClickListener(this);
		cancelTv.setOnClickListener(this);
	}

	@Override
	protected void doBackGround() throws Exception {
		// TODO Auto-generated method stub
		super.doBackGround();
		response = WebserviceUtils.likeStoreFunction(custName, custId, modId,
				busiId, TYPE_COMMENT, content);
	}

	@Override
	protected void doForeGround() throws Exception {
		// TODO Auto-generated method stub
		super.doForeGround();
		if (response != null) {
			if (response.getReturnCode().equals(CodeConstants.RETURN_SUCCESS)) {
				Toast.makeText(getApplicationContext(), "等待审核",
						Toast.LENGTH_SHORT).show();
				Date d = new Date();
				SimpleDateFormat sd = new SimpleDateFormat(
						"yyyy-MM-dd");
				newdate = sd.format(d);
				if (TextUtils.isEmpty(application.getDiscussTime())) {
					
				}else {
					com.bupt.chengde.util.LogUtil.d(TAG, newdate.substring(0, 10));
					LogUtil.d("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", application.getDiscussTime().substring(0, 10));
					if (newdate.substring(0, 10).equals(application.getDiscussTime().substring(0, 10))) {
						com.bupt.chengde.util.LogUtil.i("@@@@@@@@@@@@@", "不是首次评论");
					} else {
						com.bupt.chengde.util.LogUtil.i("1hahahah", "uuuuuuuuuuuuuuuuuuuuuu");
						runHandler2();
					}
				}
				
				finish();
			} else {
				Toast.makeText(mContext, response.getReturnMsg(), Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(mContext, "网络连接异常", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.send_comment_btn:
			content = commentEt.getText().toString().trim();
			if (TextUtils.isEmpty(content)) {
				Toast.makeText(mContext, "你还没有评论！", Toast.LENGTH_SHORT).show();
				return;
			}
            runHandler();  
			break;
		case R.id.cancel_comment_btn:
			finish();
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void doBackGround2() throws Exception {
		// TODO Auto-generated method stub
		super.doBackGround2();
		discussResponse = WebserviceUtils.addDiscussCount(
				Integer.parseInt(this.application.getCustID()), newdate);
	}
	
	@Override
	protected void doForeGround2() throws Exception {
		// TODO Auto-generated method stub
		super.doForeGround2();
		if (discussResponse.getReturnCode().equals(CodeConstants.RETURN_SUCCESS)) {	
			this.application.setDiscussTime(newdate);
			this.application.showToast("评论获取"+discussResponse.getCount()+"积分");
			this.application.setCustInt(discussResponse.getLatestCount());
		} else {
			this.application.showToast(discussResponse.getReturnMsg());
		}
	}
	
}
