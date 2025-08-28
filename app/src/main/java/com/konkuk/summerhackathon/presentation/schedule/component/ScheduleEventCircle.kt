package com.konkuk.summerhackathon.presentation.schedule.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ScheduleEventCircle(
    modifier: Modifier = Modifier,
    isMatch: Boolean = false,
    isAvailable: Boolean = false,
    isAcademic: Boolean = false
) {
    Row(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .size(4.dp)
                .clip(CircleShape)
                .background(if (isMatch) Color(0xffF04D23) else Color.Transparent)
        )
        Spacer(Modifier.width(2.dp))
        Box(
            modifier = Modifier
                .size(4.dp)
                .clip(CircleShape)
                .background(if (isAvailable) Color(0xffF9C433) else Color.Transparent)
        )
        Spacer(Modifier.width(2.dp))
        Box(
            modifier = Modifier
                .size(4.dp)
                .clip(CircleShape)
                .background(if (isAcademic) Color(0xff258FFF) else Color.Transparent)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScheduleEventCirclePreview() {
    ScheduleEventCircle(Modifier, isMatch = true, isAvailable = true, isAcademic = true)
}