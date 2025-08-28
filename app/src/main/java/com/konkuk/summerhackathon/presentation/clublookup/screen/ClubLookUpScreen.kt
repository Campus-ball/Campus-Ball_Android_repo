package com.konkuk.summerhackathon.presentation.clublookup.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.konkuk.summerhackathon.core.component.CampusBallTopBar
import com.konkuk.summerhackathon.core.component.ClubLookUpCard
import com.konkuk.summerhackathon.presentation.clublookup.viewmodel.ClubLookUpViewModel
import com.konkuk.summerhackathon.presentation.navigation.Route
import com.konkuk.summerhackathon.ui.theme.defaultCampusBallColors
import com.konkuk.summerhackathon.ui.theme.defaultCampusBallTypography

// 동아리 선택 화면
@Composable
fun ClubLookUpScreen(
    modifier: Modifier = Modifier,
    collegeId: Int,
    department: String,
    navController: NavHostController,
    viewModel: ClubLookUpViewModel = hiltViewModel()
) {
//    Text(text = "collegeId: $collegeId")

    LaunchedEffect(collegeId) {
        viewModel.searchForClubs(collegeId)
    }

    val events by viewModel.events.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val scrollState = rememberScrollState()

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.0f to Color(0xFFFFFFFF),
                            0.61f to Color(0xFFCBCBCB),
                            1.0f to Color(0xFFF0F1F7)
                        )
                    )
                ),
        ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CampusBallTopBar()

                Spacer(Modifier.height(55.dp))

                Text(
                    text = "동아리 선택",
                    color = defaultCampusBallColors.likeblack,
                    style = defaultCampusBallTypography.B_24
                )
                Spacer(Modifier.height(36.dp))


                /*                Column(
                                    Modifier
                                        .padding(horizontal = 24.dp)
                                        .verticalScroll(scrollState),
                                    verticalArrangement = Arrangement.spacedBy(15.dp),
                                ) {
                                    ClubLookUpCard(onClick = {
                                        navController.navigate(Route.ComponentLookUp.route)
                                    })
                                    ClubLookUpCard()
                                    ClubLookUpCard()
                                    ClubLookUpCard()
                                    ClubLookUpCard()
                                    ClubLookUpCard()
                                    ClubLookUpCard()
                                    Spacer(
                                        modifier = Modifier.size(15.dp),
                                    )
                                }*/

                LazyColumn(
                    Modifier
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(events?.data?.items?.size ?: 0) { index ->
                        val club = events?.data?.items?.get(index)
                        if (club != null) {
                            ClubLookUpCard(
                                clubName = club.clubName,
                                collegeAndMajor = department,
                                onClick = {
                                    navController.navigate(Route.ComponentLookUp.route + "/${club.clubId}")
                                }
                            )
                        }
                    }
                    item {
                        Spacer(
                            modifier = Modifier.size(15.dp),
                        )
                    }
                }
            }
        }
    }
}