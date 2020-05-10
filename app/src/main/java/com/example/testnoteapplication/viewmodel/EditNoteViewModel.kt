package com.example.testnoteapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testnoteapplication.data.db.NotesAppDatabase
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.data.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditNoteViewModel (application: Application): AndroidViewModel(application) {
    private val notesRepository: NotesRepository
    val data: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    init {
        val notesDao = NotesAppDatabase.getDatabase(application, viewModelScope).notesDao()
        notesRepository = NotesRepository(notesDao)
    }
    fun updateNoteVm(notesModel: AllNotesModel)=viewModelScope.launch (Dispatchers.IO){
        notesRepository.updateNoteRepo(notesModel)
    }

    fun setValue(thisRef: Boolean) {
        data.value = thisRef
    }

    fun getValue(): MutableLiveData<Boolean> {
        return data
    }
}
