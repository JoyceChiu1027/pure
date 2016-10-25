package com.bupt.chengde.control;

import com.bupt.chengde.entity.CommonBaseInfo;
import com.bupt.chengde.util.WebserviceUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class LikeStoreFunctionTask extends AsyncTask<String, Void, CommonBaseInfo>{
    private static final String TAG=LikeStoreFunctionTask.class.getSimpleName();
	
    private int mType;// 1为点赞 2为收藏 3为评论
    private Activity mContext;
    
    
    private LikeStoreTaskCallback mCallback;
	public LikeStoreFunctionTask(Activity context,int type) {
		this.mType = type;
		this.mContext=context;

	}
    public void setCallback(LikeStoreTaskCallback callback){
    	this.mCallback=callback;
    }
	@Override
	protected CommonBaseInfo doInBackground(String... params) {
		// TODO Auto-generated method stub
        Log.d(TAG, "LikeStoreFunctionTask/doInBackground is called");
        Log.e(TAG, "custId="+params[1]);
		try {
			return WebserviceUtils.likeStoreFunction(params[0], params[1],
					params[2], params[3], mType, "");
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
					mCallback.onLikeCompleted(result);
				} else if (mType == 2) {
					mCallback.onStoreCompleted(result);
				}
			} else {
				mCallback.onFailed(result);
			}
		} else {
			   mCallback.onNetError();
		}
	}

}
