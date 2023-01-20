package com.larsson.voicenote_android.ui


import android.util.MutableBoolean
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.larsson.voicenote_android.Screen
import com.larsson.voicenote_android.data.Note
import com.larsson.voicenote_android.data.getUUID
import com.larsson.voicenote_android.ui.theme.IBMFontFamily
import com.larsson.voicenote_android.ui.theme.SpaceGroteskFontFamily
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme



@Composable
fun NotesList(
    getAllNotes: () -> List<Note>,
) {

    val notes = getAllNotes()

    LazyColumn(modifier = Modifier.padding(horizontal = 12.dp)) {
            itemsIndexed(notes) { index, note ->
                Box() {
                    NoteItem(note.title, note.txtContent, note.id)
                }
            }

    }
}

@Composable
fun NoteItem(
   // navController: NavController,
    title: String,
    txtContent: String,
    id: String,
) {

    Card(
        backgroundColor = Color.Transparent,
        modifier = Modifier.wrapContentSize(),
        elevation = 0.dp
    ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {

               // editNoteVisable.value = true
            }
        ) {
        Text(
            text = title,
            color = MaterialTheme.colors.primary,
            fontSize = 14.sp,
            fontFamily = SpaceGroteskFontFamily,
            fontWeight = FontWeight.W700
        )
        Text(
            text = txtContent,
            color = MaterialTheme.colors.primary,
            fontFamily = SpaceGroteskFontFamily,
            fontWeight = FontWeight.Thin,
            style = LocalTextStyle.current.copy(lineHeight = 15.sp),
            fontSize = 14.sp,
            maxLines = 2
        )

    }
    }
}

@Preview(showBackground = true)
@Composable
fun NotesPreview() {
    VoiceNote_androidTheme {
       // NotesList(rememberNavController())
       // NoteItem(navController = rememberNavController(), title = "hej", txtContent = "content ara asf  asf  asf as  asf as f ", id = "4")
    }
}
