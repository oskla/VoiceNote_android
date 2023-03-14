package com.larsson.voicenote_android.ui.components

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

enum class ToggleVariant {
    NOTES,
    RECORDINGS
}

@Composable
fun TopToggleBar(
    modifier: Modifier = Modifier

) {
    var currentVariant = remember { mutableStateOf(ToggleVariant.NOTES) }

    Column(
        modifier = Modifier
            .padding(vertical = 33.dp)
            .background(Color.Transparent)
    ) {
        Crossfade(
            targetState = currentVariant.value,
            animationSpec = tween(
                durationMillis = 200,
                easing = LinearEasing
            )
        ) { component ->
            when (component) {
                ToggleVariant.NOTES -> {
                    ToggleBar(selectedLeft = true, selectedRight = false, currentVariant = currentVariant)
                }

                ToggleVariant.RECORDINGS -> {
                    ToggleBar(selectedLeft = false, selectedRight = true, currentVariant = currentVariant)
                }
            }
        }
    }
}

@Composable
fun ToggleBar(
    selectedLeft: Boolean,
    selectedRight: Boolean,
    currentVariant: MutableState<ToggleVariant>
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.Transparent)
            .border(
                BorderStroke(0.8.dp, MaterialTheme.colorScheme.outline),
                shape = shapes.medium
            )

    ) {
        ToggleBox(
            text = "Notes",
            selected = selectedLeft,
            onClick = {
                currentVariant.value = ToggleVariant.NOTES
            },
            modifier = Modifier.weight(1f)
        )

        ToggleBox(
            text = "Recordings",
            onClick = {
                currentVariant.value = ToggleVariant.RECORDINGS
            },
            selected = selectedRight,
            modifier = Modifier.weight(1f)

        )
    }
}

@Composable
fun ToggleBox(
    text: String,
    height: Dp = 32.dp,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .padding(4.dp)
            .background(
                if (selected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background,
                shapes.medium
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center

    ) {
        Text(text, color = if (selected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground)
    }
}

private const val componentName = "Top Toggle Bar"

@Preview("$componentName (light)", showBackground = true)
@Preview("$componentName (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("$componentName (big font)", fontScale = 1.5f, showBackground = true)
@Preview("$componentName (large screen)", device = Devices.PIXEL_C)
@Composable
fun TogglePreview() {
    VoiceNote_androidTheme {
        TopToggleBar()
    }
}
