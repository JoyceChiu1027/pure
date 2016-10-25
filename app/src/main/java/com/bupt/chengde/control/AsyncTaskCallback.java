package com.bupt.chengde.control;

public interface AsyncTaskCallback<T> {
   public void onCompleted(T response);
   public void onFailed(T response);
   public void onNetError();
}
