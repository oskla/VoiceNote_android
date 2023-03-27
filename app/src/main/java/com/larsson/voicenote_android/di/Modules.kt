package com.larsson.voicenote_android.di

import com.larsson.voicenote_android.data.repository.NotesRepository
import com.larsson.voicenote_android.data.room.NoteDatabase
import com.larsson.voicenote_android.viewmodels.NotesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var dataModule = module {}

var viewModel = module {
    viewModel<NotesViewModel> { NotesViewModel(dbRepo = get()) }
}

var repositoryModule = module {
    single<NotesRepository> { (NotesRepository(noteDao = get())) }
}

var daoModule = module {
    single { NoteDatabase.getInstance(androidContext()).dao() }
}

var utils = module {}
