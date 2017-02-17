package com.lynpo.network;

import com.lynpo.BuildConfig;
import com.lynpo.LynpoApplication;
import com.lynpo.model.User;
import com.lynpo.model.UsrMsg;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fujw on 2016/11/11.
 *
 * CMS 接口访问方法管理
 */

public class LynpoHttpMethods {

    private static final boolean LYNPO_DEBUG = LynpoApplication.LYNPO_DEBUG;

    private static final String LYNPO_HEADER_REFER_ONLINE = "https://www.lynpo.com/";
    private static final String LYNPO_HEADER_REFER_OFFLINE = "http://www.lynpo.net/";
    private static final String LYNPO_HEADER_REFER = LYNPO_DEBUG ? LYNPO_HEADER_REFER_OFFLINE : LYNPO_HEADER_REFER_ONLINE;

    private static final String LYNPO_BASE_URL_ONLINE = "https://www.lynpo.com/api/";
    private static final String LYNPO_BASE_URL_OFFLINE = "http://www.lynpo.net/api/";
    private static final String LYNPO_BASE_URL = LYNPO_DEBUG ? LYNPO_BASE_URL_OFFLINE : LYNPO_BASE_URL_ONLINE;

    private static final int DEFAULT_TIMEOUT = 5;

    private LynpoApiService mLynpoService;

    private LynpoHttpMethods() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        builder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder builder = original.newBuilder();
                builder.header("User-Agent", "lynpo_v1");
                builder.addHeader("app-os", android.os.Build.VERSION.RELEASE)
                        .addHeader("app-version", "0.0.1")
                        .addHeader("app-ac", "ac")
                        .addHeader("app-hard-name", android.os.Build.MODEL)
                        .addHeader("app-session-id", "LynpoApplication.SID")
                        .addHeader("app-v-user", "LynpoApplication.SSO_USR_NAME")
                        .addHeader("TOKEN", "LynpoApplication.TOKEN")
                        .addHeader("REFER", LYNPO_HEADER_REFER)
                        .method(original.method(), original.body())
                        .build();
                Request request = builder.build();
                return chain.proceed(request);
            }
        });
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new LynpoInterceptor());
        }

        OkHttpClient client = builder.build();
        mLynpoService = new Retrofit.Builder()
                .baseUrl(LYNPO_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build()
                .create(LynpoApiService.class);
    }

    private static class SingletonHolder {
        private static final LynpoHttpMethods INSTANCE = new LynpoHttpMethods();
    }

    public static LynpoHttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Call<User> callUsrInfo(Callback<User> callback, Map<String, String> params) {
        Call<User> call = mLynpoService.callUsrInfo(params);
        call.enqueue(callback);
        return call;
    }

    public void getUsrInfo(Subscriber<User> subscriber, Map<String, String> params) {
        mLynpoService.getUsrInfo(params)
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
    }

    public void getMsg(Subscriber<UsrMsg> subscriber, Map<String, String> params) {
        mLynpoService.getMsg(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
