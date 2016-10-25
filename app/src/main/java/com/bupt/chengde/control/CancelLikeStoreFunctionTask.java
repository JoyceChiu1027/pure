package com.bupt.chengde.control;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.bupt.chengde.entity.CommonBaseInfo;
import com.bupt.chengde.util.WebserviceUtils;

public class CancelLikeStoreFunctionTask extends AsyncTask<String, Void, CommonBaseInfo>{
	private static final String TAG=CancelLikeStoreFunctionTask.class.getSimpleName();
	
	private int mType;// 1为取消点赞 2为取消收藏 
    private Activity mContext;
 
    private LikeStoreTaskCallback mCallback;
    
	public CancelLikeStoreFunctionTask(Activity context,int type) {
		this.mType = type;
		this.mContext=context;
	}
    public void setCallback(LikeStoreTaskCallback callback){
    	this.mCallback=callback;
    }
	@Override
	protected CommonBaseInfo doInBackground(String... params) {
		// TODO Auto-generated method stub

		try {
			return WebserviceUtils.cancleLikeStroe(params[0], params[1],
					params[2], mType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	protected void onPostExecute(CommonBaseInfo result) {
		Log.d(TAG,
				"LikeStoreFunctionTask onPostExcute is called and result==null is "
						+ (result == null));
		if (result != null) {
			Log.d(TAG, "result.returnCode=" + result.getReturnCode());
			if (result.getReturnCode().equals(CodeConstants.RETURN_SUCCESS)) {
				
				if (mType == 1) {
					mCallback.onCancelLikeCompleted(result);
				} else if (mType == 2) {
					mCallback.onCancelStoreCompleted(result);
				}
			} else {
				mCallback.onFailed(result);
			}
		} else {
			   mCallback.onNetError();
		}
	}

}
