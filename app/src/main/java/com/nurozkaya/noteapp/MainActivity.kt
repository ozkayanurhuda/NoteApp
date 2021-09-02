package com.nurozkaya.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.nurozkaya.noteapp.database.NoteDatabase
import com.nurozkaya.noteapp.databinding.ActivityMainBinding
import com.nurozkaya.noteapp.repository.NoteRepository
import com.nurozkaya.noteapp.viewmodel.NoteViewModel
import com.nurozkaya.noteapp.viewmodel.NoteViewModelProvider


class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        setUpViewModel()
    }

    private fun setUpViewModel(){
        val noteRepository=NoteRepository(NoteDatabase(this))

        val viewModelProvider=NoteViewModelProvider(application, noteRepository)

        //owner and factory as a param
        noteViewModel=ViewModelProvider(this, viewModelProvider).get(NoteViewModel::class.java)
    }
}