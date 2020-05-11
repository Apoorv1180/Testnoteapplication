package com.example.testnoteapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testnoteapplication.data.db.NotesAppDatabase
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.data.repository.NotesRepository

class AllSubscriptionViewModel (application: Application): AndroidViewModel(application){

    private val notesRepository: NotesRepository

    init {
        val notesDao = NotesAppDatabase.getDatabase(application, viewModelScope).notesDao()
        notesRepository = NotesRepository(notesDao)
    }

    fun getAllNotes(noteType: String): LiveData<List<AllNotesModel>> {
        return notesRepository.getAllNotes(noteType);
    }
}
