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
import com.larsson.voicenote_android.clicklisteners.UiAudioPlayerClickListener
import com.larsson.voicenote_android.data.repository.Recording

@Composable
internal fun RecordingsList(
    isMenu: Boolean,
    recordings: List<Recording>,
    isPlaying: State<Boolean>,
    expandedContainerId: State<String>,
    currentPosition: State<Long>,
    uiAudioPlayerClickListener: UiAudioPlayerClickListener
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
                    onToggleExpandContainer = { uiAudioPlayerClickListener.onToggleExpandContainer(recording.id) },
                    isFirstItem = if (isMenu) index < 1 else false, // top item will have rounded corners in menu component
                    onClickPlay = { uiAudioPlayerClickListener.onClickPlay(recording.id) },
                    onClickDelete = { uiAudioPlayerClickListener.onClickDelete(recording.id) },
                    onClickPause = { uiAudioPlayerClickListener.onClickPause() },
                    isPlaying = isPlaying,
                    isExpanded = isExpanded,
                    progress = currentPosition,
                    uiAudioPlayerClickListener = uiAudioPlayerClickListener,
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
