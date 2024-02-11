package com.example.composeunsplashapp.screens.details

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import com.example.composeunsplashapp.screens.common.UnSplashImageItem

@OptIn(ExperimentalPagingApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavHostController,
    detailsImageViewModel: DetailsImageViewModel = hiltViewModel()
) {
    val imageState by detailsImageViewModel.unsplashImage.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        val id = navController.currentBackStackEntry?.arguments?.getString("id")
        Log.d("DetailsScreen", "LaunchedEffect called with id: $id")
        if (!id.isNullOrEmpty()) {
            Log.d("DetailsScreen", "Calling getImage() with id: $id")
            detailsImageViewModel.getImage(id)
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Details") })
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {
            val context = LocalContext.current
            UnSplashImageItem(unsplashImage = imageState, click = {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://unsplash.com/@${imageState.user.username}?utm_source=DemoApp&utm_medium=referral")
                )
                startActivity(context, browserIntent, null)
            })
        }
    }
}