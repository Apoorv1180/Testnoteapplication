package com.example.testnoteapplication.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.testnoteapplication.R
import com.example.testnoteapplication.data.model.NotesModel
import com.example.testnoteapplication.viewmodel.AllNotesViewModel
import com.example.testnoteapplication.viewmodel.enum.NoteType

class AllNotesFragment : Fragment() {

    companion object {
        fun newInstance() =
            AllNotesFragment()
    }

    private lateinit var viewModel: AllNotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.all_notes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AllNotesViewModel::class.java)

        viewModel.getAllNotes(NoteType.NOTE).observe(viewLifecycleOwner,Observer<List<NotesModel>>{ list ->
            Log.e("TAG",list.get(0).noteDescription);
        })
    }

}
