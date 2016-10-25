package com.bupt.chengde.ui.yyt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseBusinessInfo;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.GlideHelper;

/**
 * @author wyf
 * @类 :订购业务
 * @version 1.0
 */
public class YewuDingActivity extends BaseActivity {

	private TextView nameTextView;
	private LinearLayout backTextView;
	private TextView productName, proTextView;
	private TextView productPrice;
	private TextView productDescription;
	private ImageView proImag;
	private Button orderBtn;

//	private ImageLoader imageLoader;
//	private DisplayImageOptions options;
	private ResponseBusinessInfo responseBusinessInfo;

	private int type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yewu_ding);
//		imageLoader = ImageLoader.getInstance();
//		options = new DisplayImageOptions.Builder().cacheInMemory(true)
//				/*.cacheOnDisc(true)
//				.showStubImage(R.drawable.default_title)*/
//				.cacheOnDisk(true)
//				.showImageOnLoading(R.drawable.default_business)
//				.showImageForEmptyUri(R.drawable.default_business)
//				.showImageOnFail(R.drawable.default_business).build();
		type = getIntent().getIntExtra(CodeConstants.TYPE, 0);
		responseBusinessInfo = (ResponseBusinessInfo) getIntent()
				.getSerializableExtra("object");
		initView();

	}

	private void initView() {
		proTextView = (TextView) findViewById(R.id.procuct_price_text);
		backTextView = (LinearLayout) findViewById(R.id.top_back);
		nameTextView = (TextView) findViewById(R.id.top_name);
		productName = (TextView) findViewById(R.id.product_name);
		productPrice = (TextView) findViewById(R.id.product_price);
		proImag = (ImageView) findViewById(R.id.product_pic);
		proImag.setVisibility(View.VISIBLE);
		proTextView.setText("价格：");
		productDescription = (TextView) findViewById(R.id.product_description);
		orderBtn = (Button) findViewById(R.id.order_btn);
		if (type == 1) {
			nameTextView.setText("电视业务详情");
		} else if (type == 2) {
			nameTextView.setText("宽带业务详情");
		} else if (type == 3) {
			nameTextView.setText("新装业务详情");
		}

		productName.setText(responseBusinessInfo.getBusTitle());
		productPrice.setText(responseBusinessInfo.getBusFee());
		productDescription.setText(responseBusinessInfo.getBusContent());
//		imageLoader.displayImage(
//				CodeConstants.PIC_URL_PREFIX + responseBusinessInfo.getBusPicUrl(),
//				proImag, options);
		GlideHelper.showImageWithUrl(this,responseBusinessInfo.getBusPicUrl(),proImag);
		orderBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (application.isLogin()) {
					Intent intent = new Intent(YewuDingActivity.this,
							PayActivity.class);
					intent.putExtra(CodeConstants.TYPE, type);
					intent.putExtra("object", responseBusinessInfo);
					startActivity(intent);
				}else{
					Toast.makeText(activity, "操作前请登录", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		backTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
}
