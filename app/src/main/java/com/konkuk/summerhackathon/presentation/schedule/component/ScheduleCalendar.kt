package com.konkuk.summerhackathon.presentation.schedule.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleCalendar() {
    var currentYearMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDateTime by remember { mutableStateOf(LocalDateTime.now()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 8.dp))
            .shadow(
                elevation = 19.dp,
                spotColor = Color(0x17000000),
                ambientColor = Color(0x17000000)
            )
            .padding(start = 24.dp, end = 24.dp, top = 20.dp, bottom = 12.dp)
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
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarView(
    yearMonth: YearMonth,
    selectedDateTime: LocalDateTime,
    onDateTimeSelected: (LocalDateTime) -> Unit,
    onMonthChanged: (YearMonth) -> Unit
) {
    Column {
        CalendarHeader(
            yearMonth = yearMonth,
            onPrevMonth = { onMonthChanged(yearMonth.minusMonths(1)) },
            onNextMonth = { onMonthChanged(yearMonth.plusMonths(1)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        WeekDaysHeader()
        Spacer(modifier = Modifier.height(12.dp))
        CalendarGrid(
            yearMonth = yearMonth,
            selectedDateTime = selectedDateTime, // 변경된 상태 전달
            onDateTimeSelected = onDateTimeSelected //변경된 콜백 전달
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
    onDateTimeSelected: (LocalDateTime) -> Unit
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

            DayCell(
                day = day.toString(),
                isSelected = isSelected,
                onClick = {
                    val newDateTime = date.atTime(selectedDateTime.toLocalTime())
                    onDateTimeSelected(newDateTime)
                }
            )
        }
    }
}

@Composable
fun DayCell(day: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Color(0xFFF47121) else Color.Transparent
    val textColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onBackground

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day,
            style = if (!isSelected) typography.SB_16 else typography.B_24.copy(fontSize = 16.sp),
            color = textColor,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview()
@Composable
fun ScheduleCalendarPreview() {
    ScheduleCalendar()
}