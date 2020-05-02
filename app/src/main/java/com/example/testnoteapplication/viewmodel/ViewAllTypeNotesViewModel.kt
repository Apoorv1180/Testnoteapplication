package com.example.testnoteapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.data.repository.NotesRepository

class ViewAllTypeNotesViewModel : ViewModel() {
    val allNotes = mutableListOf<NotesModel>()

    init {
        for (i in 0 until 100) {
            var notes = NotesModel(
                i, "Title " + i, "Title Description " + i, "NOTE"
            )
            allNotes.add(notes)
        }
    }

    lateinit var notesRepository: NotesRepository

    init {
        notesRepository = NotesRepository()
    }

    fun getAllTypeNotes(): LiveData<List<AllNotesModel>> {
        return notesRepository.getAllTypeNotes()
    }
}
