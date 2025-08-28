package com.konkuk.summerhackathon.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

@Composable
fun DisabledField(
    label: String,
    value: String,
    check: Boolean? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            style = typography.EB_12,
            color = colors.gray
        )
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (check == true) Modifier
                    else Modifier.height(41.dp)
                )
                .background(color = colors.white, shape = RoundedCornerShape(10.dp))
                .border(width = 1.dp, color = colors.gray, shape = RoundedCornerShape(10.dp))
                .then(
                    if (check == false) {
                        Modifier.padding(start = 11.dp)
                    } else {
                        Modifier.padding(start = 11.dp, top = 13.dp, bottom = 13.dp)
                    }
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = value,
                style = typography.M_14,
                color = colors.gray
            )
        }
    }
}