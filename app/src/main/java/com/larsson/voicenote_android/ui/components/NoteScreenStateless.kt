package com.larsson.voicenote_android.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.larsson.voicenote_android.ui.TopAppBar

// Stateless reusable composable
@Composable
fun NoteView(
    textFieldValueContent: String,
    textFieldValueTitle: String,
    onBackClick: () -> Unit,
    onTextChangeTitle: (String) -> Unit,
    onTextChangeContent: (String) -> Unit

) {
    Column(
        modifier = Modifier
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
                focusedIndicatorColor = Color.Black, // hide the indicator
                cursorColor = Color.Black
            ),
            shape = MaterialTheme.shapes.large,
            value = textFieldValueContent,
            onValueChange = onTextChangeContent,
            modifier = Modifier.fillMaxSize()
        )
    }
}