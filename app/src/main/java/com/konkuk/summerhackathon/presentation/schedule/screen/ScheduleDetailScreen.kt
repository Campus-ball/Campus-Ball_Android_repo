package com.konkuk.summerhackathon.presentation.schedule.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ScheduleDetailScreen(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "일정 확인 화면")
    }
}

@Preview
@Composable
private fun ScheduleDetailScreenPreview() {
    ScheduleDetailScreen()
}