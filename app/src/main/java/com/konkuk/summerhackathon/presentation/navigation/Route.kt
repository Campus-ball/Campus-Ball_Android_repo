package com.konkuk.summerhackathon.presentation.navigation

sealed class Route(
    val route: String
) {
    data object Home: Route(route = "home")

    data object My: Route(route = "my")
}