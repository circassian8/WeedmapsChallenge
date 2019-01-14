package com.example.nasren.weedmapschallenge.api.responses

import com.example.nasren.weedmapschallenge.models.Review

data class BusinessReviewsResponseBody(var reviews: List<Review>,
                                       var total: String,
                                       var possible_languages: List<String>)