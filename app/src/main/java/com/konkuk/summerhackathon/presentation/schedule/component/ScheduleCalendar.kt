package com.konkuk.summerhackathon.presentation.schedule.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.konkuk.summerhackathon.data.dto.response.CalendarEventDto
import com.konkuk.summerhackathon.presentation.schedule.EventType
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleCalendar(
    modifier: Modifier = Modifier,
    events: List<CalendarEventDto> = emptyList()
) {

    val testEvents = listOf(
        CalendarEventDto(
            eventId = 1,
            eventType = EventType.ACADEMIC,
            title = "중간고사",
            startDate = "2025-08-14",
            endDate = "2025-08-18",
            startTime = "18:00",
            endTime = "19:00",
        ),
        CalendarEventDto(
            eventId = 5,
            eventType = EventType.ACADEMIC,
            title = "dss",
            startDate = "2025-08-02",
            endDate = "2025-08-04",
            startTime = "18:00",
            endTime = "19:00",
        ),
        CalendarEventDto(
            eventId = 2,
            eventType = EventType.MATCH,
            title = "스터디 모임",
            startDate = "2025-08-26",
            endDate = "2025-08-28",
            startTime = "10:00",
            endTime = "12:30",
        ),
        CalendarEventDto(
            eventId = 3,
            eventType = EventType.AVAILABILITY,
            title = "축구 경기",
            startDate = "2025-08-22",
            endDate = "2025-08-22",
            startTime = "13:00",
            endTime = "15:00",
        ),
        CalendarEventDto(
            eventId = 6,
            eventType = EventType.MATCH,
            title = "축구 경기",
            startDate = "2025-08-22",
            endDate = "2025-08-22",
            startTime = "13:00",
            endTime = "15:00",
        ),
        CalendarEventDto(
            eventId = 6,
            eventType = EventType.ACADEMIC,
            title = "축구 경기",
            startDate = "2025-08-22",
            endDate = "2025-08-22",
            startTime = "13:00",
            endTime = "15:00",
        )

    )


    var currentYearMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDateTime by remember { mutableStateOf(LocalDateTime.now()) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 8.dp))
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 8.dp)
    ) {
        CalendarView(
            yearMonth = currentYearMonth,
            selectedDateTime = selectedDateTime,
            onDateTimeSelected = { newDateTime ->
                selectedDateTime = newDateTime
                currentYearMonth = YearMonth.from(newDateTime)
            },
            onMonthChanged = { newMonth ->
                currentYearMonth = newMonth
            },
            events = testEvents     //TODO: 실제 이벤트값으로 나중에 변경
        )
        /*        Row {
                    Text(
                        text = "${currentYearMonth}-${selectedDateTime.dayOfMonth}일 : ",
                        style = typography.M_18,
                        color = colors.likeblack,
                    )
                    Text(
                        text = "일정",
                        style = typography.M_18,
                        color = colors.likeblack,
                    )
                }*/
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarView(
    yearMonth: YearMonth,
    selectedDateTime: LocalDateTime,
    onDateTimeSelected: (LocalDateTime) -> Unit,
    onMonthChanged: (YearMonth) -> Unit,
    events: List<CalendarEventDto> = emptyList()
) {
    Column {
        CalendarHeader(
            yearMonth = yearMonth,
            onPrevMonth = { onMonthChanged(yearMonth.minusMonths(1)) },
            onNextMonth = { onMonthChanged(yearMonth.plusMonths(1)) }
        )
        Spacer(modifier = Modifier.height(12.dp))
        WeekDaysHeader()
        Spacer(modifier = Modifier.height(8.dp))
        CalendarGrid(
            yearMonth = yearMonth,
            selectedDateTime = selectedDateTime, // 변경된 상태 전달
            onDateTimeSelected = onDateTimeSelected, //변경된 콜백 전달
            events = events
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarHeader(yearMonth: YearMonth, onPrevMonth: () -> Unit, onNextMonth: () -> Unit) {
    val formatter = DateTimeFormatter.ofPattern("M월", Locale.KOREAN)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = onPrevMonth) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "이전 달",
                tint = Color(0xffB5BEC6)
            )
        }
        Text(
            text = yearMonth.format(formatter),
            style = typography.B_11.copy(fontSize = 14.sp),
            textAlign = TextAlign.Center,
            color = Color(0xFF4A5660),
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onNextMonth) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "다음 달",
                tint = Color(0xffB5BEC6)
            )
        }
    }
}


