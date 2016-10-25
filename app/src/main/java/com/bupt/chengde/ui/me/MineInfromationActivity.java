package com.bupt.chengde.ui.me;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.bupt.chengde.R;
import com.bupt.chengde.entity.ResponsePayAmountInfo;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.GlideHelper;
import com.bupt.chengde.util.LogUtil;
import com.bupt.chengde.util.Utils;
import com.bupt.chengde.util.WebserviceUtils;

/**
 * @author wyf
 * @类 :个人信息页面
 * @version 1.0
 */
public class MineInfromationActivity extends BaseActivity {
    private static final String TAG=MineInfromationActivity.class.getSimpleName();
	/* 头像文件 */
	private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

	/* 请求识别码 */
	private static final int CODE_GALLERY_REQUEST = 0xa0;
	private static final int CODE_CAMERA_REQUEST = 0xa1;
	private static final int CODE_RESULT_REQUEST = 0xa2;

	// 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
	private static int output_X = 120;
	private static int output_Y = 120;

	private LinearLayout backTextView;// 返回按钮
	private TextView addTextView;
	private TextView nameTextView;// 昵称
	private TextView shoujiTextView;// 手机号
	private TextView sexTextView;// 性别
	private TextView birthadyTextView;
	private AlertDialog SelectPicdialog;
	private ImageView touImageView;// 头像
	private ResponsePayAmountInfo ResponseHead;// 头像

	private LinearLayout[] linearLayouts = new LinearLayout[8];
	private TextView[] textViews = new TextView[8];
	private TextView jifenTextView;

	@SuppressWarnings("unused")
	//private ImageLoader imageLoader;
//	@SuppressWarnings("unused")
//	private DisplayImageOptions options;

	private String picturePath = null;
	public static final int SELECT_PIC_BY_TACK_PHOTO = 1;

	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_infromation);

