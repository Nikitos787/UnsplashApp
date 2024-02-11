package com.example.composeunsplashapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.composeunsplashapp.data.remote.UnsplashApi
import com.example.composeunsplashapp.model.UnsplashImage
import com.example.composeunsplashapp.util.Constants.ITEMS_PER_PAGE

class SearchPagingSource(
    private val unsplashApi: UnsplashApi,
    private val query: String
) : PagingSource<Int, UnsplashImage>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashImage> {
        return try {
            val currentPage = params.key ?: 1
            val response = unsplashApi.searchImages(
                page = currentPage,
                query = query,
                perPage = ITEMS_PER_PAGE
            )
            val endOfPaginationReached = response.images.isEmpty()
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1
            if (response.images.isNotEmpty()) {
                LoadResult.Page(
                    data = response.images,
                    prevKey = prevPage,
                    nextKey = nextPage
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashImage>): Int? {
        return state.anchorPosition
    }
}
