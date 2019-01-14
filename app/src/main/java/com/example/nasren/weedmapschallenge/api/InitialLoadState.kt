package com.example.nasren.weedmapschallenge.api

sealed class InitialLoadState {
    data class Failed(val message: String?) : InitialLoadState()
    object Loading : InitialLoadState()
    object Success : InitialLoadState()
}