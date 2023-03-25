package com.larsson.voicenote_android.ui // ktlint-disable package-name

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.larsson.voicenote_android.data.Note
import com.larsson.voicenote_android.navigation.Screen
import com.larsson.voicenote_android.ui.components.NoteItem
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme
import com.larsson.voicenote_android.viewmodels.NotesViewModel

@Composable
fun NotesList(notes: List<Note>, navController: NavController, notesViewModel: NotesViewModel) {
    LazyColumn(modifier = Modifier.padding(horizontal = 12.dp), userScrollEnabled = true) {
        itemsIndexed(notes) { _, note ->
            Box() {
                Log.d("NotesView", note.title)
                NoteItem(note.title, note.txtContent, note.id, onClick = {
                    navController.navigate(Screen.EditNote.route)
                    notesViewModel.selectNoteById(id = note.id)
                    //notesViewModel.saveNote(title = note.title, txtContent = note.txtContent, id = note.id)
                })
            }
        }
    }
}

@Preview("Notes view", showBackground = true)
@Preview("Notes view (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Notes view (big font)", fontScale = 1.5f)
@Preview("Notes view (large screen)", device = Devices.PIXEL_C)
@Composable
fun NotesPreview() {
    VoiceNote_androidTheme {
        NotesList(
            notes = SnapshotStateList<Note>(),
            navController = rememberNavController(),
            notesViewModel = NotesViewModel()
        )
    }
}
