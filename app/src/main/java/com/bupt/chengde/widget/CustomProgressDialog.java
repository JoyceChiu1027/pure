package com.bupt.chengde.widget;


import com.bupt.chengde.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author wyf
 * @类 :自定义进度条
 * @version 1.0
 */
public class CustomProgressDialog extends Dialog {

	private static CustomProgressDialog customProgressDialog = null;

	public CustomProgressDialog(Context context) {

		super(context);
	}

	public CustomProgressDialog(Context context, int theme) {

		super(context, theme);
	}

	public static CustomProgressDialog createDialog(Context context) {

		customProgressDialog = new CustomProgressDialog(context,
				R.style.CustomProgressDialog);
		customProgressDialog.setContentView(R.layout.customprogressdialog);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		customProgressDialog.onWindowFocusChanged();
		customProgressDialog.getWindow().getAttributes().dimAmount = 0.1f;
		// 调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用
		customProgressDialog.setCanceledOnTouchOutside(false);
		// 调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
		customProgressDialog.setCancelable(true);

		return customProgressDialog;
	}

	public void onWindowFocusChanged() {

		if (customProgressDialog != null) {
			ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
			AnimationDrawable animationDrawable = (AnimationDrawable) imageView
					.getBackground();
			animationDrawable.start();
		}

	}

	/**
	 * 
	 * [Summary] setTitile 标题
	 * 
	 * @param strTitle
	 * @return
	 * 
	 */
	public CustomProgressDialog setTitile(String strTitle) {

		return customProgressDialog;
	}

	/**
	 * 
	 * [Summary] setMessage 提示内容
	 * 
	 * @param strMessage
	 * @return
	 * 
	 */
	public CustomProgressDialog setMessage(String strMessage) {

		TextView tvMsg = (TextView) customProgressDialog
				.findViewById(R.id.id_tv_loadingmsg);

		if (tvMsg != null) {
			tvMsg.setText(strMessage);
		}

		return customProgressDialog;
	}
}
