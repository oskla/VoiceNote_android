package com.larsson.voicenote_android.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.larsson.voicenote_android.helpers.dateFormatter
import java.time.LocalDateTime

// Stateless reusable composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteView(
    modifier: Modifier = Modifier,
    textFieldValueContent: String,
    textFieldValueTitle: String,
    onBackClick: () -> Unit,
    onMoreClick: () -> Unit,
    onTextChangeTitle: (String) -> Unit,
    onTextChangeContent: (String) -> Unit,
    date: String = dateFormatter(LocalDateTime.now().toString()),
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
    ) {
        BackHandler(true, onBackClick)

        TopAppBarCustom(
            onTextChangeTitle = onTextChangeTitle,
            value = textFieldValueTitle,
            onBackClick = onBackClick,
            onMoreClick = onMoreClick,
        )

        Text(
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(0.3f),
            textAlign = TextAlign.Center,
            text = date,
            modifier = Modifier.fillMaxWidth().align(CenterHorizontally),
        )

        TextField(
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                cursorColor = Color.Black,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                selectionColors = TextSelectionColors(handleColor = MaterialTheme.colorScheme.onBackground, backgroundColor = MaterialTheme.colorScheme.onBackground.copy(0.3f)),
            ),
            shape = MaterialTheme.shapes.large,
            value = textFieldValueContent,
            onValueChange = onTextChangeContent,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
