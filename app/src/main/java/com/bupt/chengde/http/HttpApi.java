package com.bupt.chengde.http;


import android.os.AsyncTask;
import android.os.Build;

import com.bupt.chengde.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by joycezhao on 16/10/24.
 */

public class HttpApi {
    public static final String HOST="";
    private static volatile HttpApi INSTANCE = null;
    private final Gson gson = new Gson();
    private Retrofit retrofit;
    //private Builder builder;
    private  HttpApi (){
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(5, TimeUnit.SECONDS);
        client.writeTimeout(3, TimeUnit.MINUTES);
        client.readTimeout(3, TimeUnit.MINUTES);
        if (BuildConfig.DEBUG){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.interceptors().add(interceptor);
        }
        retrofit = new Retrofit.Builder().client(client.build())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HOST)
                .build();
    }
    public static HttpApi create() {
        if (INSTANCE == null)
            synchronized (HttpApi.class) {
                INSTANCE = new HttpApi();
            }
        return INSTANCE;
    }
}
