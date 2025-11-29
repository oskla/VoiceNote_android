package com.larsson.voicenote_android.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.larsson.voicenote_android.R

val SpaceGroteskFontFamily = FontFamily(
    Font(resId = R.font.space_grotesk_variable_fontwght),
)

// Set of Material typography styles to start with
val AppTypography = Typography(
    bodyMedium = TextStyle(
        fontFamily = SpaceGroteskFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = SpaceGroteskFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = SpaceGroteskFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 14.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = SpaceGroteskFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        textAlign = TextAlign.Center,
    ),
)
