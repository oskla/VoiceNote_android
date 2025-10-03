package com.larsson.voicenote_android.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

@OptIn(ExperimentalMaterial3Api::class)
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
            value = animatedProgress,
            onValueChange = { newValue ->
                seekTo.invoke(newValue)
            },
            modifier = Modifier,
            onValueChangeFinished = onSeekingFinished,
            colors = SliderDefaults.colors(thumbColor = color, activeTrackColor = color, inactiveTrackColor = color.copy(0.1f)),
            interactionSource = interactionSource,
            thumb = {
                Box(Modifier
                    .clip(CircleShape)
                    .size(20.dp)
                    .background(MaterialTheme.colorScheme.onBackground))
            },
            track = {
                val progress = animatedProgress / durationFloat
                Box(Modifier
                    .height(3.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onBackground.copy(0.3f)))
                Box(Modifier
                    .height(3.dp)
                    .fillMaxWidth(progress)
                    .background(MaterialTheme.colorScheme.onBackground))
            },
            valueRange = 0f..durationFloat
        )
//        Slider(
//            modifier = Modifier,
//            value = animatedProgress,
//            onValueChange = { newValue ->
//                seekTo.invoke(newValue)
//            },
//            colors = SliderDefaults.colors(thumbColor = color, activeTrackColor = color, inactiveTrackColor = color.copy(0.1f)),
//            valueRange = 0f..durationFloat,
//            onValueChangeFinished = onSeekingFinished,
//            interactionSource = interactionSource,
//        )
    }
}

@Preview
@Composable
private fun PreviewLinearProgressBar() {
    VoiceNote_androidTheme {
        LinearProgressBar(
            currentPosition = remember { mutableStateOf(123) },
            color = Color.Black,
            seekTo = {},
            onSeekingFinished = {},
            durationFloat = 12312312F
        )
    }
}
