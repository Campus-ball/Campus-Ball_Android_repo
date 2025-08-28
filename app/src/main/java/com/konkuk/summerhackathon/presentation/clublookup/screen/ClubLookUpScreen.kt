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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.konkuk.summerhackathon.core.component.CampusBallTopBar
import com.konkuk.summerhackathon.presentation.clublookup.component.ClubLookUpCard
import com.konkuk.summerhackathon.presentation.navigation.Route
import com.konkuk.summerhackathon.ui.theme.defaultCampusBallColors
import com.konkuk.summerhackathon.ui.theme.defaultCampusBallTypography

// 동아리 선택 화면
@Composable
fun ClubLookUpScreen(
    modifier: Modifier = Modifier,
    collegeId: Int = 0,
    navController: NavHostController
) {
    val scrollState = rememberScrollState()
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


            Column(
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
            }
        }
    }
}

@Preview
@Composable
private fun LookUpClubScreenPreview() {
    val navController = rememberNavController()
    ClubLookUpScreen(navController = navController)
}