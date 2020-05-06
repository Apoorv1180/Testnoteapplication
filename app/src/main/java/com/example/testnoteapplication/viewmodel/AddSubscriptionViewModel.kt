package com.example.testnoteapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testnoteapplication.data.db.NotesAppDatabase
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.data.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddSubscriptionViewModel (application: Application) : AndroidViewModel(application)  {

    private val notesRepository: NotesRepository

    init {
        val notesDao = NotesAppDatabase.getDatabase(application, viewModelScope).notesDao()
        notesRepository = NotesRepository(notesDao)
    }

    fun addSubscriptionVm(notesModel: AllNotesModel) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.addSubRepo(notesModel)
    }
}
