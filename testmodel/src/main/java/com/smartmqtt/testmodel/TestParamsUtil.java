package com.smartmqtt.testmodel;

import com.smartmqtt.network.http.paramBuilder.ParamsUtils;

import org.json.JSONObject;

import okhttp3.RequestBody;

public class TestParamsUtil {

    public static RequestBody getParams() {
        JSONObject json = new JSONObject();
        return ParamsUtils.getRequestBody(json);
    }

}
