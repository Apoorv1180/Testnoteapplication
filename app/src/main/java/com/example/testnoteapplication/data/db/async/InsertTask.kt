package com.sdsu.noteapp.data.db.async

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
           // Toast.makeText(context, "Added to Database", Toast.LENGTH_LONG).show()
            viewModel.setValue(bool)
        }
    }
}