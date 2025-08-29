package com.konkuk.summerhackathon.presentation.clublookup.screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.konkuk.summerhackathon.R
import com.konkuk.summerhackathon.core.component.CampusBallTopBar
import com.konkuk.summerhackathon.core.component.SuccessModal
import com.konkuk.summerhackathon.core.component.ClubLookUpCard
import com.konkuk.summerhackathon.data.dto.request.MatchSendRequest
import com.konkuk.summerhackathon.presentation.navigation.Route
import com.konkuk.summerhackathon.presentation.proposal.viewmodel.ProposalDetailViewModel
import com.konkuk.summerhackathon.presentation.proposal.viewmodel.ReceivedMatchViewModel
import com.konkuk.summerhackathon.presentation.schedule.component.ScheduleCalendar
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography
import com.konkuk.summerhackathon.ui.theme.defaultCampusBallColors
import com.konkuk.summerhackathon.ui.theme.defaultCampusBallTypography

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ComponentLookUpScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    matchViewModel: ReceivedMatchViewModel = hiltViewModel(),
    viewModel: ProposalDetailViewModel = hiltViewModel(),
    clubId: Int = 0
) {
    val events by viewModel.events.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val opponent = events?.opponent
    val myCalendar = events?.myCalendar?.items ?: emptyList()
    val opponentCalendar = events?.opponentCalendar?.items ?: emptyList()


    val screenScrollState = rememberScrollState()
    var isMatchButtonClicked by remember { mutableStateOf(false) }


    Log.d("ProposalDetailScreen", "Events: $opponentCalendar")
    LaunchedEffect(clubId) {
        viewModel.fetchProposalDetail(clubId)
    }

    LaunchedEffect(isMatchButtonClicked) {
        matchViewModel.sendMatch(request = MatchSendRequest(cludId = clubId))
    }

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
            CampusBallTopBar()
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 52.dp)
                .verticalScroll(screenScrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(20.dp))

            Text(
                text = "일정 확인",
                color = colors.likeblack,
                style = typography.SB_24
            )
            Spacer(Modifier.height(19.dp))
            ClubLookUpCard(
                modifier = Modifier.padding(horizontal = 26.dp),
//                clubName = opponent?.clubName ?: "동아리 이름",
                clubName = opponent?.clubName ?: "동아리 이름",
//                clubIcon = opponent?.clubLogoUrl,
                collegeAndMajor = opponent?.departmentName ?: "한양대학교 전기전자공학과",
                onClick = {},
            )


            Spacer(Modifier.height(19.dp))

            Column(
                modifier = Modifier.padding(horizontal = 34.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "내 캘린더",
                    color = colors.skyblue,
                    style = typography.SB_9.copy(fontSize = 10.sp),
                    modifier = Modifier.padding(end = 13.dp, bottom = 2.dp)
                )
                ScheduleCalendar(
                    modifier = Modifier
                        .heightIn(max = 500.dp),
                    events = myCalendar
                )
            }

            Spacer(Modifier.height(9.dp))
            Column(
                modifier = Modifier.padding(horizontal = 34.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "상대방 캘린더",
                    color = colors.skyblue,
                    style = typography.SB_9.copy(fontSize = 10.sp),
                    modifier = Modifier.padding(end = 13.dp, bottom = 2.dp)
                )
                ScheduleCalendar(
                    modifier = Modifier
                        .heightIn(max = 500.dp),
                    events = opponentCalendar
                )
            }
            Spacer(Modifier.height(23.dp))

            Column(
                Modifier
                    .padding(horizontal = 26.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xFF969696),
                        shape = RoundedCornerShape(size = 10.dp)
                    )
                    .fillMaxWidth()
                    .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))
                    .padding(25.dp)

            ) {
                Text(
//                    text = "OOO는 교내 대회 수상한 이력이 있는 팀입니다! 안전하고 즐겁게 볼 차길 원합니다! 편하게 신청해주세요!",
                    text = opponent?.clubDescription
                        ?: "저희는 교내 대회 수상한 이력이 있는 팀입니다! 안전하고 즐겁게 볼 차길 원합니다! 편하게 신청해주세요!",
                    color = Color(0xFF656565),
                    style = typography.M_14.copy(fontSize = 15.sp),
                    lineHeight = 18.sp,
                )
            }

            Spacer(Modifier.height(12.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp)
                    .height(62.dp)
                    .background(color = colors.white)
                    .padding(horizontal = 39.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "동아리 인원수",
                    style = typography.M_18,
                    color = colors.likeblack,
                )

                Text(
                    text = "l",
                    style = typography.L_10.copy(fontSize = 18.sp),
                    color = colors.likeblack,
                    textAlign = TextAlign.Center
                )

                Row(
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "11",
                        style = typography.M_18,
                        color = Color(0xff137BD5),
                    )
                    Text(
                        text = "/11",
                        style = typography.M_18,
                        color = colors.likeblack,
                    )
                }

            }


            Spacer(Modifier.height(20.dp))

            // 친선 경기 신청 버튼
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .height(62.dp)
                    .background(color = colors.skyblue, shape = RoundedCornerShape(size = 100.dp))
                    .clip(RoundedCornerShape(size = 100.dp))
                    .clickable {
                        isMatchButtonClicked = true
                    },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "친선 경기 신청",
                        color = defaultCampusBallColors.white,
                        style = defaultCampusBallTypography.B_17.copy(fontSize = 18.sp),
                    )
                    Spacer(Modifier.width(10.dp))
                    Image(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = "",
                    )
                }
            }
            Spacer(Modifier.height(91.dp))
        }
        if (isMatchButtonClicked) {
            SuccessModal(value = "친선 경기 신청에 성공하였습니다!", buttonValue = "홈화면으로 이동", onClick = {
                navController.navigate(Route.Match.route)
            })
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun ComponentLookUpScreenPreview() {
    val navController = rememberNavController()
    ComponentLookUpScreen(navController = navController)
}