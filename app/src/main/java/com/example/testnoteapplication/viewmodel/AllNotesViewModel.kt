package com.example.testnoteapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testnoteapplication.data.model.NotesModel
import com.example.testnoteapplication.data.repository.NotesRepository

class AllNotesViewModel : ViewModel() {

    lateinit var notesRepository: NotesRepository

    init {
        notesRepository = NotesRepository()
    }

    fun getAllNotes(constants: String): LiveData<List<NotesModel>> {
        return notesRepository.getAllNotes(constants);
    }

    /*  fun getNotes(noteId: Int): LiveData<NotesModel> {
          return notesRepository.getNotes(noteId);
      }*/
}