@Composable
fun WeekDaysHeader() {
    val weekDays = listOf("SAN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
    Row(modifier = Modifier.fillMaxWidth()) {
        weekDays.forEach { day ->
            Text(
                text = day,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = typography.B_11.copy(fontSize = 10.sp),
                color = Color(0xFFB5BEC6)
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarGrid(
    yearMonth: YearMonth,
    selectedDateTime: LocalDateTime,
    onDateTimeSelected: (LocalDateTime) -> Unit,
    events: List<CalendarEventDto>
) {
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstOfMonth = yearMonth.atDay(1)
    val startOffset = firstOfMonth.dayOfWeek.value % 7



    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        contentPadding = PaddingValues(vertical = 4.dp)
    ) {
        items(startOffset) { Box(modifier = Modifier.aspectRatio(1f)) }

        items(daysInMonth) { dayOfMonth ->
            val day = dayOfMonth + 1
            val date = yearMonth.atDay(day)

            val isSelected = (date == selectedDateTime.toLocalDate())

            /*
                        val hasAcademicEvent = events.any { event ->
                            (event.startDate == date.toString() || event.endDate == date.toString()) && event.eventType == EventType.ACADEMIC
                        }   // events에 있는 시작 날짜가 현재 날짜면 이벤트 존재

                        val foundEvent = events.find { event ->
                            event.startDate == date.toString() || event.endDate == date.toString()
                        }
            */

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            val hasAcademyEvent = events.any { event ->
                val start = LocalDate.parse(event.startDate, formatter)
                val end = LocalDate.parse(event.endDate, formatter)
                val current = date

                event.eventType == EventType.ACADEMIC &&
                        (current.isEqual(start) || current.isEqual(end) || (current.isAfter(start) && current.isBefore(
                            end
                        )))
            }

            val hasMatchEvent = events.any { event ->
                val start = LocalDate.parse(event.startDate, formatter)
                val end = LocalDate.parse(event.endDate, formatter)
                val current = date

                event.eventType == EventType.MATCH &&
                        (current.isEqual(start) || current.isEqual(end) || (current.isAfter(start) && current.isBefore(
                            end
                        )))
            }

            val hasAvailabilityEvent = events.any { event ->
                val start = LocalDate.parse(event.startDate, formatter)
                val end = LocalDate.parse(event.endDate, formatter)
                val current = date

                event.eventType == EventType.AVAILABILITY &&
                        (current.isEqual(start) || current.isEqual(end) || (current.isAfter(start) && current.isBefore(
                            end
                        )))
            }

            var backgroundColor = Color.Transparent

//            if (foundEvent != null) {
//                backgroundColor = when (foundEvent.eventType) {
//                    EventType.ACADEMIC -> Color(0xff33B4F9)
//                    EventType.MATCH -> Color(0xFFF04D23)
//                    EventType.AVAILABILITY -> Color(0xffF9C433)
//                }
//            }

//            if (hasAcademyEvent)
//                backgroundColor = Color(0xff33B4F9)
//            if (hasMatchEvent)
//                backgroundColor = Color(0xFFF04D23)
//            if (hasAvailabilityEvent)
//                backgroundColor = Color(0xffF9C433)


            /*                if (isSelected) {
                            Color(0xFFF04D23)
                        } else Color.Transparent    */
            /*            val textColor = if (backgroundColor == Color.Transparent) Color(0xFF4A5660)
                        else colors.white*/

            val textColor = Color(0xFF4A5660)

//            val textColor=if (isSelected) colors.white else Color(0xFF4A5660)


            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(4.dp)
                    .clip(CircleShape)
//                    .background(backgroundColor)
                    .clickable {
                        val newDateTime = date.atTime(selectedDateTime.toLocalTime())
                        onDateTimeSelected(newDateTime)
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = day.toString(),
                        style = if (!isSelected) typography.SB_14 else typography.B_24.copy(fontSize = 14.sp),
                        color = textColor,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                    Spacer(Modifier.height(2.dp))
                    ScheduleEventCircle(
                        Modifier,
                        hasMatchEvent,
                        hasAvailabilityEvent,
                        hasAcademyEvent
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview()
@Composable
fun ScheduleCalendarPreview() {
    ScheduleCalendar()
}