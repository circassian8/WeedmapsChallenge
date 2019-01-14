package com.example.nasren.weedmapschallenge.models

data class Review(
    val id: String,
    val rating: Int,
    val text: String,
    val time_created: String,
    val url: String,
    val user: User
)