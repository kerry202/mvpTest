package com.smartmqtt.network.http.convert;


import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.StringReader;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

final class AesGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    AesGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String bufferedJson = bufferedSource.readUtf8();
        bufferedSource.close();
        String resultContent = bufferedJson;
        Log.d("okhttp", resultContent);
        JsonReader jsonReader = gson.newJsonReader(new StringReader(resultContent));
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}
