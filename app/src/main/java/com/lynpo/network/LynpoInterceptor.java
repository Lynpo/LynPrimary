package com.lynpo.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by fujw on 2016/11/14.
 *
 * 日志拦截器
 */

class LynpoInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
                .intercept(chain);
    }
}
