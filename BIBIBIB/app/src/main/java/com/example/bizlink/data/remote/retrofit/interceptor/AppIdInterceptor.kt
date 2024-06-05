package com.example.bizlink.data.remote.retrofit.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AppIdInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url.newBuilder()
            .addQueryParameter("appid", "")
            .build()

        val requestBuilder = chain.request().newBuilder().url(newUrl)

        return chain.proceed(requestBuilder.build())
    }
}