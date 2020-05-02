package com.example.testnoteapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.data.repository.NotesRepository

class AddNoteViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel

    lateinit var notesRepository: NotesRepository

    init {
        notesRepository = NotesRepository(application )
    }

    fun addNoteVm(notesModel: AllNotesModel): LiveData<Long> {
        return notesRepository.addNoteRepo(notesModel)
    }
}
