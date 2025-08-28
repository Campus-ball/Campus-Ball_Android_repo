package com.konkuk.summerhackathon.presentation.schedule.component

import android.app.TimePickerDialog
import android.text.format.DateFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

@Composable
fun TimeInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "시간",
    placeholder: String = "PM 3:00",
    iconResId: Int,
    is24HourText: Boolean = false,
) {
    val context = LocalContext.current
    val (initHour24, initMinute) = remember(value) { parseTimeToDefaults(value) ?: (15 to 0) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = colors.black,
            style = typography.EB_12
        )

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(41.dp)
                .background(colors.white, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, colors.indigo, RoundedCornerShape(10.dp))
                .clickable {
                    TimePickerDialog(
                        context,
                        { _, h, m -> onValueChange(formatTime(h, m, is24HourText)) },
                        initHour24,
                        initMinute,
                        is24HourText || DateFormat.is24HourFormat(context)
                    ).show()
                }
                .padding(horizontal = 11.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val showText = if (value.isBlank()) placeholder else value
            val showColor = if (value.isBlank()) colors.gray else colors.black

            Text(
                text = showText,
                color = showColor,
                style = typography.M_14,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(12.dp))
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = "시간",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

private fun parseTimeToDefaults(s: String?): Pair<Int, Int>? {
    val t0 = s?.trim() ?: return null
    if (t0.isEmpty()) return null


    val t = t0.uppercase()
    Regex("""^(\d{1,2}):(\d{2})$""").matchEntire(t)?.let {
        val h = it.groupValues[1].toIntOrNull() ?: return null
        val m = it.groupValues[2].toIntOrNull() ?: return null
        if (h in 0..23 && m in 0..59) return h to m
    }

    val norm = t.replace("오전", "AM").replace("오후", "PM")
    Regex("""^(AM|PM)\s+(\d{1,2}):(\d{2})$""").matchEntire(norm)?.let {
        val ap = it.groupValues[1]
        var h = it.groupValues[2].toIntOrNull() ?: return null
        val m = it.groupValues[3].toIntOrNull() ?: return null
        if (h !in 1..12 || m !in 0..59) return null
        if (ap == "AM" && h == 12) h = 0
        if (ap == "PM" && h != 12) h += 12
        return h to m
    }
    return null
}

private fun formatTime(hour24: Int, minute: Int, is24: Boolean): String {
    return if (is24) {
        "%02d:%02d".format(hour24, minute)
    } else {
        val ap = if (hour24 < 12) "AM" else "PM"
        val h12 = when (hour24 % 12) {
            0 -> 12; else -> hour24 % 12
        }
        "%s %d:%02d".format(ap, h12, minute)
    }
}
