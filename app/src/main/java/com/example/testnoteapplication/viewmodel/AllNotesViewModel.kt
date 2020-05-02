package com.example.testnoteapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.data.repository.NotesRepository

class AllNotesViewModel (application: Application): AndroidViewModel(application){

    lateinit var notesRepository: NotesRepository

    init {
        notesRepository = NotesRepository(application)
    }

    fun getAllNotes(noteType: String): LiveData<List<AllNotesModel>> {
        return notesRepository.getAllNotes(noteType);
    }

    /*  fun getNotes(noteId: Int): LiveData<NotesModel> {
          return notesRepository.getNotes(noteId);
      }*/
}