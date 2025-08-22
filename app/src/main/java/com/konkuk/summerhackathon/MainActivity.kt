package com.konkuk.summerhackathon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.rememberNavController
import com.konkuk.summerhackathon.core.util.noRippleClickable
import com.konkuk.summerhackathon.presentation.navigation.BottomNavItem
import com.konkuk.summerhackathon.presentation.navigation.MainNavGraph
import com.konkuk.summerhackathon.presentation.navigation.Route
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SummerHackathonTheme {
                val navController = rememberNavController()
                var selectedRoute by remember { mutableStateOf(Route.Match.route) }

                val bottomNavItems = listOf(
                    BottomNavItem("일정", Route.Schedule.route, R.drawable.ic_home),
                    BottomNavItem("동아리 조회", Route.ClubLookUp.route, R.drawable.ic_home),
                    BottomNavItem("매칭", Route.Match.route, R.drawable.ic_home),
                    BottomNavItem("제안 확인", Route.Proposal.route, R.drawable.ic_home),
                    BottomNavItem("설정", Route.Settings.route, R.drawable.ic_home)
                )

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            bottomNavItems.forEach { item ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .noRippleClickable {
                                            selectedRoute = item.route
                                            navController.navigate(item.route) {
                                                launchSingleTop = true
                                                restoreState = true
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            painter = painterResource(id = item.icon),
                                            contentDescription = item.label
                                        )
                                        Text(text = item.label)
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
            }
        }
    }
}