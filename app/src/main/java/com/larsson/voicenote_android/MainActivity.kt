package com.larsson.voicenote_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.larsson.voicenote_android.di.audioPlayerModule
import com.larsson.voicenote_android.di.daoModule
import com.larsson.voicenote_android.di.dataModule
import com.larsson.voicenote_android.di.recorder
import com.larsson.voicenote_android.di.repositoryModule
import com.larsson.voicenote_android.di.utils
import com.larsson.voicenote_android.di.viewModel
import com.larsson.voicenote_android.navigation.NavGraph
import com.larsson.voicenote_android.ui.theme.VoiceNoteTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO), 0)

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            koin.loadModules(listOf(dataModule, utils, viewModel, repositoryModule, daoModule, recorder, audioPlayerModule))
        }

        setContent {
            VoiceNoteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    NavGraph()
                }
            }
        }
    }
}
