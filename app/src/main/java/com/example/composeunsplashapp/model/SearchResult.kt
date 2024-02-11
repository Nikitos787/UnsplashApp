package com.example.composeunsplashapp.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    @SerializedName("results")
    val images: List<UnsplashImage>,
)
