package com.tasky.core.presentation.designsystem.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tasky.core.presentation.designsystem.R

val inter =
    FontFamily(
        Font(R.font.inter_thin, FontWeight.Thin),
        Font(R.font.inter_extralight, FontWeight.ExtraLight),
        Font(R.font.inter_light, FontWeight.Light),
        Font(R.font.inter_regular, FontWeight.Normal),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_semibold, FontWeight.SemiBold),
        Font(R.font.inter_bold, FontWeight.Bold),
        Font(R.font.inter_extrabold, FontWeight.ExtraBold),
        Font(R.font.inter_black, FontWeight.Black),
    )

val typography =
    Typography(
        headlineLarge = TextStyle(
            fontFamily = inter
        ),
        headlineSmall = TextStyle(
            fontFamily = inter
        ),
        displayLarge = TextStyle(
            fontFamily = inter
        ),
        displayMedium = TextStyle(
            fontFamily = inter
        ),
        displaySmall = TextStyle(
            fontFamily = inter
        ),
        bodySmall =
        TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 20.sp,
        ),
        bodyMedium =
        TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 22.sp,
        ),
        bodyLarge =
        TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.4.sp,
        ),
        labelLarge =
        TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 24.sp,
        ),
        headlineMedium =
        TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
        ),
    )
