package com.larsson.voicenote_android.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.larsson.voicenote_android.clicklisteners.UiAudioPlayerClickListener
import com.larsson.voicenote_android.clicklisteners.previewAudioPlayerClickListener
import com.larsson.voicenote_android.helpers.TimeFormatter
import com.larsson.voicenote_android.helpers.dateFormatter
import com.larsson.voicenote_android.ui.theme.VoiceNoteTheme
import java.time.LocalDateTime

@Composable
internal fun RecordingMenuItem(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.background,
    title: String,
    date: String,
    id: String,
    durationText: String,
    progress: State<Long>,
    onClickPlay: () -> Unit,
    onClickPause: () -> Unit,
    isFirstItem: Boolean,
    isPlaying: State<Boolean>,
    isExpanded: Boolean,
    onClickDelete: () -> Unit,
    uiAudioPlayerClickListener: UiAudioPlayerClickListener
) {
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = modifier
            .background(Color.Transparent),
    ) {
        if (isExpanded) {
            RecordingMenuItemPlayer(
                title = title,
                date = dateFormatter(date),
                id = id,
                durationText = TimeFormatter().timeFormatter(durationText.toLong()),
                durationFloat = durationText.toFloat(),
                progress = progress,
                color = color,
                isFirstItem = isFirstItem,
                onClickPlay = onClickPlay,
                onClickPause = onClickPause,
                isPlaying = isPlaying,
                uiAudioPlayerClickListener = uiAudioPlayerClickListener,
                onClickDelete = onClickDelete
            )
        } else {
            RecordingMenuItemCollapsed(
                id = id,
                title = title,
                date = dateFormatter(date),
                duration = if (durationText.isNotBlank()) TimeFormatter().timeFormatter(durationText.toLong()) else "",
                isFirstItem = isFirstItem,
                onClickExpandContainer = { uiAudioPlayerClickListener.onCollapsedContainerClicked(it) }
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
    VoiceNoteTheme {
        Column(
            modifier = Modifier.padding(12.dp),
        ) {
            RecordingMenuItem(
                title = "hej",
                date = LocalDateTime.now().toString(),
                id = "5",
                durationText = "240",
                progress = remember { mutableLongStateOf(40) },
                isFirstItem = false,
                onClickPlay = {},
                onClickPause = {},
                isPlaying = remember { mutableStateOf(true) },
                isExpanded = true,
                onClickDelete = {},
                uiAudioPlayerClickListener = previewAudioPlayerClickListener
            )
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            RecordingMenuItem(
                title = "hej",
                date = LocalDateTime.now().toString(),
                id = "5",
                durationText = "1221",
                progress = remember { mutableLongStateOf(0L) },
                isFirstItem = true,
                onClickPlay = {},
                onClickPause = {},
                isPlaying = remember { mutableStateOf(false) },
                isExpanded = false,
                onClickDelete = {},
                uiAudioPlayerClickListener = previewAudioPlayerClickListener
            )
        }
    }
}
