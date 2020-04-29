package com.example.testnoteapplication.view.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_note_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddNoteViewModel::class.java)
        // TODO: Use the ViewModel
        var notesModel = NotesModel(1, R.id.noteTitle.toString(), R.id.noteDescription.toString())

        viewModel.addNote(notesModel).observe(viewLifecycleOwner,Observer<Boolean> {valueBoolean ->
            Log.e("TAG", valueBoolean.toString());
        })

    }
        }





