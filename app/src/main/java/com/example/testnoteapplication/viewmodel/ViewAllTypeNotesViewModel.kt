package com.example.testnoteapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.testnoteapplication.data.db.NotesAppDatabase
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.data.repository.NotesRepository

class ViewAllTypeNotesViewModel (application: Application): AndroidViewModel(application) {

    private lateinit var notesRepository: NotesRepository

    init {
        val notesDao = NotesAppDatabase.getDatabase(application, viewModelScope).notesDao()
        notesRepository = NotesRepository(notesDao)
    }

    fun getAllTypeNotes() : LiveData<List<AllNotesModel>> {
        return notesRepository.getAllTypeNotes()
    }
}
