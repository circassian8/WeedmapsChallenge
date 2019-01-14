package com.example.nasren.weedmapschallenge.datasources

import androidx.paging.PageKeyedDataSource
import com.example.nasren.weedmapschallenge.models.Business
import androidx.lifecycle.MutableLiveData
import com.example.nasren.weedmapschallenge.WeedmapsChallengeApplication
import com.example.nasren.weedmapschallenge.api.InitialLoadState
import com.example.nasren.weedmapschallenge.api.responses.BusinessSearchResponseBody
import com.example.nasren.weedmapschallenge.api.responses.ErrorResponse
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FeedDataSource(val term: String, val latitude: Double, val longitude: Double) : PageKeyedDataSource<Long, Business>() {

    private val yelpApiInterface by lazy {
        WeedmapsChallengeApplication.yelpApiInterface
    }

    val initialLoadStatus = MutableLiveData<InitialLoadState>()

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Business>) {
        initialLoadStatus.postValue(InitialLoadState.Loading)

        yelpApiInterface.searchBusinesses(term, latitude, longitude, 0).enqueue(object: Callback<BusinessSearchResponseBody> {
            override fun onResponse(call: Call<BusinessSearchResponseBody>, response: Response<BusinessSearchResponseBody>) {
                if (!response.isSuccessful) {
                    val error = response.errorBody()?.string()?.trim()
                    val jError = GsonBuilder().create().fromJson(error, ErrorResponse::class.java)
                    initialLoadStatus.postValue(InitialLoadState.Failed(jError.error.description))
                    return
                }

                response.body()?.businesses?.let { businesses ->
                    callback.onResult(businesses, null, 20)
                }
                initialLoadStatus.postValue(InitialLoadState.Success)
            }

            override fun onFailure(call: Call<BusinessSearchResponseBody>, t: Throwable) {
                initialLoadStatus.postValue(InitialLoadState.Failed(t.message))
            }
        })
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Business>) {

        yelpApiInterface.searchBusinesses(term, latitude, longitude, params.key).enqueue(object: Callback<BusinessSearchResponseBody> {
            override fun onResponse(call: Call<BusinessSearchResponseBody>, response: Response<BusinessSearchResponseBody>) {
                if (!response.isSuccessful) {
                    return
                }

                val nextKey = if (response.body()?.businesses?.size ?: 0 < 20) null else params.key + 20
                response.body()?.businesses?.let { businesses ->
                    callback.onResult(businesses, nextKey)
                }
                initialLoadStatus.postValue(InitialLoadState.Success)
            }

            override fun onFailure(call: Call<BusinessSearchResponseBody>, t: Throwable) {
            }
        })
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Business>) {

    }

}