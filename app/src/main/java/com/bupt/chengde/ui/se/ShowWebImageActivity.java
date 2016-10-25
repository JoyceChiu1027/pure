package com.bupt.chengde.ui.se;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.util.LogUtil;

public class ShowWebImageActivity extends Activity{
	private static final String TAG=ShowWebImageActivity.class.getSimpleName();
    private String imageUrl="";
   // private static ImageLoader imageLoader;
	//private static DisplayImageOptions options;
	private TextView imageTextView = null;  
    private String imagePath = null;  
    //private ZoomableImageView imageView = null; 
    private ImageView imageView=null;
    private RelativeLayout imageContentRl=null;
	private int mWidth;
	private int mHeight;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_webimage);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mWidth = dm.widthPixels;
		mHeight = dm.heightPixels;
//		imageLoader = ImageLoader.getInstance();
//
//		options = new DisplayImageOptions.Builder().cacheInMemory(true)
//				.cacheOnDisk(true)
//				.showImageOnLoading(R.drawable.default_ad_big)
//				.showImageForEmptyUri(R.drawable.default_ad_big)
//				.showImageOnFail(R.drawable.default_ad_big)
//				.build();

		imageUrl=getIntent().getStringExtra("image");
		LogUtil.i(TAG,"imageUrl="+imageUrl);
		imageView=(ImageView) findViewById(R.id.show_webimage_imageview);
		imageContentRl=(RelativeLayout) findViewById(R.id.image_content);
		//imageView=new ImageView(this);
		//RelativeLayout.LayoutParams layoutParam=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		//imageView.setScaleType(ScaleType.CENTER);
		//imageContentRl.addView(imageView,layoutParam);
		
		//imageView.setLayoutParams();
		//imageLoader.displayImage(imageUrl, imageView, options);
		/*imageLoader.displayImage(imageUrl, imageView, options, new SimpleImageLoadingListener(){
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				// TODO Auto-generated method stub
				super.onLoadingComplete(imageUri, view, loadedImage);
			}
		});*/
//		imageLoader.loadImage(imageUrl, options, new SimpleImageLoadingListener(){
//			@Override
//			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//				// TODO Auto-generated method stub
//				 super.onLoadingComplete(imageUri, view, loadedImage);
//				 int width=mWidth;
//
//				 int loadedWidth=loadedImage.getWidth();
//				 int loadedHeigh=loadedImage.getHeight();
//				 //double scale=loadedHeigh/loadedWidth;
//				 int height=(int) (loadedHeigh*mWidth/loadedWidth);
//				 //LogUtil.e(TAG, "onLoadingComplete/scale="+scale);
//				 LogUtil.w(TAG, "onLoadingComplete/height="+height);
//				 LogUtil.e(TAG, "loadedImage.getWidth="+loadedWidth+"....loadedImage.getHeight="+loadedHeigh);
//				 imageView.setLayoutParams(new RelativeLayout.LayoutParams(width,height));
//			     imageView.setImageBitmap(loadedImage);
//			    // imageContentRl.addView(imageView);
//				 LogUtil.i(TAG, "imageView.getWidth="+imageView.getWidth()+"....imageView.getHeight="+imageView.getHeight());
//
//			}
//		});
		//imageLoader.displayImage(uri, imageView, new ImageSize(width, height));
		findViewById(R.id.top_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowWebImageActivity.this.finish();
			}
		});
	}
}
