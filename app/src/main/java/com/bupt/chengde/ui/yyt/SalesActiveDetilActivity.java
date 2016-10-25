package com.bupt.chengde.ui.yyt;

import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseActive;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.WebserviceUtils;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 优惠活动详情页
 * 
 * @author wyf
 * 
 */
public class SalesActiveDetilActivity extends BaseActivity {

	private TextView nameTextView;
	private LinearLayout backTextView;
	private TextView dataTextView, proTextView;
	private TextView productName;
	private TextView productPrice;
	private TextView productDescription;
	private ImageView proImag;
	private Button orderBtn;

	//private ImageLoader imageLoader;
	//private DisplayImageOptions options;

	private String activeId;
	private ResponseActive activeById = null;

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
		activeId = getIntent().getStringExtra(CodeConstants.BUSI_ID);
		initView();
		runHandler();
	}

	@Override
	protected void doBackGround() throws Exception {
		activeById = WebserviceUtils.getActiveById(Integer.parseInt(activeId));
	}

	@Override
	protected void doForeGround() throws Exception {
		if (activeById == null) {
			return;
		}
		orderBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SalesActiveDetilActivity.this,
						PayActivity.class);
				intent.putExtra(CodeConstants.TYPE, 4);
				intent.putExtra("object", activeById);
				startActivity(intent);
			}
		});
		productName.setText(activeById.getActiveTitle());
		productPrice.setText(activeById.getActivePrice());
		productDescription.setText(activeById.getActiveContent());
		proTextView.setText("价格：");
//		imageLoader.displayImage(
//				CodeConstants.PIC_URL_PREFIX + activeById.getActivePosterUrl(),
//				proImag, options);
	}

	private void initView() {
		backTextView = (LinearLayout) findViewById(R.id.top_back);
		nameTextView = (TextView) findViewById(R.id.top_name);
		productName = (TextView) findViewById(R.id.product_name);
		productPrice = (TextView) findViewById(R.id.product_price);
		proImag = (ImageView) findViewById(R.id.product_pic);
		proImag.setVisibility(View.VISIBLE);
		productDescription = (TextView) findViewById(R.id.product_description);
		orderBtn = (Button) findViewById(R.id.order_btn);
		nameTextView.setText("优惠活动");
		proTextView = (TextView) findViewById(R.id.procuct_price_text);
		dataTextView = (TextView) findViewById(R.id.product_data);
		//dataTextView.setVisibility(View.VISIBLE);

		backTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

}
