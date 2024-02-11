package com.example.composeunsplashapp.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.composeunsplashapp.data.repository.Repository
import com.example.composeunsplashapp.model.UnsplashImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private var _allImages = MutableStateFlow<PagingData<UnsplashImage>>(PagingData.empty())
    val allImages = _allImages

    fun getImages() {
        viewModelScope.launch {
            repository.getAllImages()
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())
                .collect {
                _allImages.value = it
            }
        }
    }
}
