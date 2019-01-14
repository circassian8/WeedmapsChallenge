package com.example.nasren.weedmapschallenge.api

import com.example.nasren.weedmapschallenge.api.responses.BusinessReviewsResponseBody
import com.example.nasren.weedmapschallenge.api.responses.BusinessSearchResponseBody
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface YelpApiInterface {

    @GET("v3/businesses/search")
    fun searchBusinesses(@Query("term") term: String,
                         @Query("latitude") latitude: Double,
                         @Query("longitude") longitude: Double,
                         @Query("offset") offset: Long): Call<BusinessSearchResponseBody>

    @GET("v3/businesses/{id}/reviews")
    fun getReviews(@Path("id") id: String): Call<BusinessReviewsResponseBody>

    companion object {
        fun create(): YelpApiInterface {
            val okHttpClient: OkHttpClient =
                OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS).addInterceptor(AuthInterceptor()).build()

            val retrofit =
                Retrofit.Builder().baseUrl("""https://api.yelp.com/""")
                    .client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()

            return retrofit.create(YelpApiInterface::class.java)
        }
    }
}