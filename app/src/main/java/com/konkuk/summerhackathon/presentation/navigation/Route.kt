package com.konkuk.summerhackathon.presentation.navigation

sealed class Route(
    val route: String
) {
    data object Login : Route("login")

    data object SignUp : Route("sign_up")

    data object Schedule : Route(route = "schedule")
    data object ScheduleDetail : Route(route = "schedule_detail")
    data object ScheduleAvailable : Route(route = "schedule_available")

    data object CollegeLookUp : Route(route = "college_lookup")
    data object ClubLookUp : Route("club_lookup/{department}/{departmentId}") {
        fun path(departmentId: Int, department: String) = "club_lookup/$department/$departmentId"
    }

    data object ComponentLookUp : Route(route = "component_lookup")    // 동아리 클릭 시 일정 확인

    data object Match : Route(route = "match")
    data object MatchDetail : Route(route = "match_detail")

    data object Proposal : Route(route = "proposal")
    data object ProposalDetail : Route(route = "proposal_detail")

    data object Settings : Route(route = "settings")
}