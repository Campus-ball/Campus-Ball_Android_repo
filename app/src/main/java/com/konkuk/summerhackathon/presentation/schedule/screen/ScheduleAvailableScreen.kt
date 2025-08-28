package com.konkuk.summerhackathon.presentation.schedule.screen

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.konkuk.summerhackathon.R
import com.konkuk.summerhackathon.core.component.CampusBallTopBar
import com.konkuk.summerhackathon.data.dto.request.AvailabilityRequest
import com.konkuk.summerhackathon.presentation.navigation.Route
import com.konkuk.summerhackathon.presentation.schedule.component.DateInputField
import com.konkuk.summerhackathon.presentation.schedule.component.TimeInputField
import com.konkuk.summerhackathon.presentation.schedule.viewmodel.AvailabilityViewModel
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography
import com.konkuk.summerhackathon.ui.theme.defaultCampusBallColors
import com.konkuk.summerhackathon.ui.theme.defaultCampusBallTypography
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


// 경기 가용 시간 등록 화면
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleAvailableScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: AvailabilityViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val viewModel: AvailabilityViewModel = hiltViewModel()
    val status by viewModel.statusCode.collectAsState()


    var date by remember { mutableStateOf("") }
    val cal = Calendar.getInstance()
    var start by remember { mutableStateOf("") }
    var end by remember { mutableStateOf("") }
    var isEndBeforeStart by remember { mutableStateOf(false) }
    var isWeeklyChecked by remember { mutableStateOf(false) }

    LaunchedEffect(start, end) {
        if (!end.isBlank() && !start.isBlank()) {
            isEndBeforeStart = !isStartBeforeEnd(start, end)
        }
    }

    LaunchedEffect(status) {
        when (status) {
            200 -> navController.navigate(Route.Schedule.route)
            -1 -> Toast.makeText(context, "서버 연결 실패", Toast.LENGTH_SHORT).show()
        }
    }

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
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CampusBallTopBar()

            Spacer(Modifier.height(55.dp))

            Text(
                text = "경기 가용 시간 등록",
                color = defaultCampusBallColors.likeblack,
                style = defaultCampusBallTypography.B_24
            )
            Spacer(Modifier.height(32.dp))

            DateInputField(
                value = date,
                onValueChange = { date = it },
                iconResId = R.drawable.img_calendar,
                label = "날짜",
                placeholder = "YYYY.MM.DD",
                minDateMillis = cal.timeInMillis
            )

            Spacer(Modifier.height(12.dp))
            TimeInputField(
                value = start,
                onValueChange = { start = it },
                label = "시작시간",
                iconResId = R.drawable.img_clock,
                is24HourText = false
            )

            Spacer(Modifier.height(12.dp))
            TimeInputField(
                value = end,
                onValueChange = { end = it },
                label = "종료시간",
                iconResId = R.drawable.img_clock,
                is24HourText = false
            )
            Spacer(Modifier.height(6.dp))
            if (isEndBeforeStart)
                Text(
                    text = "종료 시간이 시작 시간보다 빠르거나 같습니다.",
                    color = defaultCampusBallColors.crimson,
                    style = defaultCampusBallTypography.L_10.copy(fontSize = 12.sp),
                )


            Log.d("날짜", "$date")
            Log.d("시작시간", "$start")
            Log.d("종료시간", "$end")
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isWeeklyChecked,
                onCheckedChange = { isWeeklyChecked = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = colors.skyblue,
                    uncheckedColor = colors.gray,
                    checkmarkColor = colors.white
                )
            )

            Text(text = "매 주 반복", style = typography.B_11, color = colors.likeblack)

        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Box(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 26.dp, vertical = 28.dp)
                .height(62.dp)
                .clip(RoundedCornerShape(size = 100.dp))
                .background(
                    color = Color(0xFF007BFF),
                    shape = RoundedCornerShape(size = 100.dp)
                )
                .clip(RoundedCornerShape(size = 100.dp))
                .clickable {
                    if (!isEndBeforeStart && !date.isBlank() && !start.isBlank() && !end.isBlank()) {
                        val formatter = DateTimeFormatter.ofPattern("a h:mm", Locale.ENGLISH)

                        val formattedDate = date.replace(".", "-")
                        val formattedStart = LocalTime.parse(start, formatter).toString()
                        val formattedEnd = LocalTime.parse(end, formatter).toString()
                        Log.d("포맷된 시간", "$formattedDate")
                        Log.d("포맷된 시작 시간", "$formattedStart")
                        Log.d("포맷된 끝나는 시간", "$formattedEnd")

                        viewModel.registerAvailability(
                            AvailabilityRequest(
                                formattedDate,
                                formattedStart,
                                formattedEnd,
                                isWeeklyChecked
                            )
                        )

//                        navController.navigate(Route.Schedule.route)
                    } else {
                        Toast.makeText(
                            context,
                            "필수 값을 제대로 입력해주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        ) {
            Text(
                text = "일정 등록하기",
                color = defaultCampusBallColors.white,
                style = defaultCampusBallTypography.B_17.copy(fontSize = 18.sp),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun ScheduleAvailableScreenPreview() {
    val navController = rememberNavController()
    ScheduleAvailableScreen(navController = navController)
}


@RequiresApi(Build.VERSION_CODES.O)
fun isStartBeforeEnd(start: String, end: String): Boolean {
    val formatter = DateTimeFormatter.ofPattern("a h:mm", Locale.ENGLISH)

    val startTime = LocalTime.parse(start, formatter)
    val endTime = LocalTime.parse(end, formatter)

    return startTime.isBefore(endTime)
}