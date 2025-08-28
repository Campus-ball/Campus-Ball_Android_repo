package com.konkuk.summerhackathon.presentation.match.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.konkuk.summerhackathon.R
import com.konkuk.summerhackathon.core.component.CampusBallTopBar
import com.konkuk.summerhackathon.presentation.match.component.LoadingModal
import com.konkuk.summerhackathon.presentation.navigation.Route
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val slogans = listOf(
    "친선 매치로 더 즐겁게, 더 뜨겁게!",
    "오늘도 한 판 볼을 팀, 찾으셨나요?",
    "경기 준비 완료! 이제 상대만 찾으세요.",
    "친선 매치로 더 즐겁게, 더 뜨겁게!",
    "뛰고 싶을 땐, 바로 매칭!",
    "일정 맞춰서 바로 경기 매칭!",
    "동아리 축구, 이제 매칭도 스마트하게."
)

@Composable
fun MatchScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val randomSlogan = remember { slogans.random() }

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
            )
            .verticalScroll(rememberScrollState())
        ,
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CampusBallTopBar()
        }

        Image(
            painter = painterResource(R.drawable.img_match_logo),
            contentDescription = null,
            modifier = Modifier.size(400.dp).padding(top = 104.dp)
        )

        Box(
            modifier = Modifier
                .padding(top = 400.dp, start = 30.dp, end = 30.dp)
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colors.white)
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Spacer(modifier = Modifier.height(47.dp))
                Text(
                    text = "Welcome to",
                    color = colors.black,
                    style = typography.M_18.copy(fontSize = 38.sp)
                )
                Text(
                    text = "CampusBall",
                    color = colors.indigo,
                    style = typography.B_24.copy(fontSize = 40.sp)
                )
                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    text = randomSlogan,
                    color = colors.gray,
                    style = typography.L_10.copy(fontSize = 18.sp)
                )

                Spacer(modifier = Modifier.height(55.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(90.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp)
                        .background(colors.skyblue, shape = RoundedCornerShape(100.dp))
                        .clickable {
                            navController.navigate(Route.MatchDetail.route)
                        }
                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Text(
                            text = "동아리 친선전",
                            color = colors.white,
                            style = typography.B_17
                        )
                        Text(
                            text = "신청하기",
                            color = colors.white,
                            style = typography.B_17
                        )
                    }
                }

            }
        }
    }

}