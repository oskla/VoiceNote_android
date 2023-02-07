package com.larsson.voicenote_android.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.larsson.voicenote_android.viewmodels.NotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModel = module {
    viewModel { NotesViewModel() }
}
