package com.bupt.chengde.control;

import com.bupt.chengde.entity.CommonBaseInfo;

public interface LikeStoreTaskCallback {
	 void onLikeCompleted(CommonBaseInfo response);
	 void onStoreCompleted(CommonBaseInfo response);
	 void onCancelLikeCompleted(CommonBaseInfo response);
	 void onCancelStoreCompleted(CommonBaseInfo response);
	 void onFailed(CommonBaseInfo response);
	 void onNetError();
}
