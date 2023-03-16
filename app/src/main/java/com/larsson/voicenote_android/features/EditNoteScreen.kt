package com.larsson.voicenote_android.ui

import android.util.Log
import androidx.compose.runtime.*
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
/*    title: String, // TODO - handle this from viewModel instead
    txtContent: String,
    id: String*/
) {
    val title by remember { mutableStateOf("") }
    val textContent by remember { mutableStateOf("") }
    val id by remember { mutableStateOf("") }

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
