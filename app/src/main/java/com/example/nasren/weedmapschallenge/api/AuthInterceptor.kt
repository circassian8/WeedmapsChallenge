package com.example.nasren.weedmapschallenge.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()

        request = request.newBuilder().addHeader("Authorization", " Bearer $YELP_API_KEY").build()

        return chain.proceed(request)
    }
}