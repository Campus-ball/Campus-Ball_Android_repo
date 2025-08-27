package com.konkuk.summerhackathon.presentation.schedule.component

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import java.util.Calendar

@Composable
fun DateInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "날짜",
    placeholder: String = "YYYY.MM.DD",
    iconResId: Int,
    minDateMillis: Long? = null,
    maxDateMillis: Long? = null
) {
    val context = LocalContext.current

    val (initY, initM0, initD) = remember(value) {
        parseYMDToPickerDefaults(value) ?: todayPickerDefaults()
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = colors.black,
            style = typography.EB_12,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(41.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, colors.indigo, RoundedCornerShape(10.dp))
                .clickable {
                    DatePickerDialog(
                        context,
                        { _, y, m, d ->
                            onValueChange("%04d.%02d.%02d".format(y, m + 1, d))
                        },
                        initY, initM0, initD
                    ).apply {
                        minDateMillis?.let { datePicker.minDate = it }
                        maxDateMillis?.let { datePicker.maxDate = it }
                    }.show()
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
                contentDescription = "달력",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

private fun parseYMDToPickerDefaults(s: String?): Triple<Int, Int, Int>? {
    val t = s?.trim().orEmpty()
    if (t.isEmpty()) return null
    val sep = when {
        t.contains('.') -> '.'
        t.contains('-') -> '-'
        else -> return null
    }
    val p = t.split(sep)
    if (p.size != 3) return null
    val y = p[0].toIntOrNull() ?: return null
    val m = p[1].toIntOrNull() ?: return null
    val d = p[2].toIntOrNull() ?: return null
    if (m !in 1..12 || d !in 1..31) return null
    return Triple(y, m - 1, d)
}

private fun todayPickerDefaults(): Triple<Int, Int, Int> {
    val c = Calendar.getInstance()
    return Triple(
        c.get(Calendar.YEAR),
        c.get(Calendar.MONTH),
        c.get(Calendar.DAY_OF_MONTH)
    )
}
