package com.bupt.chengde.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bupt.chengde.R;
import com.bupt.chengde.entity.CommonBaseInfo;
import com.bupt.chengde.entity.ResponseProgramInfo;
import com.bupt.chengde.util.LogUtil;
import com.bupt.chengde.util.Utils;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.AlwaysMarqueeTextView;

public class JiemuAdapter extends BaseAdapter {
    private static final String TAG=JiemuAdapter.class.getSimpleName();
	private List<ResponseProgramInfo> returnList;
	private Context mContext;
	LayoutInflater inflater;
	private Date nowDate;
	private Date date;
	viewHolder holder;
	private String phoneNo;
	// private static SimpleDateFormat dfHM = new SimpleDateFormat("HH:mm:ss",
	// Locale.CHINA);
	private static SimpleDateFormat dfYMDHM = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.CHINA);

	public JiemuAdapter(Context context, List<ResponseProgramInfo> returnList,
			String phoneNo) {
		this.mContext = context;
		this.returnList = returnList;
		this.phoneNo = phoneNo;
		nowDate = new Date();
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return returnList.size();
	}

	@Override
	public Object getItem(int position) {
		return returnList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		com.bupt.chengde.util.LogUtil.d(TAG, "getView is called");
		holder = null;
		if (convertView == null) {
			holder = new viewHolder();
			convertView = inflater.inflate(R.layout.jiemuitem, null, false);
			holder.time = (TextView) convertView
					.findViewById(R.id.program_time_text);
			holder.name = (AlwaysMarqueeTextView) convertView
					.findViewById(R.id.program_name_text);
			holder.state = (TextView) convertView
					.findViewById(R.id.program_state);
			holder.reserveBtn = (ImageButton) convertView
					.findViewById(R.id.program_reserved);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		String time = returnList.get(position).getBroadCastTime();
		com.bupt.chengde.util.LogUtil.w(TAG, "time.length"+time.length());
		holder.time.setText(time.substring(11,16));
		holder.name.setText(returnList.get(position).getProdName());
		try {
			date = dfYMDHM.parse(time);
			com.bupt.chengde.util.LogUtil.d(TAG, "nowDate.gettime()="+nowDate.getTime());
			if (date.getTime() > nowDate.getTime()) {
				holder.state.setVisibility(View.GONE);
				holder.reserveBtn.setVisibility(View.VISIBLE);
			} else {
				holder.state.setVisibility(View.VISIBLE);
				holder.reserveBtn.setVisibility(View.GONE);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			holder.state.setVisibility(View.GONE);
			holder.reserveBtn.setVisibility(View.GONE);
			e.printStackTrace();
		}
		ResponseProgramInfo response = returnList.get(position);
		com.bupt.chengde.util.LogUtil.d(TAG, "status=" + response.getStatus());
		if (response.getStatus() == 1) {
			holder.reserveBtn.setBackgroundResource(R.drawable.btn_reserved);
		} else {
			holder.reserveBtn.setBackgroundResource(R.drawable.btn_reserve);
		}
		
		final int pos = position;
		holder.reserveBtn.setOnClickListener(new MyAdapterListener(pos));
		return convertView;
	}

	public class viewHolder {
		private TextView time;
		private AlwaysMarqueeTextView name;
		private TextView state;
		private ImageButton reserveBtn;
	}

	class MyAdapterListener implements OnClickListener {

		private int mPos;

		public MyAdapterListener(int pos) {
			mPos = pos;
		}

		@Override
		public void onClick(View view) {
			// Toast.makeText(mContext, "您点击了-" + returnList.get(position),
			// Toast.LENGTH_LONG).show();
			com.bupt.chengde.util.LogUtil.e(TAG, "holder.reserveBtn mPos=" +mPos + " onclick is called");

			if (!Utils.isNetworkAvailable(mContext)) {
				Toast.makeText(mContext, "网络不可用，不可执行该操作！", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			if (TextUtils.isEmpty(phoneNo) || phoneNo.equals("null")) {
				Toast.makeText(mContext, "登录后才可预约！请先登录!", Toast.LENGTH_SHORT)
						.show();
				return;
			}

			if (returnList.get(mPos).getStatus() == 0) {// 该节目为未预约状态，点击按钮预约

				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							// Looper.prepare();
							com.bupt.chengde.util.LogUtil.d(TAG,
									"new Thread(reservation) is run");
							com.bupt.chengde.util.LogUtil.d(TAG, "phoneNo"
									+ phoneNo);
							CommonBaseInfo response = WebserviceUtils
									.reservationProg(phoneNo,
											returnList.get(mPos).getPrevueId(),
											"1");
							if (null != response) {
								// notifyDataSetChanged();
								com.bupt.chengde.util.LogUtil.d(TAG,
										"预约的 returnCode="
												+ response.getReturnCode());
								// handler.sendEmptyMessage(RESERVED_SUCCESS);
								// Log.d("", msg)
								returnList.get(mPos).setStatus(1);
							} else {
								LogUtil.d(TAG,
										"预约的response is null="
												+ (response == null));

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

						}

					}
				}).start();
				view.setBackgroundResource(R.drawable.btn_reserved);
				returnList.get(mPos).setStatus(1);
				Toast.makeText(mContext, "节目已预约！", Toast.LENGTH_SHORT).show();

			} else if (returnList.get(mPos).getStatus() == 1) {// 节目为已预约状态，点击执行取消预约功能

				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							// Looper.prepare();
							com.bupt.chengde.util.LogUtil.d(TAG,
									"data.get(pos).getStatus().equals('1')");
							com.bupt.chengde.util.LogUtil.d(TAG,
									"new Thread(cancel) is run");
							com.bupt.chengde.util.LogUtil.d(TAG, "phoneNo="
									+ phoneNo);
							CommonBaseInfo response = WebserviceUtils
									.reservationProg(phoneNo,
											returnList.get(mPos).getPrevueId(),
											"0");
							if (null != response) {
								// notifyDataSetChanged();
								com.bupt.chengde.util.LogUtil.d(TAG,
										"预约的 returnCode="
												+ response.getReturnCode());
								// handler.sendEmptyMessage(RESERVED_SUCCESS);
								returnList.get(mPos).setStatus(0);
							} else {
								com.bupt.chengde.util.LogUtil.d(TAG,
										"取消预约的response is null="
												+ (response == null));
								// handler.sendEmptyMessage(RESERVED_FAILED);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							// handler.sendEmptyMessage(RESERVED_ERROR);
						}

					}
				}).start();
				returnList.get(mPos).setStatus(0);
				view.setBackgroundResource(R.drawable.btn_reserve);
				Toast.makeText(mContext, "预定已取消！", Toast.LENGTH_SHORT).show();

			}
		}
	}
}
