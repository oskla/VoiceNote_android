package com.larsson.voicenote_android.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.larsson.voicenote_android.PlayerState
import com.larsson.voicenote_android.data.entity.RecordingEntity

@Composable
fun RecordingsList(
    isMenu: Boolean,
    recordings: List<RecordingEntity>,
    onClickPlay: (String) -> Unit,
    onClickPause: () -> Unit,
    playerState: PlayerState,
    onClickContainer: () -> Unit,
    currentPosition: Int,
    seekTo: (Float) -> Unit,
) {
    var selectedRecordingId by remember { mutableStateOf<String?>(null) }
    val horizontalPadding = if (isMenu) 0.dp else 12.dp

    LazyColumn(modifier = Modifier.padding(horizontal = horizontalPadding), userScrollEnabled = true) {
        itemsIndexed(recordings) { index, recording ->
            val isSelected = (recording.recordingId == selectedRecordingId) // Checks what recording is actually pressed.

            Box() {
                RecordingMenuItem(
                    color = MaterialTheme.colorScheme.background,
                    title = recording.recordingTitle,
                    date = recording.recordingDate,
                    id = recording.recordingId,
                    durationText = recording.recordingDuration,
                    isSelected = isSelected,
                    onClickContainer = {
                        selectedRecordingId = if (isSelected) null else recording.recordingId
                        onClickContainer.invoke() // calls on event that resets player
                    },
                    isFirstItem = if (isMenu) index < 1 else false, // top item will have rounded corners in menu component
                    onClickPlay = { onClickPlay(recording.recordingId) },
                    onClickPause = onClickPause,
                    isPlaying = playerState is PlayerState.Playing,
                    progress = currentPosition,
                    seekTo = { position ->
                        seekTo(position)
                    },
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
