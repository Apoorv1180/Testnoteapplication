package com.example.testnoteapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testnoteapplication.data.db.NotesAppDatabase
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.data.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddListViewModel(application: Application) : AndroidViewModel(application)  {

    private val notesRepository: NotesRepository
    val datalist: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    init {
        val notesDao = NotesAppDatabase.getDatabase(application, viewModelScope).notesDao()
        notesRepository = NotesRepository(notesDao)
    }

    fun addNoteVm(notesModel: AllNotesModel) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.addNoteRepo(notesModel)
    }

    fun setValueList(bool: Boolean) {
        datalist.value= bool
    }

    fun getValueList(): MutableLiveData<Boolean> {
        return datalist
    }
}
