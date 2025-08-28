package com.konkuk.summerhackathon

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.konkuk.summerhackathon.core.util.noRippleClickable
import com.konkuk.summerhackathon.presentation.navigation.BottomNavItem
import com.konkuk.summerhackathon.presentation.navigation.MainNavGraph
import com.konkuk.summerhackathon.presentation.navigation.Route
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SummerHackathonTheme {
                val navController = rememberNavController()
                var selectedRoute by remember { mutableStateOf(Route.Match.route) }

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val bottomNavItems = listOf(
                    BottomNavItem("일정", Route.Schedule.route, R.drawable.ic_schedule_un),
                    BottomNavItem("동아리 조회", Route.CollegeLookUp.route, R.drawable.ic_clublookup_un),
                    BottomNavItem("매칭", Route.Match.route, R.drawable.ic_match_un),
                    BottomNavItem("제안 확인", Route.Proposal.route, R.drawable.ic_proposal_un),
                    BottomNavItem("설정", Route.Settings.route, R.drawable.ic_settings_un)
                )

                val showBottomBar = when (currentRoute) {
                    Route.Login.route -> false
                    Route.SignUp.route -> false
                    Route.ClubLookUp.route -> false
                    Route.ComponentLookUp.route -> false
                    Route.ProposalDetail.route -> false
                    "${Route.ProposalDetail.route}/{clubId}" -> false
                    else -> true
                }

                val backArrowRoutes = setOf(
                    Route.ScheduleDetail.route,
                    Route.ScheduleAvailable.route,
                    Route.ClubLookUp.route,
                    Route.ComponentLookUp.route,
                    Route.ProposalDetail.route,
                    "${Route.ProposalDetail.route}/{clubId}",
                )


                LaunchedEffect(currentRoute) {
                    if (currentRoute != null && bottomNavItems.any { it.route == currentRoute }) {
                        selectedRoute = currentRoute
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Scaffold(
                        bottomBar = {
                            if (showBottomBar) {
                                NavigationBar(
                                    containerColor = colors.white,
                                    tonalElevation = 0.dp,
                                ) {
                                    bottomNavItems.forEach { item ->
                                        val selected = selectedRoute == item.route

                                        val iconRes = when (item.route) {
                                            Route.Schedule.route -> if (selected) R.drawable.ic_schedule else item.icon
                                            Route.CollegeLookUp.route -> if (selected) R.drawable.ic_clublookup else item.icon
                                            Route.Match.route -> if (selected) R.drawable.ic_match else item.icon
                                            Route.Proposal.route -> if (selected) R.drawable.ic_proposal else item.icon
                                            Route.Settings.route -> if (selected) R.drawable.ic_settings else item.icon
                                            else -> item.icon
                                        }

                                        Box(
                                            modifier = Modifier
                                                .background(colors.white)
                                                .weight(1f)
                                                .noRippleClickable {
                                                    if (!selected) {
                                                        selectedRoute = item.route
                                                        navController.navigate(item.route) {
                                                            launchSingleTop = true
                                                            restoreState = true
                                                            popUpTo(navController.graph.startDestinationId) {
                                                                saveState = true
                                                            }
                                                        }
                                                    }
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Icon(
                                                    modifier = Modifier.size(24.dp),
                                                    painter = painterResource(id = iconRes),
                                                    contentDescription = item.label,
                                                    tint = androidx.compose.ui.graphics.Color.Unspecified
                                                )
                                                Spacer(modifier = Modifier.padding(8.dp))
                                                Text(
                                                    text = item.label,
                                                    color = if (selected) colors.black else colors.lightgray,
                                                    style = typography.M_8.copy(fontSize = 10.sp)
                                                )
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    ) { innerPadding ->
                        MainNavGraph(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                    if (currentRoute in backArrowRoutes) {
                        Image(
                            modifier = Modifier
                                .padding(start = 21.dp, top = 101.dp)
                                .size(14.dp)
                                .align(Alignment.TopStart)
                                .noRippleClickable { navController.popBackStack() },
                            painter = painterResource(id = R.drawable.ic_back_arrow),
                            contentDescription = "",
                        )
                    }
                }
            }
        }
    }
}