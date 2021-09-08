package com.smartmqtt.network.http.paramBuilder;


import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class ParamsUtils {

    public static RequestBody getRequestBody(JSONObject requestJson) {
        return RequestBody.create(MediaType.parse("application/json"), requestJson.toString().getBytes());
    }

}
