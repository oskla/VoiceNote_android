package com.larsson.voicenote_android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController


private val DarkColorPalette = darkColors(

    primary = ltRed,
    onPrimary = ltGrey,
    primaryVariant = dkGrey,
    secondary = brightRed,
    onSecondary = white,
    background = dkRed


)

private val LightColorPalette = lightColors(
    primary = ltRed ,
    onPrimary = Color.Black,
    primaryVariant = Color.Black,
    secondary = brightRed,
    onSecondary = white,
    background = dkRed,


    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)





@Composable
fun VoiceNote_androidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit

) {
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) {
        DarkColorPalette

    } else {
        LightColorPalette
    }
   val hej = systemUiController.setSystemBarsColor(
        color = Color.Black
    )
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}