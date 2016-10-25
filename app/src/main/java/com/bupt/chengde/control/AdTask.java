package com.bupt.chengde.control;

import java.util.List;

import com.bupt.chengde.entity.ResponseAdPoster;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomProgressDialog;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class AdTask extends AsyncTask<String, Void, List<ResponseAdPoster>> {
	private List<ResponseAdPoster> mDatas;
	private int mType;
	private Context mContext;
	private CustomProgressDialog pd;
	private AsyncTaskCallback<List<ResponseAdPoster>> mCallback;
	public AdTask(int type,Context context,List<ResponseAdPoster> returnList) {
		// TODO Auto-generated constructor stub
		this.mDatas=returnList;
		this.mContext=context;
		this.mType=type;
	
	}
    public void setCallback(AsyncTaskCallback<List<ResponseAdPoster>> callback){
    	mCallback=callback;
    }
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	
	}

	@Override
	protected List<ResponseAdPoster> doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			return WebserviceUtils.getAdPosterInfo(mType, params[0]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	protected void onPostExecute(List<ResponseAdPoster> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (result!=null) {
			if (result.size()>0) {
				mCallback.onCompleted(result);
			}else{
				mCallback.onFailed(result);
			}		
		}else{
			 mCallback.onNetError();
		}
		
	}

}
