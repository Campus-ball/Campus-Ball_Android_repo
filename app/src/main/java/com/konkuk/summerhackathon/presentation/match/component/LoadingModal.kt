package com.konkuk.summerhackathon.presentation.match.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

@Composable
fun LoadingModal(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize().background(colors.white).zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(55.dp),
                color = colors.black,
                strokeWidth = 4.dp
            )
            Spacer(modifier = Modifier.height(35.dp))
            Text(
                text = "상대 찾는 중 ...",
                style = typography.B_17.copy(fontSize = 30.sp),
                color = colors.black
            )
        }
    }
}