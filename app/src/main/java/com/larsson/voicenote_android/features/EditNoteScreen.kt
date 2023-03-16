package com.larsson.voicenote_android.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.larsson.voicenote_android.ui.components.NoteView
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme
import com.larsson.voicenote_android.viewmodels.NotesViewModel

@Composable
fun EditNoteScreen(
    viewModel: NotesViewModel,
    navController: NavController
) {
    val selectedNote = viewModel.getSelectedNote()

    val title by remember { mutableStateOf(selectedNote.title) }
    val textContent by remember { mutableStateOf(selectedNote.txtContent) }
    val id by remember { mutableStateOf(selectedNote.id) }

    var textFieldValueContent by remember { mutableStateOf(textContent) }
    var textFieldValueTitle by remember { mutableStateOf(title) }

    NoteView(
        onBackClick = {
            Log.d("OnBackClick", "id: $id")
            viewModel.saveNote(textFieldValueTitle, textFieldValueContent, id)
            navController.popBackStack()
            println("id: $id")
        },
        textFieldValueContent = textFieldValueContent,
        textFieldValueTitle = textFieldValueTitle,
        onTextChangeTitle = { textFieldValueTitle = it },
        onTextChangeContent = { textFieldValueContent = it }
    )
}

@Preview(showBackground = true)
@Composable
fun EditPreview() {
    VoiceNote_androidTheme {
        EditNoteScreen(
            viewModel = NotesViewModel(),
            navController = rememberNavController()
        )
    }
}
