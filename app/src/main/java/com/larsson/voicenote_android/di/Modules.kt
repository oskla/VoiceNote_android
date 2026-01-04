package com.larsson.voicenote_android.di

import com.larsson.voicenote_android.audioplayer.AudioPlayer
import com.larsson.voicenote_android.audioplayer.LocalAudioPlayer
import com.larsson.voicenote_android.data.repository.NotesRepository
import com.larsson.voicenote_android.data.repository.RecordingsRepository
import com.larsson.voicenote_android.data.room.NoteDatabase
import com.larsson.voicenote_android.features.audiorecorder.Recorder
import com.larsson.voicenote_android.viewmodels.AudioPlayerViewModel
import com.larsson.voicenote_android.features.editnotescreen.EditNoteViewModel
import com.larsson.voicenote_android.features.homescreen.HomeViewModel
import com.larsson.voicenote_android.features.audiorecorder.RecordingBottomSheetViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var dataModule = module {}

var viewModel = module {
    viewModel<RecordingBottomSheetViewModel> { RecordingBottomSheetViewModel(recordingsRepo = get()) }
    viewModel<AudioPlayerViewModel> {
        AudioPlayerViewModel(
            audioPlayer = get(),
            recordingRepository = get()
        )
    }
    viewModel<HomeViewModel> { HomeViewModel(notesRepository = get(), recordingsRepository = get()) }
    viewModel<EditNoteViewModel> { EditNoteViewModel(recordingsRepository = get(), notesRepository = get()) }
}

val recorder = module {
    single { Recorder(context = androidContext()) }
}

val audioPlayerModule = module {
    single<AudioPlayer> { LocalAudioPlayer(androidContext()) }
}

var repositoryModule = module {
    single<NotesRepository> { NotesRepository(noteDao = get()) }
    single<RecordingsRepository> { RecordingsRepository(recordingDao = get(), recorder = get()) }
}

var daoModule = module {
    single { NoteDatabase.getInstance(androidContext()).noteDao() }
    single { NoteDatabase.getInstance(androidContext()).recordingDao() }
}

var utils = module {}
