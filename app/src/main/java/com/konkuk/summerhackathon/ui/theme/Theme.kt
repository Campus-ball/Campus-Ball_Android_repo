package com.konkuk.summerhackathon.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object SummerHackathonTheme {
    val colors: CampusBallColors
        @Composable
        @ReadOnlyComposable
        get() = LocalCampusBallColorsProvider.current

    val typography: CampusBallTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalCampusBallTypographyProvider.current
}

@Composable
fun SummerHackathonTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content
    )
}