package com.example.testnoteapplication.data.db.async

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.viewmodel.AllListViewModel
import com.example.testnoteapplication.viewmodel.AllNotesViewModel

class UndoListTask (var context: Context?, var viewModel: AllListViewModel, var allNotesModel: AllNotesModel) : AsyncTask<Void, Void, Boolean>() {
    override fun doInBackground(vararg params: Void?): Boolean {
        viewModel.undoNoteVm(allNotesModel)
        return true
    }
    override fun onPostExecute(bool: Boolean?) {
        if (bool!!) {
            Toast.makeText(context, "Undo task done", Toast.LENGTH_LONG).show()
        }
    }
}