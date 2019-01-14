package com.example.nasren.weedmapschallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nasren.weedmapschallenge.repositories.ReviewsRepository
import com.jraska.livedata.TestObserver
import org.junit.Test
import org.junit.Rule
import java.util.concurrent.TimeUnit

class TopReviewUnitTests {

    @get:Rule
    var testRule = InstantTaskExecutorRule()

    @Test
    fun getTopBusinessReviewReturnsReviewWithNonEmptyText() {
        val reviewsRepository = ReviewsRepository()

        val liveData = reviewsRepository.getTopBusinessReview("pH7ei4vQg4AD1lMlMPiUYw")

        TestObserver.test(liveData)
            .awaitValue()
            .assertHasValue()
            .assertValue{ review -> review.text.isNotEmpty() }
    }

    @Test
    fun onFailureLiveDataObservesNoValue() {
        val reviewsRepository = ReviewsRepository()

        // the businessId is invalid
        val liveData = reviewsRepository.getTopBusinessReview("pH7ei4vQg6AD1lMlMPiUY333")

        TestObserver.test(liveData)
            .awaitValue(500, TimeUnit.MILLISECONDS)
            .assertNoValue()
    }
}
