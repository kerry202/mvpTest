package com.smartmqtt.network.http.interceptor;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * description 统一添加header的拦截器
 */
public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request()
                .newBuilder()
                .method("GET", null)
                .addHeader("Accept","application/json")
                .addHeader("Basic","abcdefghi123456789")
                .addHeader("token", "token")
                .build();
        Response response = chain.proceed(request);

        return response;
    }
}