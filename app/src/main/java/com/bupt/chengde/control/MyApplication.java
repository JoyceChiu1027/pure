package com.bupt.chengde.control;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

import com.bupt.chengde.R;
import com.bupt.chengde.util.GlideHelper;
import com.bupt.chengde.util.SharePreferenceManager;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

/**
 * @author wyf
 * @类 :自定义application
 * @version 1.0
 */
public class MyApplication extends Application {

	private static MyApplication application;
	
	/**
	 * 客户编号
	 */
//	private String custId;
	
	 /**
	  *  头像url
	  */
    private String avatarUrl;

    /**
     *  性别
     */
    private String user_gender;

    /**
     *  昵称
     */
    private String custName;

    /**
     *  生日
     */
    private String user_birthday;

    /**
     *  登录密码
     */
    private String password;

    /**
     *  地址
     */
    private String address;

    /**
     *  手机
     */
    private String custPhone;

    /**
     * 签到时间
     */
    private String lastModifiedTime;
    
    /**
     * 评论时间
     */
    private String discussTime;
    
    /**
     * 积分
     */
    private String custInt;

    // sharepreference的存储
    private static final String PREF_QIANDAO = "qiandao";//签到
    
    private static final String PREF_PINGLUN = "pinglun";//签到    
    
    private static final String PREF_AVATAR_URL = "portrait_url";//头像url

    private static final String PREF_USER_GENDER = "user_gender";//性别

    private static final String PREF_USER_NAME = "username";//用户名

    private static final String PREF_USER_BIRTHDAY = "user_birthday";//生日

    private static final String PREF_PASSWORD = "password";//密码

    private static final String PREF_ADDRESS = "address";//地址

    private static final String PREF_PHONE = "phone";//手机号

    private static final String PREF_INTEGRAL = "integral";//积分
    private static final String PREF_CUSTID = "custId";//用户号
    private static final String SHARED_PREFERENCE_NAME="chengde_sp";
    
	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
	
		Log.i("MyApplication", "onCreate is called");
		SharePreferenceManager.init(getApplicationContext(), SHARED_PREFERENCE_NAME);
//		File cacheDir = StorageUtils.getOwnCacheDirectory(
//				getApplicationContext(), "chengde_cache");
//		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //
//				.showImageForEmptyUri(R.drawable.default_title) // 设置图片Uri为空或是错误的时候显示的图
//				.showImageOnFail(R.drawable.default_title) // 设置图片加载/解码过程中错误时候显示的图片
//				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
//				//.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
//				.cacheOnDisk(true)
//				.build();//
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration//
//		.Builder(getApplicationContext())
//				.defaultDisplayImageOptions(defaultOptions)
//				.memoryCacheExtraOptions(480, 800)
//				.denyCacheImageMultipleSizesInMemory()
//				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
//				.memoryCacheSize(2 * 1024 * 1024)
//				.diskCache(new UnlimitedDiskCache(cacheDir))
//				.diskCacheFileCount(100)// 缓存一百张图片
//				.diskCacheSize(50 * 1024 * 1024)
//				/*
//				.discCache(new UnlimitedDiskCache(cacheDir))// 自定义缓存路径
//*/				.imageDownloader(
//						new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s) 超时时间
//				//.writeDebugLogs()//
//				.build();//
//		ImageLoader.getInstance().init(config);
	    JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

	}
	
	public static MyApplication getMyApplication() {
		return application;
	}

	public void showToast(String msg) {
		Toast.makeText(application, msg + "", Toast.LENGTH_SHORT).show();
	}
	
	 /**
     * 获取用户性别
     *
     * @return
     */
    public String getUserGender() {
       // if (TextUtils.isEmpty(user_gender)) {
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(MyApplication.this);
            user_gender = preferences.getString(PREF_USER_GENDER, null);
        //}
        return user_gender;
    }

    /**
     * 设置用户性别
     *
     * @param user_gender
     */
    public void setUserGender(String user_gender) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(MyApplication.this);
        Editor editor = preferences.edit();
		editor.putString(PREF_USER_GENDER, user_gender).commit();
      /*  if (editor.putString(PREF_USER_GENDER, user_gender).commit()) {
            this.user_gender = user_gender;
        }*/
    }

    /**
     * 设置积分
     *
     * @param custInt
     */
    public void setCustInt(String custInt) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(MyApplication.this);
        Editor editor = preferences.edit();
        editor.putString(PREF_INTEGRAL, custInt).commit();
      /*  if (editor.putString(PREF_INTEGRAL, custInt).commit()) {
            this.custInt = custInt;
        }*/
    }

    /**
     * 获取积分
     *
     * @return
     */
    public String getCustInt() {
      //  if (TextUtils.isEmpty(custInt)) {
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(MyApplication.this);
            custInt = preferences.getString(PREF_INTEGRAL, null);
        //}
        return custInt;
    }
    
    /**
     * 设置评论状态
     *
     * @param discussTime
     */
    public void setDiscussTime(String discussTime) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(MyApplication.this);
        Editor editor = preferences.edit();
        editor.putString(PREF_PINGLUN, discussTime).commit();
        /*if (editor.putString(PREF_PINGLUN, discussTime).commit()) {
            this.discussTime = discussTime;
        }*/
    }

    /**
     * 获取评论状态
     *
     * @return
     */
    public String getDiscussTime() {
//        if (TextUtils.isEmpty(discussTime)) {
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(MyApplication.this);
           discussTime = preferences.getString(PREF_PINGLUN, null);
//        }
        return discussTime;
    }
    
    /**
     * 设置签到状态
     *
     * @param lastModifiedTime
     */
    public void setLastModifiedTime(String lastModifiedTime) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(MyApplication.this);
        Editor editor = preferences.edit();
        editor.putString(PREF_QIANDAO, lastModifiedTime).commit();
        /*if (editor.putString(PREF_QIANDAO, lastModifiedTime).commit()) {
            this.lastModifiedTime = lastModifiedTime;
        }*/
    }

    /**
     * 获取签到状态
     *
     * @return
     */
    public String getLastModifiedTime() {
       // if (TextUtils.isEmpty(lastModifiedTime)) {
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(MyApplication.this);
            lastModifiedTime = preferences.getString(PREF_QIANDAO, null);
       // }
        return lastModifiedTime;
    }
    
    /**
     * 设置客户编号
     *
     * @param custId
     */
    public void setCustID(String custId) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(MyApplication.this);
        Editor editor = preferences.edit();
        editor.putString(PREF_CUSTID, custId).commit();
