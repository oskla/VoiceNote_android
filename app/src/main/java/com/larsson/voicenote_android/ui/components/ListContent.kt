package com.larsson.voicenote_android.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.larsson.voicenote_android.data.Note
import com.larsson.voicenote_android.ui.NotesList

enum class ListVariant {
    NOTES,
    RECORDINGS,
}

@Composable
fun ListContent(
    listVariant: ListVariant,
    notes: List<Note>
) {
    when (listVariant) {
        ListVariant.NOTES -> { NotesList(notes = notes) }
        ListVariant.RECORDINGS -> { RecordingsList() }
    }
}
