package com.example.testnoteapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testnoteapplication.data.model.NotesModel
import com.example.testnoteapplication.data.repository.NotesRepository
import com.example.testnoteapplication.viewmodel.enum.NoteType

class AllNotesViewModel : ViewModel() {

    lateinit var notesRepository: NotesRepository

    init {
        notesRepository = NotesRepository()
    }

    fun getAllNotes(noteType: NoteType): LiveData<List<NotesModel>> {
        return notesRepository.getAllNotes(noteType);
    }

    /*  fun getNotes(noteId: Int): LiveData<NotesModel> {
          return notesRepository.getNotes(noteId);
      }*/
}