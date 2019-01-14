package com.example.nasren.weedmapschallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nasren.weedmapschallenge.api.InitialLoadState
import com.example.nasren.weedmapschallenge.viewmodels.FeedViewModel
import com.jraska.livedata.TestObserver
import org.junit.Rule
import org.junit.Test

class FeedUnitTests {

    @get:Rule
    var testRule = InstantTaskExecutorRule()

    @Test
    fun getBusinessListReturnsNonEmptyList() {
        val feedViewModel = FeedViewModel()

        feedViewModel.search("mcdonalds", 33.666, -117.7564)

        val businessListLiveData = feedViewModel.businessList

        val initialLoadNetworkState = feedViewModel.initialLoadStatus

        TestObserver.test(businessListLiveData)
            .awaitValue()
            .assertHasValue()
            .assertValue { businessList -> businessList.isNotEmpty() }


        TestObserver.test(initialLoadNetworkState)
            .awaitValue()
            .assertHasValue()
            .assertValue{ networkState -> networkState is InitialLoadState.Loading }
    }

    @Test
    fun getBusinessListReturnsErrorWhenSupplyingWrongLatitude() {
        val feedViewModel = FeedViewModel()

        feedViewModel.search("mcdonalds", 133.666, -117.7564)

        val businessListLiveData = feedViewModel.businessList

        val initialLoadNetworkState = feedViewModel.initialLoadStatus

        TestObserver.test(businessListLiveData)
            .awaitNextValue()
            .assertNoValue()

        TestObserver.test(initialLoadNetworkState)
            .awaitValue()
            .assertHasValue()
            .assertValue{ networkState -> networkState is InitialLoadState.Failed }
    }


}