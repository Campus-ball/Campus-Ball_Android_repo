package com.konkuk.summerhackathon.presentation.navigation

import android.os.Build
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
import com.konkuk.summerhackathon.presentation.clublookup.screen.CollegeLookUpScreen
import com.konkuk.summerhackathon.presentation.clublookup.screen.ClubLookUpScreen
import com.konkuk.summerhackathon.presentation.match.screen.MatchScreen
import com.konkuk.summerhackathon.presentation.proposal.screen.ProposalScreen
import com.konkuk.summerhackathon.presentation.schedule.screen.ScheduleAvailableScreen
import com.konkuk.summerhackathon.presentation.schedule.screen.ScheduleDetailScreen
import com.konkuk.summerhackathon.presentation.schedule.screen.ScheduleScreen
import com.konkuk.summerhackathon.presentation.settings.screen.MyAccountUi
import com.konkuk.summerhackathon.presentation.settings.screen.SettingsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.ScheduleAvailable.route,
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
            CollegeLookUpScreen(modifier = modifier, onUniversityClick = { collegeId ->
                navController.navigate("${Route.ClubLookUp.route}/$collegeId")
            })
        }


 /*       composable(
            route = Route.ClubLookUpWithArg.route,
            arguments = listOf(navArgument("collegeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val collegeId = backStackEntry.arguments?.getInt(Route.CollegeLookUp.route)

            if (collegeId != null) {
                ClubLookUpScreen(collegeId = collegeId)
            }
            ClubLookUpScreen()
        }*/

        composable(route = Route.ClubLookUp.route) {
            ClubLookUpScreen(modifier = modifier)
        }


        composable(route = Route.Match.route) {
            MatchScreen(modifier = modifier)
        }

        composable(route = Route.Proposal.route) {
            ProposalScreen(modifier = modifier)
        }

        composable(route = Route.Settings.route){
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
                ui = dummyUi,
                modifier = modifier,
                navController = navController
            )
        }
    }
}