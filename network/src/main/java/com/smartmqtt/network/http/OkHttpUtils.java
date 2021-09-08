package com.smartmqtt.network.http;


import com.smartmqtt.network.http.convert.ConverterAesJsonFactory;
import com.smartmqtt.network.http.interceptor.CommonParamsInterceptor;
import com.smartmqtt.network.http.interceptor.HttpInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

import static okhttp3.internal.platform.Platform.WARN;

public class OkHttpUtils {
    private static OkHttpClient okHttpClient;
    private static Retrofit mRetrofit;

    public static void clearnRetrofit() {
        mRetrofit = null;
    }

    public static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl("http://text.com/")
                    .addConverterFactory(ConverterAesJsonFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.createSynchronous())
                    .client(getOkHttpClient())
                    .build();
        }
        return mRetrofit;
    }

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            HttpInterceptor.Logger DEFAULT = message -> Platform.get().log(WARN, message, null);
            HttpInterceptor loggingInterceptor = new HttpInterceptor(DEFAULT);
            loggingInterceptor.setLevel(HttpInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(15 * 1000, TimeUnit.MILLISECONDS)
                    .readTimeout(15 * 1000, TimeUnit.MILLISECONDS)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(new CommonParamsInterceptor())
                    .build();
        }
        return okHttpClient;
    }

}
