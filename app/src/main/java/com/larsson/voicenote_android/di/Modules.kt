package com.larsson.voicenote_android.di

import com.larsson.voicenote_android.data.repository.NotesRepository
import com.larsson.voicenote_android.data.room.NoteDatabase
import com.larsson.voicenote_android.features.audiorecorder.Recorder
import com.larsson.voicenote_android.viewmodels.NotesViewModel
import com.larsson.voicenote_android.viewmodels.RecordingViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var dataModule = module {}

var viewModel = module {
    viewModel<NotesViewModel> { NotesViewModel(dbRepo = get()) }
    viewModel<RecordingViewModel> { RecordingViewModel(recorder = get()) }
}

val recorder = module {
    single { Recorder(context = androidContext()) }
}

var repositoryModule = module {
    single<NotesRepository> { (NotesRepository(noteDao = get())) }
}

var daoModule = module {
    single { NoteDatabase.getInstance(androidContext()).noteDao() }
}

var utils = module {}