//		imageLoader = ImageLoader.getInstance();
//		options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
//				.showImageForEmptyUri(R.drawable.default_head_icon).showImageOnFail(R.drawable.default_head_icon)
//				.showImageOnLoading(R.drawable.default_head_icon)
//				.displayer(new CircleBitmapDisplayer()).build();
		initView();

	}

	@Override
	protected void onStart() {
		super.onStart();
		reFresh();
	}

	private void initView() {
		touImageView = (ImageView) findViewById(R.id.mine_tou);
		backTextView = (LinearLayout) findViewById(R.id.top_back);
		addTextView = (TextView) findViewById(R.id.top_name);
		nameTextView = (TextView) findViewById(R.id.mine_nicheng);
		shoujiTextView = (TextView) findViewById(R.id.mine_shouji);
		sexTextView = (TextView) findViewById(R.id.mine_sex);
		birthadyTextView = (TextView) findViewById(R.id.mine_birthday);
		addTextView.setText("个人信息");
		linearLayouts[0] = (LinearLayout) findViewById(R.id.mine_line_touxiang);
		linearLayouts[1] = (LinearLayout) findViewById(R.id.mine_line_nicheng);
		linearLayouts[2] = (LinearLayout) findViewById(R.id.mine_line_shouji);
		linearLayouts[3] = (LinearLayout) findViewById(R.id.mine_line_changepwd);
		linearLayouts[4] = (LinearLayout) findViewById(R.id.mine_line_sex);
		linearLayouts[5] = (LinearLayout) findViewById(R.id.mine_line_birthday);
		linearLayouts[6] = (LinearLayout) findViewById(R.id.mine_line_address);
		linearLayouts[7] = (LinearLayout) findViewById(R.id.mine_line_jifeng);
		textViews[0] = (TextView) findViewById(R.id.min_touxing_text);
		textViews[1] = (TextView) findViewById(R.id.min_nicheng_text);
		textViews[2] = (TextView) findViewById(R.id.min_birthday_text);
		textViews[3] = (TextView) findViewById(R.id.min_address_text);
		textViews[4] = (TextView) findViewById(R.id.min_jifeng_text);
		textViews[5] = (TextView) findViewById(R.id.min_changepwd_text);
		textViews[6] = (TextView) findViewById(R.id.min_sex_text);
		textViews[7] = (TextView) findViewById(R.id.min_shouji_text);
		jifenTextView = (TextView) findViewById(R.id.mine_jifeng);

		backTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	private void reFresh() {
		if (TextUtils.isEmpty(application.getCustName()) || application.getCustName().equals("null")) {
			nameTextView.setText("待完善");
		} else {
			nameTextView.setText(application.getCustName());
		}

		if (TextUtils.isEmpty(application.getCustInt()) || application.getCustInt().equals("null")) {
			jifenTextView.setText("0");
		} else {
			jifenTextView.setText(application.getCustInt());
		}
		if (TextUtils.isEmpty(application.getUserGender()) || application.getUserGender().equals("null")) {
			sexTextView.setText("待完善");
		} else {
			sexTextView.setText(application.getUserGender());
		}
		if (TextUtils.isEmpty(application.getCustPhone()) || application.getCustPhone().equals("null")) {
			shoujiTextView.setText("待完善");
		} else {
			shoujiTextView.setText(application.getCustPhone());
		}
		if (TextUtils.isEmpty(application.getUserBirthday()) || application.getUserBirthday().equals("null")) {
			birthadyTextView.setText("待完善");
		} else {
			birthadyTextView.setText(application.getUserBirthday());
		}
		if (application.getAvatarUrl() == null || application.getAvatarUrl().isEmpty()) {
			touImageView.setBackgroundResource(R.drawable.default_head_icon);
		} else {
			//imageLoader.displayImage(WebserviceUtils.URL_PREFIX + application.getPortraiturl(), touImageView, options);
			GlideHelper.showAvatarWithUrl(this,application.getAvatarUrl(),touImageView);
		}
	}

	// 显示相片选择方式对话框
	private void ShowSelectPicDialog() {
		SelectPicdialog = new Builder(this).create();
		SelectPicdialog.show();
		Window window = SelectPicdialog.getWindow();
		window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		window.setContentView(R.layout.select_pic_dialog);
		// 为确认按钮添加事件
		Button takePhoto = (Button) window.findViewById(R.id.btn_take_photo);
		takePhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SelectPicdialog.cancel();
				// takePhoto();
				choseHeadImageFromCameraCapture();
			}
		});
		Button pickPhoto = (Button) window.findViewById(R.id.btn_pick_photo);
		pickPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SelectPicdialog.cancel();
				// pickPhoto();
				choseHeadImageFromGallery();
			}
		});
		Button cancel = (Button) window.findViewById(R.id.btn_cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SelectPicdialog.cancel();
			}
		});
	}

	// 拍照
	private void takePhoto() {
		String SDState = Environment.getExternalStorageState();
		if (SDState.equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			if (!outDir.exists()) {
				outDir.mkdirs();
			}
			File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");

			picturePath = outFile.getAbsolutePath();

			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
			startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
		} else {
			application.showToast("相片未选择");
		}

	}

	// 从相册选择
	private void pickPhoto() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
	}

	// // 选择结果
	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// if (requestCode == SELECT_PIC_BY_TACK_PHOTO) {
	// if (resultCode == RESULT_OK) {
	// // 拍照
	// Utils.setImagePhoto(picturePath, touImageView,getApplication());
	// application.setPortraiturl(picturePath);
	// }else {
	// application.showToast("失败");
	// }
	// } else if (requestCode == SELECT_PIC_BY_PICK_PHOTO) {
	// if (resultCode == RESULT_OK) {
	// Uri uri = data.getData();
	// if (uri != null) {
	// String realPath = getRealPathFromURI(uri);
	// Utils.setImagePhoto(realPath, touImageView,getApplication());
	// application.setPortraiturl(realPath);
	// }else {
	// application.showToast("失败");
	// }
	// }
	// }
	// }

	// 获取相册图片
	private String getRealPathFromURI(Uri mUri) {
		if (mUri.getScheme().equals("file")) {
			return mUri.getPath();
		} else {
			String imgPath;
			Cursor cursor = getContentResolver().query(mUri, null, null, null, null);
			cursor.moveToFirst();
			imgPath = cursor.getString(1);
			return imgPath;
		}
	}

	public void click1(View view) {
		switch (view.getId()) {
		case R.id.mine_line_touxiang:// 头像
			ShowSelectPicDialog();
			break;
		case R.id.mine_line_nicheng:// 昵称
			Intent intent1 = new Intent();
			intent1.setClass(getApplicationContext(), ChangeNichengActivity.class);
			intent1.putExtra("nicheng", ((TextView) findViewById(R.id.mine_nicheng)).getText().toString());
			startActivity(intent1);
			break;
		case R.id.mine_line_shouji:// 手机号
			Intent intent3 = new Intent();
			intent3.setClass(getApplicationContext(), ChangePhoneActivity.class);
			intent3.putExtra("phone", ((TextView) findViewById(R.id.mine_shouji)).getText().toString());
			startActivity(intent3);
			break;
		case R.id.mine_line_changepwd:// 修改密码
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), ChangePwdActivity.class);
			startActivity(intent);
			break;
		case R.id.mine_line_sex:// 性别
			Intent intent2 = new Intent();
			intent2.setClass(getApplicationContext(), ChangeSexActivity.class);
			intent2.putExtra("sex", ((TextView) findViewById(R.id.mine_sex)).getText().toString());
			startActivity(intent2);
			break;
		case R.id.mine_line_birthday:// 生日
			Intent intent5 = new Intent();
			intent5.setClass(getApplicationContext(), ChangeBirthdayActivity.class);
			startActivity(intent5);
			break;
		case R.id.mine_line_address:// 地址
			Intent intent4 = new Intent();
			intent4.setClass(getApplicationContext(), ChangeAddressActivity.class);
			startActivity(intent4);
			break;
		case R.id.layout:// 退出登录
			AlertDialog ad = new Builder(MineInfromationActivity.this).setTitle("退出登录").setMessage("将要退出登录！")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							application.reSet();
							JPushInterface.setAlias(getApplicationContext(), "", mAliasCallback);
							startActivity(new Intent(MineInfromationActivity.this, LoginActivity.class));
							finish();
							// AppManager.getAppManager().finishAllActivity();
							// AppManager.getAppManager().finishAllActivity();

						}
					}).setNegativeButton("取消", null).create();
			ad.show();

			break;

		default:
			break;
		}
	}

	/**
	 * 从本地相册选取图片作为头像
	 */
	private void choseHeadImageFromGallery() {
		// Intent intentFromGallery = new Intent();
		// // 设置文件类型
		// intentFromGallery.setType("image/*");
		// intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
		// startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);

		Intent intent = new Intent(Intent.ACTION_PICK, null);
		// 此处调用了图片选择器
		// 如果直接写intent.setDataAndType("image/*");
		// 调用的是系统图库
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		startActivityForResult(intent, CODE_GALLERY_REQUEST);
	}

	/**
	 * 启动手机相机拍摄照片作为头像
	 */

	private void choseHeadImageFromCameraCapture() {
		Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 判断存储卡是否可用，存储照片文件
		if (hasSdcard()) {
			intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
		}
		startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// 用户没有进行有效的设置操作，返回
		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
			return;
		}
		switch (requestCode) {
		case CODE_GALLERY_REQUEST:
			cropRawPhoto(intent.getData());
			break;
		case CODE_CAMERA_REQUEST:
			if (hasSdcard()) {
				File tempFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
				cropRawPhoto(Uri.fromFile(tempFile));
			} else {
				Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG).show();
			}
			break;
		case CODE_RESULT_REQUEST:
			if (intent != null) {
				setImageToHeadView(intent);
				runHandler();
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}

	/**
	 * 裁剪原始的图片
	 */
	public void cropRawPhoto(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");

		// 设置裁剪
		intent.putExtra("crop", "true");

		// aspectX , aspectY :宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX , outputY : 裁剪图片宽高
		intent.putExtra("outputX", output_X);
		intent.putExtra("outputY", output_Y);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, CODE_RESULT_REQUEST);
	}

	/**
	 * 提取保存裁剪之后的图片数据
	 */
	private void setImageToHeadView(Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			picturePath = saveMyBitmap(photo);
		}
	}

	/**
	 * 检查设备是否存在SDCard的工具方法
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			// 有存储的SDCard
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 保存剪裁后的图片到指定目录
	 */
	public String saveMyBitmap(Bitmap mBitmap) {
		File sdcard = new File(Environment.getExternalStorageDirectory() + File.separator + "save");
		if (!sdcard.exists()) {
			sdcard.mkdirs();
		}
		File userHeadFile = new File(sdcard, "UserHead.jpg");
		try {
			FileOutputStream out = new FileOutputStream(userHeadFile);
			mBitmap.compress(CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String picturePath = null;
		try {
			picturePath = userHeadFile.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		com.bupt.chengde.util.LogUtil.i("=====", "picturePath:" + picturePath);
		return picturePath;
	}

	@Override
	protected void doBackGround() throws Exception {
		String fileName = "touxiang" + picturePath.substring(picturePath.length() - 4, picturePath.length());
		String uploadBuffer = Utils.fileTobuffer(picturePath);
		ResponseHead = WebserviceUtils.updatePicture(application.getCustID(), fileName, uploadBuffer);
		LogUtil.i("=====", "ResponseHead=" + ResponseHead.toString());
	}
  
	@Override
	protected void doForeGround() throws Exception {
		if (ResponseHead != null) {
			if (Integer.parseInt(ResponseHead.getReturnCode()) == 0) {
				application.showToast("头像上传成功");
				application.setAvatarUrl(ResponseHead.getUrl());
				//imageLoader.displayImage(WebserviceUtils.URL_PREFIX + application.getPortraiturl(), touImageView, options);
				GlideHelper.showAvatarWithUrl(activity,application.getAvatarUrl(),touImageView);
			}else {
				application.showToast(ResponseHead.getReturnMsg());
			}
		}else {
			application.showToast("网络连接异常");
		}
	}
	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs;
			switch (code) {
			case 0:
				logs = "Set tag and alias success";
				com.bupt.chengde.util.LogUtil.i(TAG, logs);
				break;

			case 6002:
				logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
				com.bupt.chengde.util.LogUtil.i(TAG, logs);
				if (Utils.isNetworkAvailable(activity)) {
					// mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS,
					// alias), 1000 * 60);

				} else {
					com.bupt.chengde.util.LogUtil.i(TAG, "No network");
				}
				break;

			default:
				logs = "Failed with errorCode = " + code;
				com.bupt.chengde.util.LogUtil.e(TAG, logs);
			}

			// ExampleUtil.showToast(logs, getApplicationContext());
		}

	};

}
