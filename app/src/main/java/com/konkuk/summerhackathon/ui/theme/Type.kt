package com.konkuk.summerhackathon.ui.theme

import android.R.attr.fontFamily
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.sp
import com.konkuk.summerhackathon.R

val FontExtraBold = FontFamily(Font(R.font.pretendard_extrabold))
val FontBold = FontFamily(Font(R.font.pretendard_bold))
val FontSemiBold = FontFamily(Font(R.font.pretendard_semibold))
val FontMedium = FontFamily(Font(R.font.pretendard_medium))
val FontRegular = FontFamily(Font(R.font.pretendard_regular))
val FontLight = FontFamily(Font(R.font.pretendard_light))

@Immutable
data class CampusBallTypography(
    val EB_12: TextStyle,

    val B_24: TextStyle,
    val B_20: TextStyle,
    val B_17: TextStyle,
    val B_11: TextStyle,

    val SB_24: TextStyle,
    val SB_16: TextStyle,
    val SB_14: TextStyle,
    val SB_12: TextStyle,
    val SB_9: TextStyle,

    val M_18: TextStyle,
    val M_14: TextStyle,
    val M_8: TextStyle,

    val R_15: TextStyle,
    val R_12: TextStyle,
    val R_11: TextStyle,
    val R_8: TextStyle,

    val L_10: TextStyle
)

val defaultCampusBallTypography = CampusBallTypography(
    EB_12 = TextStyle(
        fontFamily = FontExtraBold,
        fontSize = 12.sp,
        lineHeight = 12.sp
    ),

    B_24 = TextStyle(
        fontFamily = FontBold,
        fontSize = 24.sp,
        lineHeight = 24.sp
    ),
    B_20 = TextStyle(
        fontFamily = FontBold,
        fontSize = 20.sp,
        lineHeight = 20.sp
    ),
    B_17 = TextStyle(
        fontFamily = FontBold,
        fontSize = 17.sp,
        lineHeight = 17.sp
    ),
    B_11 = TextStyle(
        fontFamily = FontBold,
        fontSize = 11.sp,
        lineHeight = 11.sp
    ),

    SB_24 = TextStyle(
        fontFamily = FontSemiBold,
        fontSize = 24.sp,
        lineHeight = 24.sp
    ),
    SB_16 = TextStyle(
        fontFamily = FontSemiBold,
        fontSize = 16.sp,
        lineHeight = 16.sp
    ),
    SB_14 = TextStyle(
        fontFamily = FontSemiBold,
        fontSize = 14.sp,
        lineHeight = 14.sp
    ),
    SB_12 = TextStyle(
        fontFamily = FontSemiBold,
        fontSize = 12.sp,
        lineHeight = 12.sp
    ),
    SB_9 = TextStyle(
        fontFamily = FontSemiBold,
        fontSize = 9.sp,
        lineHeight = 9.sp
    ),

    M_18 = TextStyle(
        fontFamily = FontMedium,
        fontSize = 18.sp,
        lineHeight = 18.sp
    ),
    M_14 = TextStyle(
        fontFamily = FontMedium,
        fontSize = 14.sp,
        lineHeight = 14.sp
    ),
    M_8 = TextStyle(
        fontFamily = FontMedium,
        fontSize = 8.sp,
        lineHeight = 8.sp
    ),

    R_15 = TextStyle(
        fontFamily = FontRegular,
        fontSize = 15.sp,
        lineHeight = 15.sp
    ),
    R_12 = TextStyle(
        fontFamily = FontRegular,
        fontSize = 12.sp,
        lineHeight = 12.sp
    ),
    R_11 = TextStyle(
        fontFamily = FontRegular,
        fontSize = 11.sp,
        lineHeight = 11.sp
    ),
    R_8 = TextStyle(
        fontFamily = FontRegular,
        fontSize = 8.sp,
        lineHeight = 8.sp
    ),

    L_10 = TextStyle(
        fontFamily = FontLight,
        fontSize = 10.sp,
        lineHeight = 10.sp
    )
)

val LocalCampusBallTypographyProvider = staticCompositionLocalOf { defaultCampusBallTypography }