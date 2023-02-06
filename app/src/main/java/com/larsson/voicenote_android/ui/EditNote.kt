package com.larsson.voicenote_android.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.larsson.voicenote_android.viewmodels.NotesViewModel

import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme


// TODO - Create a Composable that can be used as template for both EditNote And NewNote

@Composable
// Stateless reusable composable
fun NoteView(
    textFieldValueContent: String,
    textFieldValueTitle: String,
    onBackClick: () -> Unit,
    onTextChangeTitle: (String) -> Unit,
    onTextChangeContent: (String) -> Unit,

) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)
    ) {
        BackHandler(true, onBackClick)

        TopAppBar(
            onTextChangeTitle = onTextChangeTitle,
            value = textFieldValueTitle,
            onBackClick = onBackClick

        )
        TextField(
            textStyle = MaterialTheme.typography.body1,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                textColor = MaterialTheme.colors.primary,
                focusedIndicatorColor =  Color.Black, //hide the indicator
                cursorColor = Color.Black
            ),
            shape = shapes.large,
            value = textFieldValueContent,
            onValueChange =  onTextChangeContent,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun NewNoteScreen(
    notesViewModel: NotesViewModel,
    id: String,

) {

    var textFieldValueContent by remember { mutableStateOf("") }
    var textFieldValueTitle by remember { mutableStateOf("title") }


    NoteView(
        textFieldValueTitle = textFieldValueTitle,
        textFieldValueContent = textFieldValueContent,
        onBackClick = {

            notesViewModel.createNote(textFieldValueTitle, textFieldValueContent)
            notesViewModel.visibilityModifier(homeScreen = true)

        },
        onTextChangeTitle = { textFieldValueTitle = it },
        onTextChangeContent = { textFieldValueContent = it },

    )

}

@Composable
fun EditNoteScreen(
    viewModel: NotesViewModel,
    title: String,
    txtContent: String,
    id: String,
) {
    var textFieldValueContent by remember { mutableStateOf(txtContent) }
    var textFieldValueTitle by remember { mutableStateOf(title) }

    NoteView(
        onBackClick = {
            Log.d("OnBackClick", "id: $id")
            viewModel.saveNote(textFieldValueTitle, textFieldValueContent, id)
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
        EditNoteScreen(NotesViewModel(), "Title", "content", "1")
    }
}

