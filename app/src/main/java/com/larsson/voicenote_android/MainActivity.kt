package com.larsson.voicenote_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.larsson.voicenote_android.components.SetupNavGraph
import com.larsson.voicenote_android.data.Note
import com.larsson.voicenote_android.ui.NotesList

import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

class MainActivity : ComponentActivity() {
    private val notesViewModel : NotesViewModel by viewModels()
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
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
    val navController = rememberNavController()

    Column() {
        SetupNavGraph(navController = navController, notesViewModel)
    }
}

@Composable
fun HomeScreen(
    navController: NavController,
    notesViewModel: NotesViewModel,

    ) {
    val getAllNotes = notesViewModel.getAllNotes()

    Column() {

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier,
        ) {
            Box(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                NotesList(navController, getAllNotes)
            }
            BottomBox()
        }


    }

}

@Composable
fun BottomBox() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colors.secondary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

            ) {

            Box(modifier = Modifier.padding(end = 6.dp)) {
                Icon(
                    Icons.Filled.Add,
                    "add",
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp),
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
                        .width(30.dp),
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
        // HomeScreen(null)
        BottomBox()
    }
}