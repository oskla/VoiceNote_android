package com.larsson.voicenote_android.ui.components // ktlint-disable package-name

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.larsson.voicenote_android.data.entity.NoteEntity
import com.larsson.voicenote_android.data.repository.Recording
import com.larsson.voicenote_android.navigation.Screen

@Composable
fun NotesList(notes: State<List<NoteEntity>>, navController: NavController, recordings: List<Recording>) {
    LazyColumn(modifier = Modifier.padding(horizontal = 12.dp), userScrollEnabled = true) {
        itemsIndexed(notes.value) { _, note ->

            Box() {
                NoteItem(
                    title = note.noteTitle,
                    txtContent = note.noteTxtContent,
                    id = note.noteId,
                    containsRecordings = recordings.any { it.noteId == note.noteId },
                    onClick = {
                        navController.navigate("${Screen.EditNote.route}/${note.noteId}")
                    },
                )
            }
        }
    }
}
