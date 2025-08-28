package com.konkuk.summerhackathon.presentation.navigation

sealed class Route(
    val route: String
) {
    data object Login : Route("login")

    data object SignUp : Route("sign_up")

    data object Schedule: Route(route = "schedule")
    data object ScheduleDetail: Route(route = "schedule_detail")
    data object ScheduleAvailable: Route(route = "schedule_available")

    data object CollegeLookUp: Route(route = "college_lookup")
    data object ClubLookUp: Route(route = "club_lookup")    // 동아리 선택 화면
    data object ClubLookUpWithArg: Route(route = "$ClubLookUp/{collegeId}")    // 동아리 선택 화면

    data object Match: Route(route = "match")

    data object Proposal: Route(route = "proposal")

    data object Settings: Route(route = "settings")
}