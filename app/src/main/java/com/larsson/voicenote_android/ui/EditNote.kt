package com.larsson.voicenote_android.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.larsson.voicenote_android.NotesViewModel

import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme


// TODO - Create a Composable that can be used as template for both EditNote And NewNote

@Composable
// Stateless reusable composable
fun NoteView(
    textFieldValueContent: String,
    textFieldValueTitle: String,
    onBackClick: () -> Unit,
    onTextChangeTitle: (String) -> Unit,
    onTextChangeContent: (String) -> Unit
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


            value = textFieldValueContent,
            onValueChange =  onTextChangeContent,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun NewNoteScreen(
    viewModel: NotesViewModel,
    navController: NavController,
    id: String
) {
    var textFieldValueContent by remember { mutableStateOf("") }
    var textFieldValueTitle by remember { mutableStateOf("title") }

    NoteView(
        textFieldValueTitle = textFieldValueTitle,
        textFieldValueContent = textFieldValueContent,
        onBackClick = {
            viewModel.saveNote(textFieldValueTitle, textFieldValueContent, id)
                navController.popBackStack()
        },
        onTextChangeTitle = { textFieldValueTitle = it },
        onTextChangeContent = { textFieldValueContent = it }
    )

}

@Composable
fun EditNoteScreen(
    viewModel: NotesViewModel,
    navController: NavController,
    title: String,
    txtContent: String,
    id: String,
) {
    var textFieldValueContent by remember { mutableStateOf(txtContent) }
    var textFieldValueTitle by remember { mutableStateOf(title) }

    NoteView(
        onBackClick = {
            viewModel.saveNote(textFieldValueTitle, textFieldValueContent, id)
            navController.popBackStack() },
        textFieldValueContent = textFieldValueContent,
        textFieldValueTitle = textFieldValueTitle,
        onTextChangeTitle = { textFieldValueTitle = it },
        onTextChangeContent = { textFieldValueContent = it }
    )
}
@Composable
fun EditNoteScreen2(
    viewModel: NotesViewModel,
    navController: NavController,
    title: String,
    txtContent: String,
    id: String,
) {
    var textFieldValueContent by remember { mutableStateOf(txtContent) }
    var textFieldValueTitle by remember { mutableStateOf(title) }

    
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)) {

        BackHandler() {
            viewModel.saveNote(textFieldValueTitle, textFieldValueContent, id)
            navController.popBackStack()
        }
        TopAppBar(
            onTextChangeTitle = { textFieldValueTitle = it },
            value = textFieldValueTitle,
            onBackClick = {
                viewModel.saveNote(textFieldValueTitle, textFieldValueContent, id)
                navController.popBackStack()
            }
     )

        TextField(
            textStyle = MaterialTheme.typography.body1,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                textColor = MaterialTheme.colors.primary,
                focusedIndicatorColor =  Color.Black, //hide the indicator
                cursorColor = Color.Black
            ),


            value = textFieldValueContent,
            onValueChange = {
                textFieldValueContent = it
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditPreview() {
    VoiceNote_androidTheme {
        EditNoteScreen(NotesViewModel(), rememberNavController(), "Title", "content", "1")
    }
}

