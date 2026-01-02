package com.larsson.voicenote_android.ui.components // ktlint-disable package-name

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.larsson.voicenote_android.data.repository.Note
import com.larsson.voicenote_android.data.repository.Recording

@Composable
fun NotesList(notes: State<List<Note>>, onNavigateToNote: (noteId: String) -> Unit, recordings: List<Recording>) {
    LazyColumn(modifier = Modifier.padding(horizontal = 12.dp), userScrollEnabled = true) {
        itemsIndexed(notes.value) { index, note ->
            NoteItem(
                title = note.title,
                txtContent = note.textContent,
                id = note.id,
                containsRecordings = recordings.any { it.noteId == note.id }, // TODO this should probably already be in the Note-object
                onClick = { onNavigateToNote(note.id) },
            )
            if (index != recordings.size - 1) {
                HorizontalDivider(color = MaterialTheme.colorScheme.onBackground.copy(0.1f))
            }
        }
    }
}

