package com.example.testnoteapplication.data.db.async


import android.content.Context
import android.os.AsyncTask
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.viewmodel.AddNoteViewModel

class InsertTask(var context: Context?, var viewModel: AddNoteViewModel, var allNotesModel: AllNotesModel) : AsyncTask<Void, Void, Boolean>() {
    override fun doInBackground(vararg params: Void?): Boolean {
        viewModel.addNoteVm(allNotesModel)
        return true
    }
    override fun onPostExecute(bool: Boolean?) {
        if (bool!!) {
            viewModel.setValue(bool)

        }
    }
}