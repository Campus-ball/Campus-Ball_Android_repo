package com.konkuk.summerhackathon.presentation.settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class Btn { Filled, Outlined }

@Composable
fun BoxButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    variant: Btn = Btn.Filled,
    modifier: Modifier = Modifier,
    minWidth: Dp = 96.dp,
    height: Dp = 40.dp,
    corner: Dp = 12.dp,
) {
    val shape = RoundedCornerShape(corner)
    val base = modifier
        .height(height)
        .widthIn(min = minWidth)
        .clickable(
            enabled = enabled,
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) { onClick() }

    val border = Color(0xFFBDBDBD)
    val fill   = Color(0xFF6E6E6E)

    Box(
        modifier = when (variant) {
            Btn.Filled -> base.background(if (enabled) fill else fill.copy(alpha = 0.4f), shape)
            Btn.Outlined -> base
                .border(1.dp, if (enabled) border else border.copy(alpha = 0.5f), shape)
                .background(Color.Transparent, shape)
        },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = if (variant == Btn.Filled) Color.White else Color(0xFF222222))
    }
}

@Composable
fun ConfirmModalBox(
    visible: Boolean,
    message: String,
    leftText: String,
    rightText: String,
    onLeft: () -> Unit,
    onRight: () -> Unit,
    onDismiss: () -> Unit = {}
) {
    if (!visible) return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.35f))
            .clickable(
                enabled = true,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        val shape = RoundedCornerShape(18.dp)
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .shadow(12.dp, shape, clip = false)
                .background(Color.White, shape)
                .clickable(
                    enabled = true,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { }
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(message, color = Color(0xFF222222))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BoxButton(text = leftText, onClick = onLeft, variant = Btn.Outlined)
                    BoxButton(text = rightText, onClick = onRight, variant = Btn.Filled)
                }
            }
        }
    }
}