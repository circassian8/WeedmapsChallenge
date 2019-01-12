package com.example.nasren.weedmapschallenge.api

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class YelpApiInterface {

    companion object {
        fun create(): YelpApiInterface {
            val okHttpClient: OkHttpClient =
                OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS).build()

            val retrofit =
                Retrofit.Builder().baseUrl("""http://ec2-13-57-244-151.us-west-1.compute.amazonaws.com:3000""")
                    .client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()

            return retrofit.create(YelpApiInterface::class.java)
        }
    }
}