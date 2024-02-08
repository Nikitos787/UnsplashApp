package com.example.composeunsplashapp.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.composeunsplashapp.navigation.Screen
import com.example.composeunsplashapp.screens.common.ListContent

@OptIn(ExperimentalPagingApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by searchViewModel.searchQuery
    var active by remember {
        mutableStateOf(false)
    }
    val searchImage = searchViewModel.searchImages.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            SearchBar(
                query = searchQuery,
                onQueryChange = {
                    searchViewModel.updateSearchQuery(query = it)
                },
                onSearch = {
                    searchViewModel.searchImage(query = it)
                    active = false
                },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text(text = "Search") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search icon"
                    )
                },
                trailingIcon = {
                    if (active) {
                        Icon(
                            modifier = Modifier.clickable {
                                if (searchQuery.isNotEmpty()) {
                                    searchViewModel.updateSearchQuery(query = "")
                                } else {
                                    active = false
                                    navController.navigate(Screen.Home.route)
                                }
                            }, imageVector = Icons.Default.Close,
                            contentDescription = "close icon"
                        )
                    }
                }

            ) {}
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {
            ListContent(items = searchImage)
        }
    }

}