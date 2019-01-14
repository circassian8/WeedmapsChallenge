package com.example.nasren.weedmapschallenge.datasources

import androidx.paging.DataSource
import com.example.nasren.weedmapschallenge.models.Business
import androidx.lifecycle.MutableLiveData

class FeedDataFactory(term: String, latitude: Double, longitude: Double) : DataSource.Factory<Long, Business>() {

    val mutableLiveData = MutableLiveData<FeedDataSource>()
    private var feedDataSource = FeedDataSource(term, latitude, longitude)

    override fun create(): DataSource<Long, Business> {
        mutableLiveData.postValue(feedDataSource)
        return feedDataSource
    }
}