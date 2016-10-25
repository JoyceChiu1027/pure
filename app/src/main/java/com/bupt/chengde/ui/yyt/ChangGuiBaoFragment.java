package com.bupt.chengde.ui.yyt;

import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponsePayAmountInfo;
import com.bupt.chengde.ui.BaseFragment;
import com.bupt.chengde.util.Utils;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomProgressDialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * @author wyf
 * @类 :常规报修页面
 * @version 1.0
 */
public class ChangGuiBaoFragment extends BaseFragment {

	private View view;
	private EditText[] editTexts = new EditText[6];
	private Button suButton;
	private Spinner spinner;

	private String custName;
	private String phoneNo;
	private String IdNo = "";
	private String addr = "";
	private String caNo = "";
	private String gzIntro;
	private int type = 0;
	private ResponsePayAmountInfo response;
	private static final int SUBMIT_FAILURE = 0;
	private static final int SUBMIT_SUCCESS = 1;
	private static final int SUBMIT_ERROR = -1;

	private CustomProgressDialog dialog;
	private ArrayAdapter<String> adapter;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			dialog.dismiss();
			switch (msg.what) {
			case SUBMIT_SUCCESS:
				if (getActivity() != null) {
					activity.application.showToast("提交成功！");
				}
				break;
			case SUBMIT_ERROR:
				String e = (String) msg.obj;
				Log.d("ChangGuiBaoFragment", "异常信息：" + e);
				if (getActivity() != null) {
					activity.application.showToast("提交失败！");
				}
				break;
			case SUBMIT_FAILURE:
				if (getActivity() != null) {
					activity.application.showToast("提交失败," + response.getReturnMsg());
				}

			default:
				break;
			}
		};
	};

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_changgui, null, false);
		initView();

		return view;
	}

	private void initView() {
		editTexts[0] = (EditText) view.findViewById(R.id.bao_fast_name);// 姓名
		editTexts[1] = (EditText) view.findViewById(R.id.bao_fast_phone);// 电话
		editTexts[2] = (EditText) view.findViewById(R.id.bao_fast_id);// 身份证号
		editTexts[3] = (EditText) view.findViewById(R.id.bao_fast_add);// 详细地址
		editTexts[4] = (EditText) view.findViewById(R.id.bao_fast_ca);// CA卡号
		editTexts[5] = (EditText) view.findViewById(R.id.bao_reason);// 故障现象描述
		spinner = (Spinner) view.findViewById(R.id.bao_fast_type);

		suButton = (Button) view.findViewById(R.id.bao_fast_sub);
		adapter = new ArrayAdapter<String>(activity, R.layout.item2);
		adapter.add("点击选择故障类型");
		adapter.add("电视业务故障");
		adapter.add("宽带业务故障");
		adapter.add("双向业务故障");
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				type = arg2;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		suButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				custName = editTexts[0].getText().toString().trim();
				phoneNo = editTexts[1].getText().toString().trim();
				IdNo = editTexts[2].getText().toString().trim();
				addr = editTexts[3].getText().toString().trim();
				caNo = editTexts[4].getText().toString().trim();
				gzIntro = editTexts[5].getText().toString().trim();
				if (TextUtils.isEmpty(custName)) {
					activity.application.showToast("姓名不能为空！");
					return;
				}
				if (TextUtils.isEmpty(phoneNo)) {
					activity.application.showToast("电话不能为空！");
					return;
				}else if (!Utils.isMobileNO(phoneNo)) {
					activity.application.showToast("电话号码格式错误");
					return;
				}

				if (TextUtils.isEmpty(addr)) {
					activity.application.showToast("地址不能为空！");
					return;
				}

				if (type == 0) {
					activity.application.showToast("请选择故障类型");
					return;
				}
				runHandler();
				/*
				 * new Thread(new Runnable() {
				 * 
				 * @Override public void run() { Message msg =
				 * handler.obtainMessage(); try { response =
				 * WebserviceUtils.addBreakdown(custName, phoneNo, addr, caNo,
				 * IdNo, type); if (null != response && response
				 * .getReturnCode() .trim()
				 * .equals(CodeConstants.RETURN_SUCCESS)) { msg.what =
				 * SUBMIT_SUCCESS; handler.sendMessage(msg); } else if
				 * (!response.getReturnCode().trim()
				 * .equals(CodeConstants.RETURN_SUCCESS)) {
				 * Log.d("ChangGuiBaoFragment", "response.getReturnCode()" +
				 * response.getReturnCode());
				 * handler.sendEmptyMessage(SUBMIT_FAILURE); } } catch
				 * (Exception e) { e.printStackTrace(); dialog.dismiss();
				 * msg.obj = e.getMessage();
				 * handler.sendEmptyMessage(SUBMIT_ERROR); } } }).start();
				 */
			}
		});
	}

	@Override
	protected void doBackGround() throws Exception {
		response = WebserviceUtils.addBreakdown(custName, phoneNo, addr, caNo, IdNo, type, gzIntro);
	}

	@Override
	protected void doForeGround() throws Exception {

		if (response != null) {
			if (getActivity() != null) {
				if (response.getReturnCode().equals(CodeConstants.RETURN_SUCCESS)) {
					//activity.application.showToast("提交"+response.getReturnMsg()+",工作人员稍后联系您！");
				    showDialog("提示","提交"+response.getReturnMsg()+",工作人员稍后联系您！");
				}
				else{
					activity.application.showToast(response.getReturnMsg());
				}
			}
		} else {
			if (getActivity() != null) {
				activity.application.showToast("网络连接异常");
			}
		}
	}
	private void showDialog(String title ,String text){
		new AlertDialog.Builder(getActivity())
		.setTitle(title)
		.setMessage(text)
		.setNegativeButton("退出该页面", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				ChangGuiBaoFragment.this.getActivity().finish();
			}
		})
		.setPositiveButton("继续报修", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		}).create().show();
	}

}
