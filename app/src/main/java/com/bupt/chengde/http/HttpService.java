package com.bupt.chengde.http;

import com.google.gson.JsonElement;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

/**
 * Created by joycezhao on 16/10/24.
 */

public interface HttpService {
    /**
     * 下载文件，使用完整链接
     */
    @GET
    Call<ResponseBody> loadFileWithFull(@Url String url);

    /**
     * 请求网络数据
     */
    @FormUrlEncoded
    @POST
    Call<JsonElement> loadData(@Url String url, @FieldMap Map<String, String> map);

    @GET
    Call<JsonElement> getData(@Url String url);

    @Multipart
    @POST
    Call<JsonElement> upImage(@Url String url, @PartMap Map<String, RequestBody> params);
}
