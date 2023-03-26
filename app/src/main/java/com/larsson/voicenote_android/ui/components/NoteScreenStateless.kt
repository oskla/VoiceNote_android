package com.larsson.voicenote_android.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.larsson.voicenote_android.ui.TopAppBarCustom

// Stateless reusable composable
@Composable
fun NoteView(
    modifier: Modifier = Modifier,
    textFieldValueContent: String,
    textFieldValueTitle: String,
    onBackClick: () -> Unit,
    onTextChangeTitle: (String) -> Unit,
    onTextChangeContent: (String) -> Unit,

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        BackHandler(true, onBackClick)

        TopAppBarCustom(
            onTextChangeTitle = onTextChangeTitle,
            value = textFieldValueTitle,
            onBackClick = onBackClick,

        )
        TextField(
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colorScheme.background,
                textColor = MaterialTheme.colorScheme.onBackground,
                focusedIndicatorColor = Color.Transparent, // hide the underline
                cursorColor = MaterialTheme.colorScheme.onBackground,
                unfocusedIndicatorColor = Color.Transparent, // hide the underline
                disabledIndicatorColor = Color.Transparent, // hide the underline
            ),
            shape = MaterialTheme.shapes.large,
            value = textFieldValueContent,
            onValueChange = onTextChangeContent,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
