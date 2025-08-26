package com.konkuk.summerhackathon.presentation.navigation

sealed class Route(
    val route: String
) {
    data object Login : Route("login")

    data object Schedule: Route(route = "schedule")

    data object ClubLookUp: Route(route = "club_lookup")

    data object Match: Route(route = "match")

    data object Proposal: Route(route = "proposal")

    data object Settings: Route(route = "settings")
}