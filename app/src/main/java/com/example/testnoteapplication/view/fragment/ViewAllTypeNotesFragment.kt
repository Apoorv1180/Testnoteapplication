package com.example.testnoteapplication.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.testnoteapplication.R
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.view.adapter.AllTypeNotesAdapter
import com.example.testnoteapplication.viewmodel.ViewAllTypeNotesViewModel
import kotlinx.android.synthetic.main.view_all_type_notes_fragment.*

class ViewAllTypeNotesFragment : Fragment(), View.OnLongClickListener {
    var allNotes = mutableListOf<AllNotesModel>()
    private lateinit var staggeredLayoutManager: StaggeredGridLayoutManager

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
        staggeredLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        allNotesRecyclerView.layoutManager = staggeredLayoutManager
        allNotesRecyclerView.adapter = adapter
        return view
    }

    // on view created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ViewAllTypeNotesViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        viewModel.getAllTypeNotes().observe(
            viewLifecycleOwner,
            Observer { listNotes ->
                listNotes?.let {
                    Log.i("All-Notes", "Got crimeLiveData ${listNotes.size}")
                    updateUI(listNotes)
                }
            }
        )

        adapter = AllTypeNotesAdapter(allNotes)
        allNotesRecyclerView.adapter = adapter
    }

    private fun updateUI(listNotes: List<AllNotesModel>) {
        adapter?.let {
            it.allNotes = listNotes
        } ?: run {
            adapter = AllTypeNotesAdapter(allNotes)
        }
        allNotesRecyclerView.adapter = adapter
        progress.visibility = View.GONE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(ViewAllTypeNotesViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onLongClick(v: View?): Boolean {
        Toast.makeText(v?.context, "Long clicked from view all note fragment!", Toast.LENGTH_SHORT).show()
        return true
    }

}
