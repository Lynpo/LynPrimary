package com.lynpo.network;

import com.lynpo.model.User;
import com.lynpo.model.UsrMsg;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by fujw on 2017/2/16.
 *
 * com.lynpo network data api.
 */

interface LynpoApiService {

    /**
     * 资讯列表
     *
     * @param params 参数
     */
    @GET("v1/u")
    Call<User> callUsrInfo(
            @QueryMap Map<String, String> params);

    /**
     * 获取文章列表
     */
    @GET("v2/u")
    Observable<User> getUsrInfo(@QueryMap Map<String, String> params);

    /**
     * 提交文章评论列表
     * @param params params
     * @return Call
     */
    @FormUrlEncoded
    @PUT("v2/u/msg")
    Observable<UsrMsg> getMsg(@FieldMap Map<String, String> params);
}
