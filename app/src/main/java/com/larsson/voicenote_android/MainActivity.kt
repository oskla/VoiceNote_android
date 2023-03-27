package com.larsson.voicenote_android
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.larsson.voicenote_android.di.daoModule
import com.larsson.voicenote_android.di.dataModule
import com.larsson.voicenote_android.di.repositoryModule
import com.larsson.voicenote_android.di.utils
import com.larsson.voicenote_android.di.viewModel
import com.larsson.voicenote_android.navigation.NavGraph
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme
import com.larsson.voicenote_android.viewmodels.NotesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

// TODO - Create reusable bottomBox
// TODO - Fill max height new note view

class MainActivity : ComponentActivity() {
    private val notesViewModel: NotesViewModel by viewModels()
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            koin.loadModules(listOf(dataModule, utils, viewModel, repositoryModule, daoModule))
        }

        setContent {
            VoiceNote_androidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    VNApp()
                }
            }
        }
    }
}

@Composable
fun VNApp() {
    Column() {
        NavGraph()
    }
}
