package com.konkuk.summerhackathon.presentation.match.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.konkuk.summerhackathon.R
import com.konkuk.summerhackathon.core.component.CampusBallTopBar
import com.konkuk.summerhackathon.core.component.ClubLookUpCard
import com.konkuk.summerhackathon.core.component.DisabledField
import com.konkuk.summerhackathon.core.component.SuccessModal
import com.konkuk.summerhackathon.core.util.noRippleClickable
import com.konkuk.summerhackathon.presentation.match.component.LoadingModal
import com.konkuk.summerhackathon.presentation.match.viewmodel.MatchViewModel
import com.konkuk.summerhackathon.presentation.navigation.Route
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography
import com.konkuk.summerhackathon.ui.theme.defaultCampusBallColors
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MatchDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    vm: MatchViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val ui by vm.ui.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    val error by vm.error.collectAsState()

    var isMatchButtonClicked by remember { mutableStateOf(false) }
    var otherTeamClicked by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { vm.fetchRandomMatch() }

    LaunchedEffect(otherTeamClicked) {
        if (otherTeamClicked) {
            vm.fetchRandomMatch()
            otherTeamClicked = false
        }
    }

    LaunchedEffect(Unit) {
        vm.events.collectLatest { ev ->
            when (ev) {
                is MatchViewModel.Event.RequestSuccess -> {
                    isMatchButtonClicked = true
                }
                is MatchViewModel.Event.Error -> {
                    // 에러 모달/스낵바 표시
                }
                is MatchViewModel.Event.MatchFetched -> Unit
            }
        }
    }

    if (isLoading) LoadingModal()

    if (error != null) {
        Column(
            modifier = modifier.fillMaxSize().zIndex(2f)
        ){
            Box(
                modifier = Modifier
                    .padding(top = 19.dp, start = 21.dp)
            ){
                Image(
                    modifier = Modifier
                        .padding(start = 21.dp, top = 101.dp)
                        .size(25.dp)
                        .align(Alignment.TopStart)
                        .noRippleClickable { navController.popBackStack() },
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "",
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(colors.white)
                .zIndex(1f)
                .padding(horizontal = 20.dp)
        ){
            Image(
                painter = painterResource(R.drawable.img_fail),
                contentDescription = "error image",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "가용 날짜가 맞는 동아리가 없어요..",
                style = typography.M_18,
                color = colors.black
            )
        }
    }

    Column(
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
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CampusBallTopBar()
        Spacer(modifier = Modifier.height(21.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.img_back_arrow),
                contentDescription = "back arrow icon",
                modifier = Modifier
                    .size(14.dp)
                    .align(Alignment.CenterStart)
                    .noRippleClickable { navController.popBackStack() }
            )

            Text(
                text = "Match Found!",
                modifier = Modifier.align(Alignment.Center),
                style = typography.B_24
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        ClubLookUpCard(
            clubName = ui.clubName.ifBlank { "—" },
            collegeAndMajor = ui.departmentName.ifBlank { "—" },
        )

        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(colors.white, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            DisabledField(
                label = "날짜",
                value = ui.startDate.replace("-", "/") + " (월)", // 요일 계산 로직 있으면 교체
                check = false
            )

            Spacer(modifier = Modifier.height(27.dp))
            DisabledField(label = "경기 시간", value = ui.startTime.ifBlank { "—" }, check = false)

            Spacer(modifier = Modifier.height(27.dp))
            DisabledField(
                label = "동아리 소개",
                value = ui.clubDescription.ifBlank { "—" },
                check = true
            )
            Spacer(modifier = Modifier.height(27.dp))
        }

        Spacer(modifier = Modifier.height(88.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .height(53.dp)
                    .clip(shape = RoundedCornerShape(13.dp))
                    .background(colors.black, shape = RoundedCornerShape(13.dp))
                    .clickable {
                        otherTeamClicked = true
                    }
            ) {
                Text(
                    text = "다른 상대 찾기",
                    style = typography.B_17.copy(fontSize = 13.sp),
                    color = colors.white
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .height(53.dp)
                    .clip(shape = RoundedCornerShape(13.dp))
                    .background(colors.skyblue, shape = RoundedCornerShape(13.dp))
                    .clickable { vm.requestMatch() }
            ) {
                Text(
                    text = "제안하기",
                    style = typography.B_17.copy(fontSize = 13.sp),
                    color = colors.white
                )
            }
        }
        Spacer(modifier = Modifier.height(27.dp))
    }

    if (isMatchButtonClicked) {
        SuccessModal(
            value = "매치를 제안하였습니다!",
            value2 = "상대 대표님께 제안이 들어갔어요!",
            buttonValue = "확인",
            onClick = { navController.navigate(Route.Match.route) }
        )
    }
}