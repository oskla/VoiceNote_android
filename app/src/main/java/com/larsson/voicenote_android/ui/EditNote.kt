package com.larsson.voicenote_android.ui

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.larsson.voicenote_android.NotesViewModel
import com.larsson.voicenote_android.ui.theme.IBMFontFamily

import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme


// TODO - Create a Composable that can be used as template for both EditNote And NewNote

@Composable
fun EditNoteScreen(
    viewModel: NotesViewModel,
    navController: NavController,
    title: String,
    txtContent: String,
    id: String,
) {
    var textFieldValue by remember { mutableStateOf(txtContent) }
    var textFieldValue2 by remember { mutableStateOf(title) }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)) {

        BackHandler() {
            viewModel.saveNote(textFieldValue2, textFieldValue, id)
            navController.popBackStack()
        }
        TopAppBar(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.primary,
            modifier = Modifier.height(60.dp),
            title = {
                TextField(
                textStyle = MaterialTheme.typography.caption,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.background,
                    textColor = MaterialTheme.colors.primary,
                    cursorColor = Color.Black,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                    ),
                value = textFieldValue2,
                onValueChange = {
                    textFieldValue2 = it
                })
            },
            navigationIcon = {
                IconButton(onClick = {
                  viewModel.saveNote(textFieldValue2, textFieldValue, id)
                    navController.popBackStack()
                }) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Icon(
                            Icons.Filled.ArrowBackIosNew,
                            "arrow_back_ios_new",
                            tint = MaterialTheme.colors.primary
                        )
                        Text(text = "Back")
                        
                    }
                }
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


            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
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

