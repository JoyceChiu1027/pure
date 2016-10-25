package com.bupt.chengde.ui.yyt;

import com.bupt.chengde.R;
import com.bupt.chengde.ui.BaseFragment;
import com.bupt.chengde.util.Utils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * @author wyf
 * @类 :常规预约页面
 * @version 1.0
 */
public class ChangGuiyuFragment extends BaseFragment {

	private View view;
	private EditText[] editTexts = new EditText[5];
	private Button suButton;

	private String custName;
	private String phoneNo;
	private String mount;
	private String addr;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_changgui, null,false);
		initView();

		return view;
	}

	private void initView() {
		editTexts[0] = (EditText) view.findViewById(R.id.bao_fast_name);// 数量
		editTexts[1] = (EditText) view.findViewById(R.id.bao_fast_phone);// 用户姓名
		editTexts[2] = (EditText) view.findViewById(R.id.bao_fast_id);// 手机号
		editTexts[3] = (EditText) view.findViewById(R.id.bao_fast_add);// 详细地址
		editTexts[4] = (EditText) view.findViewById(R.id.bao_fast_ca);
		editTexts[4].setVisibility(View.GONE);
		editTexts[0].setHint("请输入数量");
		editTexts[0].setInputType(InputType.TYPE_CLASS_NUMBER);
		editTexts[1].setHint("请输入用户姓名");
		editTexts[1].setInputType(InputType.TYPE_CLASS_TEXT);
		editTexts[2].setHint("请输入用户手机号");
		editTexts[2].setInputType(InputType.TYPE_CLASS_NUMBER);
		editTexts[3].setHint("请输入用户地址");

		suButton = (Button) view.findViewById(R.id.bao_fast_sub);
		suButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mount = editTexts[0].getText().toString().trim();
				custName = editTexts[1].getText().toString().trim();
				phoneNo = editTexts[2].getText().toString().trim();
				addr = editTexts[3].getText().toString().trim();
				if (mount.equals("")) {
					activity.application.showToast("数量不能为空！");
					return;
				}
				if (custName.equals("")) {
					activity.application.showToast("姓名不能为空！");
					return;
				}
				if (phoneNo.equals("")) {
					activity.application.showToast("电话不能为空！");
					return;
				}
				if (!Utils.isMobileNO(phoneNo)) {
					activity.application.showToast("电话号码不存在！");
					return;
				}
				if (addr.equals("")) {
					activity.application.showToast("地址不能为空！");
					return;
				}
			}
		});
	}

}
