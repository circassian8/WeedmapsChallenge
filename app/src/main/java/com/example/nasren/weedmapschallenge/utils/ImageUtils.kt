package com.example.nasren.weedmapschallenge.utils

import android.widget.ImageView
import com.example.nasren.weedmapschallenge.R
import com.squareup.picasso.Picasso

fun ImageView.setImage(url: String) {
    if (url.isEmpty()) {
        Picasso.get()
            .load(R.drawable.restaurant)
            .into(this)
        return
    }

    Picasso.get()
        .load(url)
        .error(R.drawable.restaurant)
        .into(this)
}