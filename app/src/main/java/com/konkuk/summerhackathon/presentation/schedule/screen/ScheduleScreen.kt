package com.konkuk.summerhackathon.presentation.schedule.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.konkuk.summerhackathon.R
import com.konkuk.summerhackathon.core.component.CampusBallTopBar
import com.konkuk.summerhackathon.core.util.noRippleClickable
import com.konkuk.summerhackathon.presentation.navigation.Route
import com.konkuk.summerhackathon.presentation.schedule.component.DateInputField
import com.konkuk.summerhackathon.presentation.schedule.component.ScheduleCalendar
import com.konkuk.summerhackathon.presentation.schedule.component.ScheduleClubCard
import com.konkuk.summerhackathon.presentation.schedule.component.TimeInputField
import com.konkuk.summerhackathon.ui.theme.defaultCampusBallColors
import com.konkuk.summerhackathon.ui.theme.defaultCampusBallTypography
import java.util.Calendar


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    clubName: String = "KONKUK FC",
    navController: NavHostController
) {
    val screenScrollState = rememberScrollState()
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
        CampusBallTopBar()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 52.dp)
            .verticalScroll(screenScrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(20.dp))

        Text(
            text = clubName,
            color = defaultCampusBallColors.likeblack,
            style = defaultCampusBallTypography.SB_24
        )
        Spacer(Modifier.height(12.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 70.dp)
                .height(38.7.dp)
                .background(
                    color = Color(0xFF007BFF),
                    shape = RoundedCornerShape(size = 100.dp)
                )
                .clip(RoundedCornerShape(size = 100.dp))
                .clickable {
                    navController.navigate(Route.ScheduleAvailable.route)
                }
        ) {
            Text(
                text = "일정 추가하기",
                color = defaultCampusBallColors.white,
                style = defaultCampusBallTypography.B_11,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(Modifier.height(24.dp))

        ScheduleCalendar(
            modifier = Modifier
                .padding(horizontal = 34.dp)
                .height(348.dp)
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "경기 성사 목록",
            color = Color(0xff292929),
            style = defaultCampusBallTypography.SB_16.copy(fontSize = 18.sp),
        )
        Spacer(Modifier.height(10.dp))


        Column(
            Modifier
                .padding(horizontal = 24.dp),
//                .heightIn(max = 500.dp),
//                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            ScheduleClubCard(isRandomMatching = true, onClickCard = {
                navController.navigate(Route.ScheduleDetail.route)
            })
            ScheduleClubCard(onClickCard = {
                navController.navigate(Route.ScheduleDetail.route)
            })
            ScheduleClubCard(onClickCard = {
                navController.navigate(Route.ScheduleDetail.route)
            })
            ScheduleClubCard(isRandomMatching = true, onClickCard = {
                navController.navigate(Route.ScheduleDetail.route)
            })
            ScheduleClubCard(isRandomMatching = true, onClickCard = {
                navController.navigate(Route.ScheduleDetail.route)
            })
            ScheduleClubCard(isRandomMatching = true, onClickCard = {
                navController.navigate(Route.ScheduleDetail.route)
            })
            ScheduleClubCard(isRandomMatching = true, onClickCard = {
                navController.navigate(Route.ScheduleDetail.route)
            })
            Spacer(
                modifier = Modifier.size(15.dp),
            )
        }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun ScheduleScreenPreview() {
    val navController = rememberNavController()
    ScheduleScreen(navController = navController)
}