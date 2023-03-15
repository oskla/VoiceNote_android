package com.larsson.voicenote_android.ui.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.larsson.voicenote_android.data.recordings
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

@Composable
fun RecordingsList() {
    LazyColumn(modifier = Modifier.padding(horizontal = 12.dp), userScrollEnabled = true) {
        itemsIndexed(recordings) { _, recording ->
            Box() {
                Log.d("NotesView", recording.title)
                RecordingItem(title = recording.title, date = recording.date, duration = recording.duration, onClick = {}, id = recording.id)
            }
        }
    }
}

private const val componentName = "Recordings List"

@Preview("$componentName (light)", showBackground = true)
@Preview("$componentName (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("$componentName (big font)", fontScale = 1.5f, showBackground = true)
@Preview("$componentName (large screen)", device = Devices.PIXEL_C)
@Composable
private fun PreviewComponent() {
    VoiceNote_androidTheme {
        RecordingsList()
    }
}
