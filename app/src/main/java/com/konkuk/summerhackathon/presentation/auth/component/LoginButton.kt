package com.konkuk.summerhackathon.presentation.auth.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

@Composable
fun LoginButton(
    text: String = "로그인",
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(124.dp)
            .height(48.dp)
            .drawBehind {
                drawRoundRect(
                    color = Color.White.copy(alpha = 0.6f),
                    cornerRadius = CornerRadius(24.dp.toPx(), 24.dp.toPx()),
                    topLeft = Offset(0f, 8f),
                    size = Size(size.width, size.height)
                )
            }
            .background(
                color = if (enabled) colors.skyblue else colors.gray,
                shape = RoundedCornerShape(100.dp)
            )
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = colors.white,
            style = typography.B_17
        )
    }
}
