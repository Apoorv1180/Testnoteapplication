package com.example.testnoteapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.data.repository.NotesRepository

class ViewAllTypeNotesViewModel (application: Application): AndroidViewModel(application) {
    val allNotes = mutableListOf<AllNotesModel>()

    lateinit var notesRepository: NotesRepository

    init {
        notesRepository = NotesRepository(application )
    }

    fun getAllTypeNotes(): LiveData<List<AllNotesModel>> {
        return notesRepository.getAllTypeNotes()
    }
}
