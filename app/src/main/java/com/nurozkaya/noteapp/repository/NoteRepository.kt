package com.nurozkaya.noteapp.repository

import com.nurozkaya.noteapp.database.NoteDatabase
import com.nurozkaya.noteapp.model.Note

class NoteRepository(private val db: NoteDatabase) {

    suspend fun addNote(note: Note) =db.getNoteDao().insertNote(note)
    suspend fun updateNote(note:Note)= db.getNoteDao().updateNote(note)
    suspend fun deleteNote(note: Note)=db.getNoteDao().deleteNote(note)
    fun getAllNotes() =db.getNoteDao().getAllNotes()
    fun searchNotes(query:String?) = db.getNoteDao().searchNote(query)
}