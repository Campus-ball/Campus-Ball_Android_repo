package com.konkuk.summerhackathon.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

val White = Color(color = 0xFFFFFFFF)
val LightGray = Color(color = 0xFFBDBDBD)
val Gray = Color(color = 0xFF767676)
val Black = Color(color = 0xFF000000)
val SkyBlue = Color(color = 0xFF007BFF)
val Indigo = Color(color = 0xFF454BFF)
val Blue = Color(color = 0xFF1E48A8)
val MediumBlue = Color(color = 0xFF4483D0)
val Magenta = Color(color = 0xFFFF00EE)
val Red = Color(color = 0xFFFF0000)
val Crimson = Color(color = 0xFFD04444)
val DarkRed = Color(color = 0xFF8F2626)
val Salmon = Color(color = 0xFFEE5E60)
val Green = Color(color = 0xFF2ECC71)
val Yellow = Color(color = 0xFFFFD84D)

@Immutable
data class CampusBallColors(
    val white: Color,
    val lightgray: Color,
    val gray: Color,
    val black: Color,
    val skyblue: Color,
    val indigo: Color,
    val blue: Color,
    val mediumblue: Color,
    val magenta: Color,
    val red: Color,
    val crimson: Color,
    val darkred: Color,
    val salmon: Color,
    val green: Color,
    val yellow: Color
)

val defaultCampusBallColors = CampusBallColors(
    white = White,
    lightgray = LightGray,
    gray = Gray,
    black = Black,
    skyblue = SkyBlue,
    indigo = Indigo,
    blue = Blue,
    mediumblue = MediumBlue,
    magenta = Magenta,
    red = Red,
    crimson = Crimson,
    darkred = DarkRed,
    salmon = Salmon,
    green = Green,
    yellow = Yellow
)

val LocalCampusBallColorsProvider = staticCompositionLocalOf { defaultCampusBallColors }