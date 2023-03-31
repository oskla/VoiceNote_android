package com.larsson.voicenote_android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

// TODO - hook this up to actual audio
@Composable
fun LinearProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    color: Color,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    onProgressChanged: (Float) -> Unit,
) {
    Box(
        modifier = modifier.background(backgroundColor),
    ) {
        Slider(
            modifier = Modifier,
            value = progress,
            onValueChange = { onProgressChanged.invoke(it) },
            colors = SliderDefaults.colors(thumbColor = color, activeTrackColor = color, inactiveTrackColor = color.copy(0.1f)),
            valueRange = 0f..1f,
        )
    }
}
