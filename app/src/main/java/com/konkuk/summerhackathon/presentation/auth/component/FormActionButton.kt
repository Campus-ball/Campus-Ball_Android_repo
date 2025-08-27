package com.konkuk.summerhackathon.presentation.auth.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.konkuk.summerhackathon.core.util.noRippleClickable
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

@Composable
fun FormActionButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 34.dp,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    missingReasons: List<String> = emptyList(),
) {
    var showDialog by remember { mutableStateOf(false) }

    val borderColor = if (enabled) colors.indigo else colors.gray
    val textColor   = if (enabled) colors.indigo else colors.gray

    Box(
        modifier = modifier
            .width(41.dp)
            .height(height)
            .clip(shape)
            .border(BorderStroke(2.dp, borderColor), shape)
            .background(Color.Transparent)
            .noRippleClickable {
                if (enabled) onClick() else showDialog = true
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = typography.M_14, color = textColor)
    }

    if (showDialog) {
        MissingInputDialog(
            reasons = missingReasons,
            onConfirm = { showDialog = false }
        )
    }
}

@Composable
fun MissingInputDialog(
    reasons: List<String>,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onConfirm,
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White,
        tonalElevation = 2.dp,
        title = {
            Text(
                "모든 항목을 빠짐없이 입력해주세요",
                style = typography.B_17,
                color = colors.black
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                val body = if (reasons.isEmpty())
                    "입력창이 아직 완료되지 않았어요. 빠진 항목을 채워주세요."
                else
                    "다음 항목을 확인해주세요."

                Text(body, style = typography.M_14, color = colors.black)

                if (reasons.isNotEmpty()) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        reasons.forEach { r ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(RoundedCornerShape(50))
                                        .background(colors.indigo.copy(alpha = 0.9f))
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(r, style = typography.M_14, color = colors.black)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(colors.indigo.copy(alpha = 0.08f))
                    .border(BorderStroke(1.dp, colors.indigo), RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = 8.dp)
                    .noRippleClickable { onConfirm() },
                contentAlignment = Alignment.Center
            ) {
                Text("확인", style = typography.B_11, color = colors.indigo)
            }
        }
    )
}

