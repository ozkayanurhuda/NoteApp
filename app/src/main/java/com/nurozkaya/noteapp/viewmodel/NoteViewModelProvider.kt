package com.nurozkaya.noteapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nurozkaya.noteapp.repository.NoteRepository

//To use viewModel with arguments

class NoteViewModelProvider(val app: Application, private val noteRepository: NoteRepository)
    :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteViewModel(app, noteRepository) as T
    }
}