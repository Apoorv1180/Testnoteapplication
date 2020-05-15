package com.example.testnoteapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testnoteapplication.data.db.NotesAppDatabase
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.data.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllListViewModel(application: Application) : AndroidViewModel(application) {

    private val notesRepository: NotesRepository
    val data: MutableLiveData<AllNotesModel> by lazy {
        MutableLiveData<AllNotesModel>()
    }
    val databoolean: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    init {
        val notesDao = NotesAppDatabase.getDatabase(application, viewModelScope).notesDao()
        notesRepository = NotesRepository(notesDao)
    }

    fun getAllNotes(noteType: String): LiveData<List<AllNotesModel>> {
        return notesRepository.getAllNotes(noteType);
    }

    fun deleteNote(notesModel: AllNotesModel) = viewModelScope.launch(Dispatchers.IO)  {
        notesRepository.deleteNoteRepo(notesModel)
    }

    fun undoNoteVm(notesModel: AllNotesModel) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.undoNoteRepo(notesModel)
    }


    fun setValue(thisRef: AllNotesModel) {
        data.value = thisRef
    }

    fun getValue(): MutableLiveData<AllNotesModel> {
        return data
    }

    /*fun setValueDelete(thisRef: Boolean) {
        databoolean.value = thisRef
    }

    fun getValueDelete(): MutableLiveData<Boolean> {
        return databoolean
    }*/
}
