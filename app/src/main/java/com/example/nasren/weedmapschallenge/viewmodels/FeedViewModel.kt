package com.example.nasren.weedmapschallenge.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.nasren.weedmapschallenge.models.Business
import com.example.nasren.weedmapschallenge.models.Review
import androidx.paging.LivePagedListBuilder
import androidx.lifecycle.Transformations
import com.example.nasren.weedmapschallenge.repositories.ReviewsRepository
import com.example.nasren.weedmapschallenge.datasources.FeedDataFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import com.example.nasren.weedmapschallenge.api.InitialLoadState

class FeedViewModel(private var reviewsRepository: ReviewsRepository = ReviewsRepository()) : ViewModel() {

    var businessList: LiveData<PagedList<Business>> = MutableLiveData()
    private var executor: Executor? = null
    var initialLoadStatus: LiveData<InitialLoadState>? = null

    fun search(term: String, latitude: Double, longitude: Double) {
        executor = Executors.newFixedThreadPool(5)
        val feedDataFactory = FeedDataFactory(term, latitude, longitude)

        initialLoadStatus = Transformations.switchMap(feedDataFactory.mutableLiveData) { dataSource ->
            dataSource.initialLoadStatus
        }

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(20).build()

        executor?.let { executor ->
            businessList = LivePagedListBuilder(feedDataFactory, pagedListConfig)
                .setFetchExecutor(executor)
                .build()
        }
    }

    fun fetchTopReview(businessId: String) : LiveData<Review> {
        return reviewsRepository.getTopBusinessReview(businessId)
    }
}