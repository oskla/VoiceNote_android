package com.larsson.voicenote_android.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



class StoreNote(private val context: Context) {

    // To make sure theres onlu one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("NoteTitle")
        val NOTE_TITLE_KEY = stringPreferencesKey("note_title")
    }

    // To get note
    val getNoteTitle: Flow<String?> = context.dataStore.data
        .map { preferences ->
        preferences[NOTE_TITLE_KEY] ?: ""

        }

    // to save note
    suspend fun saveNoteTitle(title: String) {
        context.dataStore.edit { preferences ->
        preferences[NOTE_TITLE_KEY] = title

        }
    }
}