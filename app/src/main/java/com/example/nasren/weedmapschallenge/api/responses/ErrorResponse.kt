package com.example.nasren.weedmapschallenge.api.responses

data class ErrorResponse(val error: Error)

data class Error(val code: String, val description: String)