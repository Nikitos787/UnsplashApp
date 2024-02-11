package com.example.composeunsplashapp.screens.details

import android.util.Log
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class DetailsImageViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _id = mutableStateOf("")
    val id = _id

    private val _unsplashImage =
        MutableStateFlow(UnsplashImage("", Urls(""), 0, User("")))
    val unsplashImage = _unsplashImage.asStateFlow()


    fun getImage(id: String) {
        _id.value = id
        Log.e("DetailsImageViewModel", "Now we inside getImage method with id : ${_id.value}")

        viewModelScope.launch(Dispatchers.IO) {
            Log.e("DetailsImageViewModel", "Now we lunch block of method getImage method with id : ${_id.value}")
            try {
                Log.e("DetailsImageViewModel", "Now we lunch block in try catch block of method getImage method with id : ${_id.value}")
                repository.getImage(id)
                    .collectLatest {
                        Log.e("DetailsImageViewModel", "Now we lunch block in try catch block in collect of method getImage method with id : ${_id.value}")

                        _unsplashImage.value = it
                    }
            } catch (e: Exception) {
                // Обработка ошибки, например, вывод сообщения об ошибке в логи или уведомление пользователю
                Log.e("DetailsImageViewModel", "Error fetching image: ${e.message}")
            }
        }
    }
}
