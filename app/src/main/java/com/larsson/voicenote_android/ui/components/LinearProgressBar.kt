package com.larsson.voicenote_android.ui.components

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LinearProgressBar(
    modifier: Modifier = Modifier,
    currentPosition: Int,
    color: Color,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    seekTo: (Float) -> Unit,
    durationFloat: Float,
) {
    val sliderPosition = remember { mutableStateOf(currentPosition.toFloat()) }
    val sliderTransitionAnimation by animateFloatAsState(
        targetValue = sliderPosition.value,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 0,
            easing = LinearEasing,
        ),
    )

    LaunchedEffect(key1 = currentPosition) {
        // if (currentPosition.toFloat() != sliderPosition.value) {
        Log.d("Slider", "currentPosition: $currentPosition")
        sliderPosition.value = currentPosition.toFloat()
        // }
    }

    Box(
        modifier = modifier.background(backgroundColor),
    ) {
        Slider(
            modifier = Modifier,
            value = sliderTransitionAnimation,
            onValueChange = { newValue ->
                sliderPosition.value = newValue
                seekTo.invoke(newValue)
            },
            colors = SliderDefaults.colors(thumbColor = color, activeTrackColor = color, inactiveTrackColor = color.copy(0.1f)),
            valueRange = 0f..durationFloat,
            onValueChangeFinished = { },
        )
    }
}

