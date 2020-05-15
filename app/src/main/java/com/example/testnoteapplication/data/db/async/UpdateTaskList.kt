package com.example.testnoteapplication.data.db.async


import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.viewmodel.EditListViewModel
import com.example.testnoteapplication.viewmodel.EditNoteViewModel
import com.example.testnoteapplication.viewmodel.EditSubscriptionViewModel

class UpdateTaskList(var context: Context?, var viewModel: EditListViewModel, var allNotesModel: AllNotesModel) : AsyncTask<Void, Void, Boolean>() {
    override fun doInBackground(vararg params: Void?): Boolean {
        viewModel.updateListVm(allNotesModel)
        return true
    }

    override fun onPostExecute(bool: Boolean?) {
        if (bool!!) {
            viewModel.setValue(bool)
        }
    }
}