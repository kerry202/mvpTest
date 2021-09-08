package com.smartmqtt.testmodel

import com.smartmqtt.network.base.BaseInfo
import com.smartmqtt.network.http.OkHttpUtils
import io.reactivex.rxjava3.core.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST


interface TestService {

    @POST("/xxx/xxx")
    fun send(@Body body: RequestBody): Observable<BaseInfo<Info>>

    companion object {
        fun create(): TestService {
            return OkHttpUtils.getRetrofit().create(TestService::class.java)
        }
    }
}