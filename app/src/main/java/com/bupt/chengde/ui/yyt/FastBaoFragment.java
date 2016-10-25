package com.bupt.chengde.ui.yyt;

import com.bupt.chengde.R;
import com.bupt.chengde.ui.BaseFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * @author wyf
 * @类 :快速报修页面
 * @version 1.0
 */
public class FastBaoFragment extends BaseFragment {

	private AlertDialog dialog;
	private TextView textView;
	private View view;
	private LinearLayout layout ;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_fast, null,false);
		textView = (TextView) view.findViewById(R.id.bao_fast_phone);
		textView.setText(getResources().getString(R.string.phone));
		layout = (LinearLayout) view.findViewById(R.id.bao_fast);
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showDialog(textView);
			}

		});
		return view;
	}
	
	private void showDialog(final TextView textView) {
		dialog = new Builder(getActivity()).create();
		dialog.show();
		Window window = dialog.getWindow();
		window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		window.setContentView(R.layout.fast_diallog);
		Button lianxiButton = (Button) window.findViewById(R.id.lianxi);
		lianxiButton.setText("维修热线："+textView.getText().toString());
		lianxiButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+textView.getText().toString()));    
				startActivity(intent); 
			}
		});
		Button cancleButton = (Button) window.findViewById(R.id.cancel);
		cancleButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
	}
}
