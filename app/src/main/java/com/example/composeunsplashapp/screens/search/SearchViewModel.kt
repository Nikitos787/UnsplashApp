package com.example.composeunsplashapp.screens.search

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.composeunsplashapp.data.repository.Repository
import com.example.composeunsplashapp.model.UnsplashImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedImages = MutableStateFlow<PagingData<UnsplashImage>>(PagingData.empty())
    val searchedImages = _searchedImages

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchImage(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchImages(query = query)
                .stateIn(viewModelScope)
                .cachedIn(viewModelScope)
                .collect {
                    _searchedImages.value = it
                }
        }
        Log.d("SearchViewModel", searchedImages.value.toString())
        Log.d("SearchViewModel", query)
    }
}
