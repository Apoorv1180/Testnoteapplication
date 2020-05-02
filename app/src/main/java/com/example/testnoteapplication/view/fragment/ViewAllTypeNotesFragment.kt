package com.example.testnoteapplication.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.testnoteapplication.R
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.view.adapter.AllTypeNotesAdapter
import com.example.testnoteapplication.viewmodel.ViewAllTypeNotesViewModel

class ViewAllTypeNotesFragment : Fragment() {
    var allNotes = mutableListOf<AllNotesModel>()
    private lateinit var linearLayoutManager: LinearLayoutManager

    companion object {
        fun newInstance() = ViewAllTypeNotesFragment()
    }

    lateinit var viewModel: ViewAllTypeNotesViewModel
    private lateinit var allNotesRecyclerView: RecyclerView
    private var adapter: AllTypeNotesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_all_type_notes_fragment, container, false)
    }

    // on view created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ViewAllTypeNotesViewModel::class.java)

//        var notes = AllNotesModel(
//            1, "Title " + 2, "Title Description " + 1, "NOTE","99090")
//        allNotes.add(notes)
        allNotesRecyclerView =
            view.findViewById(R.id.recyclerView) as RecyclerView
        linearLayoutManager = LinearLayoutManager(activity)
        allNotesRecyclerView.layoutManager = linearLayoutManager
        adapter = AllTypeNotesAdapter(allNotes)
        allNotesRecyclerView.adapter = adapter

        allNotesRecyclerView.layoutManager =
            GridLayoutManager(view.context, 2, GridLayoutManager.VERTICAL, false)
        viewModel.getAllTypeNotes()
            .observe(viewLifecycleOwner, Observer<List<AllNotesModel>> { list ->
                allNotes.clear()
                allNotes.addAll(list)
                list.forEach {
                    Log.e("ALL_TYPE"," Title " + it.noteTitle)
                }
           //     adapter = AllTypeNotesAdapter(allNotes)
            })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(ViewAllTypeNotesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
