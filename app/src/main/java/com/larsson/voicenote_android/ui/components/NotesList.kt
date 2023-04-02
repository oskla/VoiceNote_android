package com.larsson.voicenote_android.ui // ktlint-disable package-name

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.larsson.voicenote_android.data.entity.NoteEntity
import com.larsson.voicenote_android.data.entity.RecordingEntity
import com.larsson.voicenote_android.navigation.Screen
import com.larsson.voicenote_android.ui.components.NoteItem

@Composable
fun NotesList(notes: MutableState<List<NoteEntity>>, navController: NavController, recordings: MutableState<List<RecordingEntity>>) {
    LazyColumn(modifier = Modifier.padding(horizontal = 12.dp), userScrollEnabled = true) {
        itemsIndexed(notes.value) { _, note ->

            Box() {
                Log.d("NotesView", note.noteTitle)
                NoteItem(
                    title = note.noteTitle,
                    txtContent = note.noteTxtContent,
                    id = note.noteId.toString(),
                    containsRecordings = recordings.value.any { it.noteId == note.noteId },
                    onClick = {
                        navController.navigate("${Screen.EditNote.route}/${note.noteId}")
                    },
                )
            }
        }
    }
}
