package com.smartmqtt.network.http;

import java.lang.reflect.Field;

import okhttp3.HttpUrl;


public class HttpUrlHelper {

    public static final Field hostField;

    private final HttpUrl httpUrl;

    public HttpUrlHelper(HttpUrl httpUrl) {
        this.httpUrl = httpUrl;
    }

    static {
        Field field = null;
        try {
            field = HttpUrl.class.getDeclaredField("host");
            field.setAccessible(true);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        hostField = field;
    }

    public void setHostUrl(String hostUrl){
        try {
            hostField.set(httpUrl,hostUrl);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public HttpUrl getHttpUrl() {
        return httpUrl;
    }
}
