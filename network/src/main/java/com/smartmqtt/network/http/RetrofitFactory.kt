package com.studycloud.tj_basemoudle.http

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitFactory {
    fun getRetrofit(url: String): Retrofit {

        return Retrofit.Builder().addCallAdapterFactory(RxJava3CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).baseUrl(url).build()
    }
}