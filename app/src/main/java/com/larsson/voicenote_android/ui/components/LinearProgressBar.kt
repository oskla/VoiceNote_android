package com.larsson.voicenote_android.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LinearProgressBar(
    modifier: Modifier = Modifier,
    currentPosition: State<Long>,
    color: Color,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    seekTo: (Float) -> Unit,
    onSeekingFinished: () -> Unit,
    durationFloat: Float,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isDraggingLocal by interactionSource.collectIsDraggedAsState()
    val animationDuration = if (isDraggingLocal) 0 else 300

    val animatedProgress by animateFloatAsState(
        animationSpec = tween(durationMillis = animationDuration, easing = LinearEasing),
        targetValue = currentPosition.value.toFloat(),
        label = "ProgressBarAnimation",
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
            onValueChangeFinished = onSeekingFinished,
            interactionSource = interactionSource,
        )
    }
}
