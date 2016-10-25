package com.bupt.chengde.ui.se;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import com.bupt.chengde.R;
import com.bupt.chengde.adapter.PhotographerListAdapter;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.Category;
import com.bupt.chengde.entity.ResponsePhotographer;
import com.bupt.chengde.ui.BaseFragment;
import com.bupt.chengde.util.LogUtil;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.bupt.chengde.widget.XListView;

public class PhotographFellowFragment extends BaseFragment implements OnItemClickListener {
	private static final String TAG = PhotographFellowFragment.class.getSimpleName();
	private XListView mXListView;

	private PhotographerListAdapter adapter;
	private List<Category> mDatas = new ArrayList<Category>();

	private View refresh;// 刷新按钮
	private CustomFrameLayout customFrameLayout;
	private TextView errorTxt;
	CustomProgressDialog pd;
	private PhotographerTask task;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_photographer, null, false);
		mXListView = (XListView) view.findViewById(R.id.comm_listview);
		mXListView.setPullLoadEnable(false);
		mXListView.setPullRefreshEnable(false);
		;
		mXListView.setOnItemClickListener(this);
		customFrameLayout = (CustomFrameLayout) view.findViewById(R.id.web_frame);
		customFrameLayout.setList(new int[] { R.id.comm_listview, R.id.common_net_error });
		refresh = view.findViewById(R.id.error_btn);
		errorTxt = (TextView) view.findViewById(R.id.error_txt);
		pd = CustomProgressDialog.createDialog(getActivity());
		pd.setMessage("正在请求数据");
		refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pd.show();
				customFrameLayout.show(R.id.comm_listview);
				new PhotographerTask().execute();

			}
		});

		task = new PhotographerTask();
		task.execute();
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), PhotographDetailActivity.class);
		intent.putExtra(CodeConstants.MODULE_ID, CodeConstants.PHOTO_PHOTOGRAPHER);
		intent.putExtra(CodeConstants.BUSI_ID,
				((ResponsePhotographer) adapter.getItem(position - 1)).getPhotographerId());
		startActivity(intent);
	}

	class PhotographerTask extends AsyncTask<Void, Void, ResponsePhotographer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd.setCancelable(false);
		}

		@Override
		protected ResponsePhotographer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return WebserviceUtils.getPhotographerInfo();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(ResponsePhotographer result) {
			pd.dismiss();

			if (result != null) {
				if (!TextUtils.isEmpty(result.getJson()) && !result.getJson().equals("null")) {
					try {
						LogUtil.i(TAG, "result.getJson=" + result.getJson());
						jiexi(result.getJson());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						customFrameLayout.show(R.id.common_net_error);
						errorTxt.setText("数据解析异常");
					}
				} else {
					customFrameLayout.show(R.id.common_net_error);
					errorTxt.setText(getResources().getString(R.string.server_no_data));

				}
			} else {
				customFrameLayout.show(R.id.common_net_error);
				errorTxt.setText(getResources().getString(R.string.server_time_out));

			}
		}
	}

	private void jiexi(String json) throws JSONException {

		JSONArray jsonArr = new JSONArray(json);// 返回json为json数组
		int length = jsonArr.length();
		com.bupt.chengde.util.LogUtil.w(TAG, "jsonArr.length=" + length);
		mDatas.clear();
		for (int i = 0; i < length; i++) {

			JSONObject jsonObj = jsonArr.getJSONObject(i);
			JSONArray contentArr = jsonObj.getJSONArray("content");
			Category category = new Category(jsonObj.getString("name"));
			com.bupt.chengde.util.LogUtil.i(TAG, "contentArr.length=" + contentArr.length());
			for (int j = 0; j < contentArr.length(); j++) {

				JSONObject obj = contentArr.getJSONObject(j);
				ResponsePhotographer response = new ResponsePhotographer();
				response.setPhotographerId(obj.getInt("photographerId") + "");
				response.setPhotographerName(obj.getString("photographerName"));
				response.setPhotographerUrl(obj.getString("photographerUrl"));
				// String name=obj.getString("photographerName");
				category.addItem(response);
			}

			mDatas.add(category);
		}
		if (mDatas.size() > 0) {
			if (adapter == null) {
				adapter = new PhotographerListAdapter(getActivity(), mDatas);
				mXListView.setAdapter(adapter);
			} else {
				adapter.notifyDataSetChanged();
			}
		} else {
			
			customFrameLayout.show(R.id.common_net_error);
			errorTxt.setText("服务器返回无数据！");
		}

	}

	@Override
	public void onDestroy() {
		if (this.task != null)
			this.task.cancel(true);
		super.onDestroy();
	}
}
