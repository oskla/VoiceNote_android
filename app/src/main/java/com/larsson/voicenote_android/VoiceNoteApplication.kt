package com.larsson.voicenote_android

import android.app.Application
import com.larsson.voicenote_android.di.audioPlayerModule
import com.larsson.voicenote_android.di.daoModule
import com.larsson.voicenote_android.di.dataModule
import com.larsson.voicenote_android.di.recorder
import com.larsson.voicenote_android.di.repositoryModule
import com.larsson.voicenote_android.di.utils
import com.larsson.voicenote_android.di.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class VoiceNoteApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@VoiceNoteApplication)
            koin.loadModules(listOf(dataModule, utils, viewModel, repositoryModule, daoModule, recorder, audioPlayerModule))
        }
    }
}