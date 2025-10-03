package com.larsson.voicenote_android.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.larsson.voicenote_android.data.repository.Recording

@Composable
fun RecordingsList(
    isMenu: Boolean,
    recordings: List<Recording>,
    onClickPlay: (String) -> Unit,
    onClickPause: () -> Unit,
    isPlaying: State<Boolean>,
    expandedContainerId: State<String>,
    onToggleExpandContainer: (recordingId: String) -> Unit,
    currentPosition: State<Long>,
    seekTo: (Float) -> Unit,
    onSeekingFinished: () -> Unit,
) {
    val horizontalPadding = if (isMenu) 0.dp else 12.dp

    LazyColumn(modifier = Modifier.padding(horizontal = horizontalPadding), userScrollEnabled = true) {
        itemsIndexed(recordings) { index, recording ->
            val isExpanded = expandedContainerId.value == recording.id
            Box {
                RecordingMenuItem(
                    color = MaterialTheme.colorScheme.background,
                    title = recording.userTitle ?: "Recording ${recording.recordingNumber}",
                    date = recording.date,
                    id = recording.id,
                    durationText = recording.duration,
                    onToggleExpandContainer = { onToggleExpandContainer(recording.id) },
                    isFirstItem = if (isMenu) index < 1 else false, // top item will have rounded corners in menu component
                    onClickPlay = { onClickPlay(recording.id) },
                    onClickPause = onClickPause,
                    isPlaying = isPlaying,
                    isExpanded = isExpanded,
                    progress = currentPosition,
                    seekTo = seekTo,
                    onSeekingFinished = onSeekingFinished,
                )
            }
            if (index != recordings.size - 1) {
                if (isMenu) {
                    Divider(color = Color.Transparent, thickness = 2.dp)
                } else {
                    Divider(color = MaterialTheme.colorScheme.onBackground.copy(0.1f))
                }
            }
        }
    }
}
