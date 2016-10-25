package com.bupt.chengde.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bupt.chengde.util.LogUtil;

import java.lang.reflect.Field;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseFragment extends Fragment implements
		BaseApp.Fragment{
	protected String TAG;
	protected LayoutInflater inflater;
	protected View contentView;

	protected ViewGroup container;
	protected CompositeSubscription mCompositeSubscription;
	public BaseActivity mActivity;
	public Context mContext;

	@Override
	public final View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		TAG=getClass().getName();
		mActivity=(BaseActivity) getActivity();
        mContext=mActivity.getApplicationContext();
		this.inflater = inflater;
		this.container = container;
		onCreateView(savedInstanceState);
		initViews();
		initData(savedInstanceState);
		if (contentView == null)
			return super.onCreateView(inflater, container, savedInstanceState);
		return contentView;
	}

	protected abstract void onCreateView(Bundle savedInstanceState);

	public void setContentView(View view) {
		contentView = view;
	}

	public void setContentView(int layoutResID) {
		contentView = inflater.inflate(layoutResID, container, false);
	}
	public View getContentView() {
		return contentView;
	}

	public View findViewById(int id) {
		if (contentView != null)
			return contentView.findViewById(id);
		return null;
	}
	protected void intent2Activity(Class<? extends Activity> tarActivity) {
		Intent intent = new Intent(mActivity, tarActivity);
		startActivity(intent);
	}

	protected void showToast(String msg) {
		//ToastUtils.showToast(mActivity, msg, Toast.LENGTH_SHORT);
		mActivity.showToast(msg);
	}

	protected void showLog(String msg) {
		LogUtil.i(TAG, msg);
	}

	protected void showProgressDialog() {
		mActivity.showProgressDialog();
	}

	protected void dismissProgressDialog() {
		mActivity.dismissProgressDialog();
	}



	public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
	     	mCompositeSubscription = new CompositeSubscription();
        }
		mCompositeSubscription.add(subscription);
	}

	private void onUnsubscribe() {
		//取消注册，以避免内存泄露
		if (mCompositeSubscription != null) {
			mCompositeSubscription.unsubscribe();
		}
	}
	public Context getApplicationContext() {
		return mContext;
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		contentView = null;
		container = null;
		inflater = null;
	}

	@Override
	public void onDetach() {
		LogUtil.d(TAG, "onDetach() : ");
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public void onDestroy() {
		onUnsubscribe();
		mCompositeSubscription=null;
		super.onDestroy();
	}
}
