package com.konkuk.summerhackathon.presentation.proposal.screen

import android.widget.Toast
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
import com.konkuk.summerhackathon.data.dto.request.MatchRequest
import com.konkuk.summerhackathon.data.dto.response.ReceivedMatchResponse
import com.konkuk.summerhackathon.presentation.navigation.Route
import com.konkuk.summerhackathon.presentation.proposal.component.ProposalCard
import com.konkuk.summerhackathon.presentation.proposal.viewmodel.ReceivedMatchViewModel
import com.konkuk.summerhackathon.ui.theme.defaultCampusBallColors
import com.konkuk.summerhackathon.ui.theme.defaultCampusBallTypography

@Composable
fun ProposalScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: ReceivedMatchViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    val events by viewModel.events.collectAsStateWithLifecycle()

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val context = LocalContext.current


    val testEvents = listOf(
        ReceivedMatchResponse(
            requestId = 2,
            requestType = "랜덤 매칭 요청",
            clubId = 305,
            clubName = "동아리 이름1",
            departmentName = "건국대학교 컴퓨터공학부",
            clubLogoUrl = "https://example.com/logo1.png",
        ),
        ReceivedMatchResponse(
            requestId = 3,
            requestType = "친선 경기",
            clubId = 306,
            clubName = "동아리 이름2",
            departmentName = "한양대학교 컴퓨터공학부",
            clubLogoUrl = "https://example.com/logo2.png"
        ),
        ReceivedMatchResponse(
            requestId = 4,
            requestType = "랜덤 매칭 요청",
            clubId = 307,
            clubName = "동아리 이름3",
            departmentName = "대대대학교 컴퓨터공학부",
            clubLogoUrl = "https://example.com/logo3.png"
        ),
        ReceivedMatchResponse(
            requestId = 5,
            requestType = "친선 경기",
            clubId = 308,
            clubName = "동아리 이름4",
            departmentName = "건국대학교 컴퓨터공학부",
            clubLogoUrl = "https://example.com/logo4.png"
        )

    )


    LaunchedEffect(key1 = true) {
        viewModel.error.collect { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.actionResult.collect { isSuccess ->
            if (isSuccess) {
                Toast.makeText(context, "성공!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "실패!", Toast.LENGTH_SHORT).show()
            }
        }
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
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CampusBallTopBar()

                Spacer(Modifier.height(55.dp))

                Text(
                    text = "받은 신청 목록",
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
                    if (events.isEmpty()) {
                        Spacer(modifier = Modifier.size(200.dp))
                        Text(
                            text = "받은 신청 내역이 없습니다.",
                            style = defaultCampusBallTypography.B_20,
                            color = defaultCampusBallColors.likeblack,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    } else
                        events.forEach { event ->
                            ProposalCard(
                                isRandomMatching = event.requestType == "랜덤 매칭 요청",
                                onClickCard = {
                                    navController.navigate("${Route.ProposalDetail.route}/${event.clubId}")
//                                    navController.navigate(Route.ScheduleDetail.route)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                clubName = event.clubName,
//                        clubIcon = event.clubLogoUrl,     //TODO: 동아리 아이콘 url로 변경
                                universityAndMajor = event.departmentName,
                                onAccept = {
                                    viewModel.acceptMatch(MatchRequest(event.requestId))
                                },              // TODO: 수락 api 연결
                                onDecline = {
                                    viewModel.rejectMatch(MatchRequest(event.requestId))

                                }              // TODO: 거절 api 연결
                            )
                        }
                    /*      ProposalCard(isRandomMatching = true)
                          ProposalCard()
                          ProposalCard()
                          ProposalCard(isRandomMatching = true)
                          ProposalCard(isRandomMatching = true)
                          ProposalCard(isRandomMatching = true)
                          ProposalCard(isRandomMatching = true) */
                    Spacer(
                        modifier = Modifier.size(15.dp),
                    )
                }

            }
        }
    }
}

@Preview
@Composable
private fun ProposalScreenPreview() {
    val navController = rememberNavController()
    ProposalScreen(navController = navController)
}