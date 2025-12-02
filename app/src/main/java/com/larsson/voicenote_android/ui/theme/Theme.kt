package com.larsson.voicenote_android.ui.theme // ktlint-disable filename

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val AppLightColorScheme = lightColorScheme(
    onBackground = notBlack,
    onPrimary = notBlack,
    secondary = ltGrey,
    onSecondary = Color.Black,
    outline = dkGrey,
    background = notWhite,
)

val AppDarkColorScheme = darkColorScheme(
    onBackground = notWhite,
    onPrimary = notWhite,
    secondary = dkdkGrey,
    onSecondary = white,
    outline = dkGrey,
    background = notBlack,
)

@Composable
fun VoiceNoteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val systemUiController = rememberSystemUiController()

    val appColorScheme = if (darkTheme) {
        AppDarkColorScheme.also {
            systemUiController.setSystemBarsColor(notBlack)
            systemUiController.setNavigationBarColor(notBlack)
        }
    } else {
        AppLightColorScheme.also {
            systemUiController.setStatusBarColor(color = notWhite, darkIcons = true)
            systemUiController.setNavigationBarColor(notWhite)
        }
    }

    MaterialTheme(
        colorScheme = appColorScheme,
        typography = AppTypography,
        shapes = AppShapes,
    ) {
        val textSelectionColors = TextSelectionColors(
            handleColor = appColorScheme.onBackground,
            backgroundColor = appColorScheme.onBackground.copy(alpha = 0.4f)
        )

        CompositionLocalProvider(
            LocalTextSelectionColors provides textSelectionColors,
            content = content
        )
    }
}
