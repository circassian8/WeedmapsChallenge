package com.example.nasren.weedmapschallenge.api.responses

import com.example.nasren.weedmapschallenge.models.Business

data class BusinessSearchResponseBody(val total: Long?,
                                      val businesses: List<Business>?)