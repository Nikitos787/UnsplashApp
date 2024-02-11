package com.example.composeunsplashapp.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.composeunsplashapp.data.local.UnsplashDatabase
import com.example.composeunsplashapp.data.paging.SearchPagingSource
import com.example.composeunsplashapp.data.paging.UnsplashRemoteMediator
import com.example.composeunsplashapp.data.remote.UnsplashApi
import com.example.composeunsplashapp.model.UnsplashImage
import com.example.composeunsplashapp.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val unsplashDatabase: UnsplashDatabase
) {

    fun getAllImages(): Flow<PagingData<UnsplashImage>> {
        val pagingSourceFactory = { unsplashDatabase.unsplashImageDao().getAllImages() }
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = UnsplashRemoteMediator(
                unsplashApi = unsplashApi,
                unsplashDatabase = unsplashDatabase
            )
        ).flow
    }

    fun searchImages(query: String): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchPagingSource(unsplashApi = unsplashApi, query = query)
            }
        ).flow
    }

    fun getImage(id: String): Flow<UnsplashImage> {
        return flow {
            val image = unsplashApi.getImage(id)
            Log.d("Repository", image.toString())
        }
    }
}
