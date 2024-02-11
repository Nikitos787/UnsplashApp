package com.example.composeunsplashapp.data.remote

import com.example.composeunsplashapp.BuildConfig
import com.example.composeunsplashapp.model.SearchResult
import com.example.composeunsplashapp.model.UnsplashImage
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {
    @Headers("Accept-Version: v1", "Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("/photos")
    suspend fun getAllImages(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<UnsplashImage>

    @Headers("Accept-Version: v1", "Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("/search/photos")
    suspend fun searchImages(
        @Query("page") page : Int,
        @Query("query") query: String,
        @Query("per_page") perPage: Int
    ): SearchResult

    @Headers("Accept-Version: v1", "Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("/photos/{id}")
    suspend fun getImage(
        @Path("id") id: String
    ): UnsplashImage
}
