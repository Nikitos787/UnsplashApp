package com.example.composeunsplashapp.screens.details

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.composeunsplashapp.data.repository.Repository
import com.example.composeunsplashapp.model.UnsplashImage
import com.example.composeunsplashapp.model.Urls
import com.example.composeunsplashapp.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class DetailsImageViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val newImageState = savedStateHandle.getStateFlow<String>("id", "")
        .flatMapLatest { id ->
            repository.getImage(id)
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UnsplashImage(
                "",
                Urls(""),
                0,
                User("")
            )
        )

    fun setId(id: String) {
        savedStateHandle["id"] = id
    }
}
