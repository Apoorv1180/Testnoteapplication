package com.example.testnoteapplication.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.testnoteapplication.R
import com.example.testnoteapplication.data.model.NotesModel
import com.example.testnoteapplication.view.adapter.AllTypeNotesAdapter
import com.example.testnoteapplication.viewmodel.ViewAllTypeNotesViewModel

class ViewAllTypeNotesFragment : Fragment() {
    var allNotes = mutableListOf<NotesModel>()

    companion object {
        fun newInstance() = ViewAllTypeNotesFragment()
    }

    private var viewModel: ViewAllTypeNotesViewModel? = null
    private lateinit var allNotesRecyclerView: RecyclerView
    private var adapter: AllTypeNotesAdapter? = null

    private val allTypeNotesViewModel: ViewAllTypeNotesViewModel by lazy {
        ViewModelProviders.of(this).get(ViewAllTypeNotesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var allNotesView = inflater.inflate(R.layout.view_all_type_notes_fragment, container, false)
        allNotesRecyclerView =
            allNotesView.findViewById(R.id.viewAllTypeNotesRecyclerView) as RecyclerView
        allNotesRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        viewModel?.getAllTypeNotes()
            ?.observe(viewLifecycleOwner, Observer<List<NotesModel>> { note ->
                allNotes.clear()
                allNotes.addAll(note)
            })
        //allNotes = allTypeNotesViewModel.getAllTypeNotes()
        adapter = AllTypeNotesAdapter(allNotes)
        allNotesRecyclerView.adapter = adapter
        return allNotesView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ViewAllTypeNotesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
