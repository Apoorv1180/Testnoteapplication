package com.example.testnoteapplication.view.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.testnoteapplication.R
import com.example.testnoteapplication.data.model.NotesModel
import com.example.testnoteapplication.viewmodel.AddNoteViewModel
import kotlinx.android.synthetic.main.add_note_fragment.*

class AddNoteFragment : Fragment() {

    companion object {
        fun newInstance() = AddNoteFragment()
    }

    private lateinit var viewModel: AddNoteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_note_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddNoteViewModel::class.java)
        // TODO: Use the ViewModel
//        savenote.setOnClickListener{
            var notesModel = NotesModel(1, R.id.noteTitle.toString(), R.id.noteDescription.toString())
//            val isNoteAdded = viewModel.addNote(notesModel)
//            if(isNoteAdded) {
//                Toast.makeText(this.context,"Success", Toast.LENGTH_LONG).show() //.maketext(this,"Success", Toast.LENGTH_LONG)
//            }
//        }
//        viewModel.addNote(notesModel).observe(this, Observer<List<CountryModel>> { countryList ->
//            Log.e(MainActivity::class.java.simpleName,countryList.toString())
        viewModel.addNote(notesModel).observe(this,Observer<Boolean>){ value ->
            Log.e("TAG",String.valueOf(value));
            if(value) {
                Toast.makeText(this.context,"Success", Toast.LENGTH_LONG).show() //.maketext(this,"Success", Toast.LENGTH_LONG)
           }
    }




}
