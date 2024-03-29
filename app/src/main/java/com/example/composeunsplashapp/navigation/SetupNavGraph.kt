package com.example.composeunsplashapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.example.composeunsplashapp.screens.details.DetailsScreen
import com.example.composeunsplashapp.screens.home.HomeScreen
import com.example.composeunsplashapp.screens.search.SearchScreen

@OptIn(ExperimentalPagingApi::class, ExperimentalCoilApi::class)
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route){
            HomeScreen(navController = navController)
        }
        composable(route = Screen.Search.route){
            SearchScreen(navController = navController)
        }
        composable(route = Screen.Details.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ){
            DetailsScreen(navController = navController)
        }
    }
}
