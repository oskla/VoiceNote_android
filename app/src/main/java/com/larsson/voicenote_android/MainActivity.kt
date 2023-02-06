package com.larsson.voicenote_android
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.larsson.voicenote_android.components.TopToggleBar
import com.larsson.voicenote_android.data.getUUID
import com.larsson.voicenote_android.ui.NewNoteScreen
import com.larsson.voicenote_android.ui.NotesList
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme
import com.larsson.voicenote_android.viewmodels.NotesViewModel

// TODO - Create reusable bottomBox
// TODO - Fill max height new note view

class MainActivity : ComponentActivity() {
    private val notesViewModel: NotesViewModel by viewModels()
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // notesViewModel.addNotes()
            VoiceNote_androidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    VNApp(notesViewModel)
                }
            }
        }
    }
}

@Composable
fun VNApp(notesViewModel: NotesViewModel) {
    Column() {
        HomeScreen(notesViewModel)
    }
}

@Composable
fun HomeScreen(
    notesViewModel: NotesViewModel
) {
    val getAllNotes = notesViewModel.getAllNotes()
    val newNoteId = getUUID()

    val newNoteVisible = notesViewModel.newNoteVisible
    val notesListVisible = notesViewModel.notesListVisible
    val bottomBoxVisible = notesViewModel.bottomBoxVisible
    val topToggleBar = notesViewModel.topToggleBarVisible

    Column() {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            if (topToggleBar) {
                TopToggleBar()
            }
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                if (newNoteVisible) {
                    NewNoteScreen(notesViewModel, newNoteId)
                }
                if (notesListVisible) {
                    NotesList()
                }
            }
            if (bottomBoxVisible) {
                BottomBox(newNoteId, notesViewModel)
            }
        }
    }
}

@Composable
fun BottomBox(
    newNoteId: String,
    notesViewModel: NotesViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colors.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    notesViewModel.visibilityModifier(homeScreen = false)
                }
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 6.dp)
            ) {
                Icon(
                    Icons.Filled.Add,
                    "add",
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp)
                )
            }
            Text(text = "New note", color = MaterialTheme.colors.onSecondary, fontSize = 14.sp)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.padding(end = 6.dp)) {
                Icon(
                    imageVector = Icons.Filled.RadioButtonChecked,
                    "radio_button_checked",
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                )
            }
            Text(
                text = "New recording",
                color = MaterialTheme.colors.onSecondary,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VoiceNote_androidTheme {
        HomeScreen(notesViewModel = NotesViewModel())
        // BottomBox(rememberNavController(), "")
    }
}
