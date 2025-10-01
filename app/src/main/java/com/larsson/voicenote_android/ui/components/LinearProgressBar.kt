package com.larsson.voicenote_android.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LinearProgressBar(
    modifier: Modifier = Modifier,
    currentPosition: State<Long>,
    color: Color,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    seekTo: (Float) -> Unit,
    durationFloat: Float,
) {
    // TODO - fix the seeking. This need to happen locally as well as in the player.
    //  Now it only happesn in the player
    val animatedProgress by animateFloatAsState(
        animationSpec = tween(300, easing = LinearEasing),
        targetValue = currentPosition.value.toFloat(),
        label = "ProgressBarAnimation"
    )
    Box(
        modifier = modifier.background(backgroundColor),
    ) {
        Slider(
            modifier = Modifier,
            value = animatedProgress,
            onValueChange = { newValue ->
                seekTo.invoke(newValue)
            },
            colors = SliderDefaults.colors(thumbColor = color, activeTrackColor = color, inactiveTrackColor = color.copy(0.1f)),
            valueRange = 0f..durationFloat,
            onValueChangeFinished = { },
        )
    }
}
