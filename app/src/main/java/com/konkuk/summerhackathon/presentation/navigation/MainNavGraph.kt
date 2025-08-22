package com.konkuk.summerhackathon.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.konkuk.summerhackathon.presentation.home.screen.HomeScreen
import com.konkuk.summerhackathon.presentation.my.screen.MyScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.route,
    ){
        composable(route = Route.Home.route) {
            HomeScreen(modifier = modifier)
        }

        composable(route = Route.My.route) {
            MyScreen(modifier = modifier)
        }
    }
}