package com.smartmqtt.network.http.interceptor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smartmqtt.network.base.BaseInfo;

import java.io.IOException;
import java.nio.charset.Charset;

import androidx.annotation.Nullable;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;


public class CommonParamsInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request.Builder requestBuilder = originalRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer ")
                .method(originalRequest.method(), originalRequest.body());
        Request request = requestBuilder.build();
        Response originalResponse = chain.proceed(request);
        ResponseBody responseBody = originalResponse.body();
        //打印响应结果
        String bodyString = null;
        if (responseBody != null && isParsable(responseBody.contentType())) {
            bodyString = printResult(request, originalResponse);
        }
        return onHttpResultResponse(bodyString, chain, originalResponse);
    }


    public Response onHttpResultResponse(String httpResult, Chain chain, Response response) {
        BaseInfo baseInfo = null;
        if (httpResult.contains("refresh token") && httpResult.contains("newToken") || httpResult.contains("\"code\":1001")) {
            baseInfo = new Gson().fromJson(httpResult, new TypeToken<BaseInfo>() {
            }.getType());
        }

        return response;
    }


    /**
     * 打印响应结果
     */
    @Nullable
    private String printResult(Request request, Response response) {
        try {
            //读取服务器返回的结果
            ResponseBody responseBody = response.newBuilder().build().body();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            //获取content的压缩类型
            String encoding = response
                    .headers()
                    .get("Content-Encoding");

            Buffer clone = buffer.clone();

            //解析response content
            return parseContent(responseBody, encoding, clone);
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    /**
     * 解析服务器响应的内容
     *
     * @param responseBody 返回体
     * @param encoding     数据类型
     * @param clone        数据流
     * @return 返回数据
     */
    private String parseContent(ResponseBody responseBody, String encoding, Buffer clone) {
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        if (charset != null) {
            return clone.readString(charset);
        }
        return "";
    }

    /**
     * 是否可以解析
     *
     * @param mediaType 数据类型
     * @return 是否序列化
     */
    private static boolean isParsable(MediaType mediaType) {
        return isText(mediaType) || isPlain(mediaType)
                || isJson(mediaType) || isForm(mediaType)
                || isHtml(mediaType) || isXml(mediaType);
    }

    private static boolean isText(MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }
        return mediaType.type().equals("text");
    }

    private static boolean isPlain(MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }
        return mediaType.subtype().toLowerCase().contains("plain");
    }

    private static boolean isJson(MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }
        return mediaType.subtype().toLowerCase().contains("json");
    }

    private static boolean isXml(MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }
        return mediaType.subtype().toLowerCase().contains("xml");
    }

    private static boolean isHtml(MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }
        return mediaType.subtype().toLowerCase().contains("html");
    }

    private static boolean isForm(MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }
        return mediaType.subtype().toLowerCase().contains("x-www-form-urlencoded");
    }

    private static String convertCharset(Charset charset) {
        String s = charset.toString();
        int i = s.indexOf("[");
        if (i == -1) {
            return s;
        }
        return s.substring(i + 1, s.length() - 1);
    }

}
