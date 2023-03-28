package com.larsson.voicenote_android.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.larsson.voicenote_android.data.recordings
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

@Composable
fun RecordingMenu(
    modifier: Modifier = Modifier,
    noteId: String,
) {
    var selectedRecordingId by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.secondary),
        userScrollEnabled = true,
    ) {
        itemsIndexed(recordings) { index, recording ->
            val isSelected =
                (recording.id == selectedRecordingId) // Checks what recording is actually pressed.
            if (noteId == recording.belongsToNoteId) { // Checks if these recordings belongs to this specific note.
                RecordingMenuItem(
                    title = recording.title,
                    date = recording.date,
                    duration = recording.duration,
                    id = recording.id,
                    progress = 0.6f, // TODO should come from viewmodel,
                    isSelected = isSelected,
                    onClick = {
                        selectedRecordingId = if (isSelected) null else recording.id
                    },
                )
                Divider(color = MaterialTheme.colorScheme.background)
            }
        }
    }
}

private const val componentName = "Recording Menu Item Player"

@Preview("$componentName (light)", showBackground = true)
@Preview("$componentName (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("$componentName (big font)", fontScale = 1.5f, showBackground = true)
@Preview("$componentName (large screen)", device = Devices.PIXEL_C)
@Composable
fun RecordingMenuPreview() {
    VoiceNote_androidTheme {
        RecordingMenu(noteId = "2")
    }
}
