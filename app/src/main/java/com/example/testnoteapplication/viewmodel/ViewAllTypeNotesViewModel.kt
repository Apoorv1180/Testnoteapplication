package com.example.testnoteapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.data.repository.NotesRepository

class ViewAllTypeNotesViewModel : ViewModel() {
    val allNotes = mutableListOf<AllNotesModel>()

    lateinit var notesRepository: NotesRepository

    init {
        notesRepository = NotesRepository()
    }

    fun getAllTypeNotes(): LiveData<List<AllNotesModel>> {
        return notesRepository.getAllTypeNotes()
    }
}
