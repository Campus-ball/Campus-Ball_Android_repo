package com.konkuk.summerhackathon.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.konkuk.summerhackathon.presentation.clublookup.screen.ClubLookUpScreen
import com.konkuk.summerhackathon.presentation.match.screen.MatchScreen
import com.konkuk.summerhackathon.presentation.proposal.screen.ProposalScreen
import com.konkuk.summerhackathon.presentation.schedule.screen.ScheduleScreen
import com.konkuk.summerhackathon.presentation.settings.screen.SettingsScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.Match.route,
    ){
        composable(route = Route.Schedule.route){
            ScheduleScreen(modifier = modifier)
        }

        composable(route = Route.ClubLookUp.route){
            ClubLookUpScreen(modifier = modifier)
        }

        composable(route = Route.Match.route){
            MatchScreen(modifier = modifier)
        }

        composable(route = Route.Proposal.route){
            ProposalScreen(modifier = modifier)
        }

        composable(route = Route.Settings.route){
            SettingsScreen(modifier = modifier)
        }
    }
}