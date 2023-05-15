package com.larsson.voicenote_android.di

import com.larsson.voicenote_android.MediaManager
import com.larsson.voicenote_android.data.repository.NotesRepository
import com.larsson.voicenote_android.data.repository.RecordingsRepository
import com.larsson.voicenote_android.data.room.NoteDatabase
import com.larsson.voicenote_android.features.audiorecorder.Recorder
import com.larsson.voicenote_android.viewmodels.AudioPlayerViewModel
import com.larsson.voicenote_android.viewmodels.NotesViewModel
import com.larsson.voicenote_android.viewmodels.RecordingViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var dataModule = module {}

var viewModel = module {
    viewModel<NotesViewModel> { NotesViewModel(dbRepo = get()) }
    viewModel<RecordingViewModel> { RecordingViewModel(recorder = get(), recordingsRepo = get()) }
    viewModel<AudioPlayerViewModel> { AudioPlayerViewModel(mediaManager = get()) }
}

val recorder = module {
    single { Recorder(context = androidContext()) }
}

val audioPlayerModule = module {
    single { MediaManager(androidContext()) }
}

var repositoryModule = module {
    single<NotesRepository> { (NotesRepository(noteDao = get())) }
    single<RecordingsRepository> { RecordingsRepository(recordingDao = get()) }
}

var daoModule = module {
    single { NoteDatabase.getInstance(androidContext()).noteDao() }
    single { NoteDatabase.getInstance(androidContext()).recordingDao() }
}

var utils = module {}
