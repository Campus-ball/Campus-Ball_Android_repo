package com.konkuk.summerhackathon.presentation.schedule.screen

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.konkuk.summerhackathon.core.component.CampusBallTopBar
import com.konkuk.summerhackathon.presentation.schedule.component.ScheduleClubCard
import com.konkuk.summerhackathon.ui.theme.defaultCampusBallColors
import com.konkuk.summerhackathon.ui.theme.defaultCampusBallTypography

//
@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    clubName: String = "KONKUK FC"
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
            ) {
                Text(
                    text = "일정 추가하기",
                    color = defaultCampusBallColors.white,
                    style = defaultCampusBallTypography.B_11,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(Modifier.height(24.dp))


            // TODO: 달력 구현 후 바꿔야 함
            Spacer(
                Modifier
                    .size(306.dp, 294.dp)
                    .background(defaultCampusBallColors.skyblue)
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
                    .padding(horizontal = 24.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                ScheduleClubCard(isRandomMatching = true)
                ScheduleClubCard()
                ScheduleClubCard()
                ScheduleClubCard(isRandomMatching = true)
                ScheduleClubCard(isRandomMatching = true)
                ScheduleClubCard(isRandomMatching = true)
                ScheduleClubCard(isRandomMatching = true)
                Spacer(
                    modifier = Modifier.size(15.dp),
                )
            }

        }
    }
}

@Preview
@Composable
private fun ScheduleScreenPreview() {
    ScheduleScreen()
}