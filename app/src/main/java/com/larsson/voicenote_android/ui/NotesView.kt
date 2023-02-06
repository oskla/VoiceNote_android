package com.larsson.voicenote_android.ui // ktlint-disable package-name

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.larsson.voicenote_android.data.notes
import com.larsson.voicenote_android.ui.theme.SpaceGroteskFontFamily
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

@Composable
fun NotesList(
    // getAllNotes: () -> List<Note>
) {
    // val notes = getAllNotes()
    val notes = notes
    LazyColumn(modifier = Modifier.padding(horizontal = 12.dp)) {
        itemsIndexed(notes) { index, note ->
            Box() {
                Log.d("NotesView", note.title)
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
    id: String
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

/*@Preview(showBackground = true)
@Composable
fun NotesPreview() {
    VoiceNote_androidTheme {
        // NotesList(rememberNavController())
        // NoteItem(navController = rememberNavController(), title = "hej", txtContent = "content ara asf  asf  asf as  asf as f ", id = "4")
    }
}*/
@Preview("Profile screen", showBackground = true)
@Preview("Profile screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Profile screen (big font)", fontScale = 1.5f)
@Preview("Profile screen (large screen)", device = Devices.PIXEL_C)
@Composable
fun NotesPreview() {
    VoiceNote_androidTheme {
        NotesList()
        // NoteItem(title = "hej", txtContent = "content", id = "101")
        // Test()
    }
}
