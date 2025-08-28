package com.konkuk.summerhackathon.presentation.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.konkuk.summerhackathon.presentation.auth.screen.LoginScreen
import com.konkuk.summerhackathon.presentation.auth.screen.SignUpScreen
import com.konkuk.summerhackathon.presentation.clublookup.screen.ClubLookUpScreen
import com.konkuk.summerhackathon.presentation.clublookup.screen.CollegeLookUpScreen
import com.konkuk.summerhackathon.presentation.clublookup.screen.ComponentLookUpScreen
import com.konkuk.summerhackathon.presentation.match.screen.MatchDetailScreen
import com.konkuk.summerhackathon.presentation.match.screen.MatchScreen
import com.konkuk.summerhackathon.presentation.proposal.screen.ProposalDetailScreen
import com.konkuk.summerhackathon.presentation.proposal.screen.ProposalScreen
import com.konkuk.summerhackathon.presentation.schedule.screen.ScheduleAvailableScreen
import com.konkuk.summerhackathon.presentation.schedule.screen.ScheduleDetailScreen
import com.konkuk.summerhackathon.presentation.schedule.screen.ScheduleScreen
import com.konkuk.summerhackathon.presentation.settings.screen.SettingsScreen
import com.konkuk.summerhackathon.presentation.settings.viewmodel.MyAccountUi

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.Login.route,
    ) {
        composable(route = Route.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Route.Match.route) {
                        popUpTo(Route.Login.route) { inclusive = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onSignUpClick = {
                    navController.navigate(Route.SignUp.route)
                },
                modifier = modifier
            )
        }

        composable(route = Route.SignUp.route) {
            SignUpScreen(
                modifier = modifier,
                navController = navController
            )
        }

        // Schedule
        composable(route = Route.Schedule.route) {
            ScheduleScreen(modifier = modifier, navController = navController)
        }
        composable(route = Route.ScheduleDetail.route) {
            ScheduleDetailScreen(modifier = modifier)
        }
        composable(route = Route.ScheduleAvailable.route) {
            ScheduleAvailableScreen(modifier = modifier, navController = navController)
        }


        // Lookup
        composable(route = Route.CollegeLookUp.route) {
            CollegeLookUpScreen(modifier = modifier, navController = navController, onDepartmentClick = { dept ->
                navController.navigate(Route.ClubLookUp.path(dept.departmentId))
            })
        }


        composable(
            route = Route.ClubLookUp.route,
            arguments = listOf(
                navArgument("departmentId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val departmentId = backStackEntry.arguments?.getInt("departmentId") ?: 0
            ClubLookUpScreen(
                modifier = modifier,
                navController = navController,
                collegeId = departmentId   // 필요하면 넘겨서 사용
            )
        }

        composable(route = Route.ComponentLookUp.route) {
            ComponentLookUpScreen(modifier = modifier, navController = navController)
        }




        composable(route = Route.Match.route) {
            MatchScreen(
                modifier = modifier,
                navController = navController
            )
        }

        composable(route = Route.Proposal.route) {
            ProposalScreen(modifier = modifier, navController = navController)
        }

        composable(route = Route.ProposalDetail.route) {
            ProposalDetailScreen(modifier = modifier)
        }

        composable(
            route = "${Route.ProposalDetail.route}/{clubId}",
            arguments = listOf(navArgument("clubId") { type = NavType.IntType })
        ) { backStackEntry ->
            val clubId = backStackEntry.arguments?.getInt("clubId") ?: -1
            ProposalDetailScreen(modifier = modifier, clubId = clubId)
            Log.d("ProposalDetailScreen requestId", clubId.toString())
        }

        composable(route = Route.Settings.route) {
            val dummyUi = remember {
                MyAccountUi(
                    club = "A 동아리",
                    name = "홍길동",
                    nickname = "배고픈 하마",
                    gender = "남자",
                    contact = "010-4171-2876",
                    kakaoOpenChat = "http://12341234"
                )
            }

            SettingsScreen(
                //ui = dummyUi,
                modifier = modifier,
                navController = navController
            )
        }

        composable(route = Route.MatchDetail.route) {
            MatchDetailScreen(
                modifier = modifier,
                navController = navController
            )
        }
    }
}