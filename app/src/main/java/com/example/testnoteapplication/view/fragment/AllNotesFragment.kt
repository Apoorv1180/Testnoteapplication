package com.example.testnoteapplication.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.testnoteapplication.R
import com.example.testnoteapplication.Util.NoteUtil
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.view.adapter.AllTypeNotesAdapter
import com.example.testnoteapplication.viewmodel.AllNotesViewModel

class AllNotesFragment : Fragment() {
    var allNotes = mutableListOf<AllNotesModel>()
    companion object {
        fun newInstance() =
            AllNotesFragment()
    }

    private lateinit var viewModel: AllNotesViewModel
    private lateinit var allNotesRecyclerView: RecyclerView
    private var adapter: AllTypeNotesAdapter? = null
    private var notesList = mutableListOf<AllNotesModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.all_notes_fragment, container, false)
        allNotesRecyclerView =
            view.findViewById(R.id.noteAllRecycler) as RecyclerView
        allNotesRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        allNotesRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AllNotesViewModel::class.java)
    }


    override fun onStart() {
        super.onStart()

        viewModel.getAllNotes(NoteUtil.NOTE).observe(
            viewLifecycleOwner,
            Observer { listNotes ->
                listNotes?.let {
                    Log.i("Notes", "Got crimeLiveData ${listNotes.size}")
                    updateUI(listNotes)
                }
            }
        )
        adapter = AllTypeNotesAdapter(allNotes)
        allNotesRecyclerView.adapter = adapter
    }


    private fun updateUI(allNotesRe: List<AllNotesModel>) {
        adapter?.let {
            it.allNotes = allNotesRe
        } ?: run {
            adapter = AllTypeNotesAdapter(allNotes)
        }
        allNotesRecyclerView.adapter = adapter
    }
}
