package com.example.testnoteapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.data.repository.NotesRepository

class AddNoteViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    lateinit var notesRepository: NotesRepository

    init {
        notesRepository = NotesRepository()
    }

    fun addNoteVm(notesModel: AllNotesModel): LiveData<Boolean> {
        return notesRepository.addNoteRepo(notesModel)
    }
}
