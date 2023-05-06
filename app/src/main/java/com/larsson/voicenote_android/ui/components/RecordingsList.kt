package com.larsson.voicenote_android.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.larsson.voicenote_android.data.entity.RecordingEntity
import com.larsson.voicenote_android.viewmodels.AudioPlayerViewModel

@Composable
fun RecordingsList(
    recordings: MutableState<List<RecordingEntity>>,
    onClickPlay: (String) -> Unit,
    onClickPause: () -> Unit,
    playerState: AudioPlayerViewModel.PlayerState,
    audioItems: List<AudioPlayerViewModel.AudioItem>,

) {
    var selectedRecordingId by remember { mutableStateOf<String?>(null) }

    LazyColumn(modifier = Modifier.padding(horizontal = 12.dp), userScrollEnabled = true) {
        itemsIndexed(recordings.value) { index, recording ->
            val isSelected = (recording.recordingId == selectedRecordingId) // Checks what recording is actually pressed.
            val mappedAudioItem = audioItems.find { it.recordingId == recording.recordingId } // Finds the audioItem that corresponds with the recording in the list

            Box() {
                RecordingMenuItem(
                    color = MaterialTheme.colorScheme.background,
                    title = recording.recordingTitle,
                    date = recording.recordingDate,
                    id = recording.recordingId,
                    duration = recording.recordingDuration,
                    isSelected = isSelected,
                    onClick = { selectedRecordingId = if (isSelected) null else recording.recordingId },
                    isFirstItem = false,
                    onClickPlay = {
                        onClickPlay(recording.recordingId)
                    },
                    onClickPause = onClickPause,
                    isPlaying = mappedAudioItem?.isPlaying ?: false,
                )
            }
            if (index != recordings.value.size - 1) {
                Divider(color = MaterialTheme.colorScheme.onBackground.copy(0.1f))
            }
        }
    }
}
