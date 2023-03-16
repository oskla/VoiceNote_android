package com.larsson.voicenote_android.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.larsson.voicenote_android.ui.TopAppBarCustom

// Stateless reusable composable
@Composable
fun NoteView(
    modifier: Modifier = Modifier,
    textFieldValueContent: String,
    textFieldValueTitle: String,
    onBackClick: () -> Unit,
    onTextChangeTitle: (String) -> Unit,
    onTextChangeContent: (String) -> Unit

) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            BackHandler(true, onBackClick)

            TopAppBarCustom(
                onTextChangeTitle = onTextChangeTitle,
                value = textFieldValueTitle,
                onBackClick = onBackClick

            )
            TextField(
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colorScheme.background,
                    textColor = MaterialTheme.colorScheme.onBackground,
                    focusedIndicatorColor = MaterialTheme.colorScheme.onBackground, // hide the indicator
                    cursorColor = MaterialTheme.colorScheme.onBackground
                ),
                shape = MaterialTheme.shapes.large,
                value = textFieldValueContent,
                onValueChange = onTextChangeContent,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
