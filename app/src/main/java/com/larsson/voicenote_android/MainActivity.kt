package com.larsson.voicenote_android
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.larsson.voicenote_android.features.HomeScreen
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VoiceNote_androidTheme {
        VNApp(notesViewModel = NotesViewModel())
    }
}
