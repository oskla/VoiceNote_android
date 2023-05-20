package com.larsson.voicenote_android.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.larsson.voicenote_android.helpers.TimeFormatter
import com.larsson.voicenote_android.helpers.dateFormatter
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

@Composable
fun RecordingMenuItem(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.background,
    title: String,
    date: String,
    id: String,
    durationText: String,
    progress: Int? = null,
    isSelected: Boolean? = null,
    onClickContainer: () -> Unit,
    onClickPlay: () -> Unit,
    onClickPause: () -> Unit,
    isFirstItem: Boolean,
    isPlaying: Boolean,
    seekTo: (Float) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .background(Color.Transparent)
            .clickable(interactionSource = interactionSource, indication = null) {
                onClickContainer.invoke()
            },
    ) {
        if (isSelected == true) {
            RecordingMenuItemPlayer(
                title = title,
                date = dateFormatter(date),
                id = id,
                durationText = TimeFormatter().timeFormatter(durationText.toLong()),
                durationFloat = durationText.toFloat(),
                progress = progress ?: 0,
                color = color,
                isFirstItem = isFirstItem,
                onClickPlay = { onClickPlay() },
                onClickPause = { onClickPause() },
                isPlaying = isPlaying,
                seekTo = { position ->
                    seekTo(position)
                },
            )
        } else {
            RecordingMenuItemBase(
                title = title,
                date = dateFormatter(date),
                duration = if (durationText.isNotBlank()) TimeFormatter().timeFormatter(durationText.toLong()) else "",
                isFirstItem = isFirstItem,
            )
        }
    }
}

private const val componentName = "Recording Menu Item"

@Preview("$componentName (light)", showBackground = true)
@Preview("$componentName (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("$componentName (big font)", fontScale = 1.5f, showBackground = true)
@Preview("$componentName (large screen)", device = Devices.PIXEL_C)
@Composable
fun RecordingMenuItemPreview() {
    VoiceNote_androidTheme {
        Column(
            modifier = Modifier.padding(12.dp),
        ) {
            RecordingMenuItem(
                title = "hej",
                date = "2023-04-01",
                id = "5",
                durationText = "02:21",
                progress = 40,
                isSelected = true,
                onClickContainer = {
                },
                isFirstItem = false,
                onClickPlay = {},
                onClickPause = {},
                isPlaying = true,
                seekTo = {},
            )
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            RecordingMenuItem(
                title = "hej",
                date = "2023-04-01",
                id = "5",
                durationText = "02:21",
                progress = null,
                isSelected = false,
                onClickContainer = { },
                isFirstItem = true,
                onClickPlay = {},
                onClickPause = {},
                isPlaying = false,
                seekTo = {},
            )
        }
    }
}
