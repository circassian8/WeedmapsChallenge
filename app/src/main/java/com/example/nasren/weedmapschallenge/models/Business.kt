package com.example.nasren.weedmapschallenge.models

import androidx.recyclerview.widget.DiffUtil

data class Business(
    val alias: String,
    val categories: List<Category>,
    val coordinates: Coordinates,
    val distance: Double,
    val id: String,
    val image_url: String,
    val is_closed: Boolean,
    val location: Location,
    val name: String,
    val phone: String,
    val price: String,
    val rating: Double,
    val review_count: Int,
    val transactions: List<String>,
    val url: String,
    var topReview: String?) {

    override fun equals(other: Any?): Boolean {
        if (other === this)
            return true

        val business = other as Business?
        return business?.id == this.id
    }

    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Business> = object : DiffUtil.ItemCallback<Business>() {
            override fun areItemsTheSame(oldItem: Business, newItem: Business): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Business, newItem: Business): Boolean {
                return oldItem == newItem
            }
        }
    }
}