package com.example.nasren.weedmapschallenge.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nasren.weedmapschallenge.WeedmapsChallengeApplication
import com.example.nasren.weedmapschallenge.api.responses.BusinessReviewsResponseBody
import com.example.nasren.weedmapschallenge.models.Review
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewsRepository {

    private val yelpApiInterface by lazy {
        WeedmapsChallengeApplication.yelpApiInterface
    }

    fun getTopBusinessReview(businessId: String) : LiveData<Review> {
        val review = MutableLiveData<Review>()

        yelpApiInterface.getReviews(businessId).enqueue(object: Callback<BusinessReviewsResponseBody> {
            override fun onResponse(call: Call<BusinessReviewsResponseBody>, response: Response<BusinessReviewsResponseBody>) {
                if (!response.isSuccessful) {
                    return
                }

                review.value = response.body()?.reviews?.firstOrNull()
            }

            override fun onFailure(call: Call<BusinessReviewsResponseBody>, t: Throwable) {

            }
        })
        return review
    }
}