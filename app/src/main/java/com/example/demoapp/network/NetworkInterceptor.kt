package com.example.demoapp.network

import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val addQueryParameter = originalRequest.url.newBuilder()
            .build()
        val modifiedRequest = originalRequest.newBuilder()
            .url(addQueryParameter)
            .build()
        return chain.proceed(modifiedRequest)
    }
}