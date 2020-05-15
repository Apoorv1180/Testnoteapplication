package com.example.testnoteapplication.view.fragment

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.testnoteapplication.R
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.view.activity.MainActivity
import com.example.testnoteapplication.view.adapter.AllTypeNotesAdapter
import com.example.testnoteapplication.viewmodel.ViewAllTypeNotesViewModel
import kotlinx.android.synthetic.main.view_all_type_notes_fragment.*

class ViewAllTypeNotesFragment : Fragment() {
    var allNotes = mutableListOf<AllNotesModel>()
    private lateinit var staggeredLayoutManager: GridLayoutManager

    companion object {
        fun newInstance() = ViewAllTypeNotesFragment()
    }

    private lateinit var viewModel: ViewAllTypeNotesViewModel
    private lateinit var allNotesRecyclerView: RecyclerView
    private var adapter: AllTypeNotesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.view_all_type_notes_fragment, container, false)
        allNotesRecyclerView =
            view.findViewById(R.id.viewAllTypeNotesRecyclerView) as RecyclerView
        staggeredLayoutManager = GridLayoutManager(view.context,2);
        allNotesRecyclerView.layoutManager = staggeredLayoutManager
        allNotesRecyclerView.adapter = adapter
        return view
    }

    // on view created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ViewAllTypeNotesViewModel::class.java)

        viewModel.getAllTypeNotes().observe(
            viewLifecycleOwner,
            Observer { listNotes ->
                listNotes?.let {
                    if(listNotes.size>0) {
                        updateUI(listNotes)
                        //todo make image empty view gone
                        emptyview.visibility = View.GONE
                        allNotesRecyclerView.visibility=View.VISIBLE
                    }
                    else{
                        progress.visibility = View.GONE
                        emptyview.visibility = View.VISIBLE
                        allNotesRecyclerView.visibility=View.INVISIBLE
                        //todo make empty image view visible
                    }
                }
            }
        )
        adapter = AllTypeNotesAdapter(allNotes){
            openEditSubDialogueFragment(it)
        }
        allNotesRecyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

    }

    private fun updateUI(listNotes: List<AllNotesModel>) {
        adapter?.let {
            it.allNotes = listNotes
        } ?: run {
            adapter = AllTypeNotesAdapter(allNotes){
                openEditSubDialogueFragment(it)
            }
        }
        allNotesRecyclerView.adapter = adapter
        progress.visibility = View.GONE
    }

    private fun openEditSubDialogueFragment(sub: AllNotesModel) {
        Log.e("TAG-ADAPTER", sub.noteTitle)
        (activity as MainActivity?)?.callEditFragment(sub)
    }


}
