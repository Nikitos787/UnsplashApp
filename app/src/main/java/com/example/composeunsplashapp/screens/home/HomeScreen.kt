package com.example.composeunsplashapp.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.example.composeunsplashapp.navigation.Screen
import com.example.composeunsplashapp.screens.common.ListContent

@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val getAllImages = homeViewModel.allImages.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            HomeTopBar(
                onSearchClicked = {
                    navController.navigate(Screen.Search.route)
                }
            )
        }
    ) { innerPadding ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(innerPadding)) {
            ListContent(items = getAllImages) { id ->
                navController.navigate(Screen.Details.passImageId(id))
            }

        }
    }
}