//        if (editor.putString(PREF_CUSTID, custId).commit()) {
//            this.custId = custId;
//        }
    }

    /**
     * 获取客户编号
     *
     * @return
     */
    public String getCustID() {
//        if (TextUtils.isEmpty(custId)) {
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(MyApplication.this);
//            custId = preferences.getString(PREF_CUSTID, null);
//        }
        return preferences.getString(PREF_CUSTID, null);
    }

    /**
     * 获取头像url
     *
     * @return
     */
    public String getAvatarUrl() {
        if (TextUtils.isEmpty(avatarUrl)) {
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(MyApplication.this);
            avatarUrl = preferences.getString(PREF_AVATAR_URL, null);
        }

        return avatarUrl;
    }

    /**
     * 设置头像url
     *
     * @param avatarUrl
     */
    public void setAvatarUrl(String avatarUrl) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(MyApplication.this);
        Editor editor = preferences.edit();
//		editor.putString(PREF_PORTRAIT_URL, avatarUrl).commit();
        if (editor.putString(PREF_AVATAR_URL, avatarUrl).commit()) {
            this.avatarUrl = avatarUrl;
        }
    }

    /**
     * 设置user_name
     *
     * @param custName
     */
    public void setCustName(String custName) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(MyApplication.this);
        Editor editor = preferences.edit();
		editor.putString(PREF_USER_NAME, custName).commit();
      /*  if (editor.putString(PREF_USER_NAME, custName).commit()) {
            this.custName = custName;
        }*/
    }

    /**
     * 获取user_name
     *
     * @return
     */
    public String getCustName() {
       // if (TextUtils.isEmpty(custName)) {
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(MyApplication.this);
            custName = preferences.getString(PREF_USER_NAME, null);
       // }
        return custName;
    }

    /**
     * 获取用户生日
     *
     * @return
     */
    public String getUserBirthday() {
        //if (TextUtils.isEmpty(user_birthday)) {
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(MyApplication.this);
            user_birthday = preferences.getString(PREF_USER_BIRTHDAY, null);
        //}
        return user_birthday;
    }

    /**
     * 设置用户生日
     *
     * @param user_birthday
     */
    public void setUserBirthday(String user_birthday) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(MyApplication.this);
        Editor editor = preferences.edit();
	    editor.putString(PREF_USER_BIRTHDAY, user_birthday).commit();
  /*      if (editor.putString(PREF_USER_BIRTHDAY, user_birthday).commit()) {
            this.user_birthday = user_birthday;
        }*/
    }

    /**
     * 设置密码
     *
     * @param password
     */
    public void setPassword(String password) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(MyApplication.this);
        Editor editor = preferences.edit();
		editor.putString(PREF_PASSWORD, password).commit();
       /* if (editor.putString(PREF_PASSWORD, password).commit()) {
            this.password = password;
        }*/
    }

    /**
     * 获取密码
     *
     * @return
     */
    public String getPassword() {
      //  if (TextUtils.isEmpty(password)) {
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(MyApplication.this);
            password = preferences.getString(PREF_PASSWORD, null);
        //}
        return password;
    }

    /**
     * 设置地址
     *
     * @param address
     */
    public void setAddress(String address) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(MyApplication.this);
        Editor editor = preferences.edit();
		editor.putString(PREF_ADDRESS, address).commit();
       /* if (editor.putString(PREF_ADDRESS, address).commit()) {
            this.address = address;
        }*/
    }

    /**
     * 获取地址
     */
    public String getAddress() {
       // if (TextUtils.isEmpty(address)) {
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(MyApplication.this);
            address = preferences.getString(PREF_ADDRESS, "");
      //  }
        return address;
    }

    /**
     * 设置手机
     *
     * @param custPhone
     */
    public void setCustPhone(String custPhone) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(MyApplication.this);
        Editor editor = preferences.edit();
		editor.putString(PREF_PHONE, custPhone).commit();
      /*  if (editor.putString(PREF_PHONE, custPhone).commit()) {
            this.custPhone = custPhone;
        }*/
    }

    /**
     * 获取手机
     *
     * @return
     */
    public String getCustPhone() {
       /* if (TextUtils.isEmpty(custPhone)) {*/
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(MyApplication.this);
            custPhone = preferences.getString(PREF_PHONE, "");
       // }
        return custPhone;
    }
    public boolean isLogin(){
    	if (TextUtils.isEmpty(getCustID())||getCustID().equals("null")) {
			return false;
		}
    	return true;
    }

    /**
     * 退出登录
     */
    public void reSet() {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(MyApplication.this);
        Editor edit = preferences.edit();
        edit.clear();
        edit.commit();
    }
}
