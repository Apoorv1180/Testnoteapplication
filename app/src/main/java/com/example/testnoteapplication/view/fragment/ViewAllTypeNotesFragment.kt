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
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.view.adapter.AllTypeNotesAdapter
import com.example.testnoteapplication.viewmodel.ViewAllTypeNotesViewModel

class ViewAllTypeNotesFragment : Fragment() {
    var allNotes = mutableListOf<AllNotesModel>()


    companion object {
        fun newInstance() = ViewAllTypeNotesFragment()
    }

    lateinit var viewModel: ViewAllTypeNotesViewModel
    private lateinit var allNotesRecyclerView: RecyclerView
    private var adapter: AllTypeNotesAdapter? = null

    private val allTypeNotesViewModel: ViewAllTypeNotesViewModel by lazy {
        ViewModelProviders.of(this).get(ViewAllTypeNotesViewModel::class.java)
    }
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
        viewModel.getAllTypeNotes().observe(viewLifecycleOwner,Observer<List<AllNotesModel>>{list->
            for( item in list){
                Log.e("VALUE",item.createdOn)
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(ViewAllTypeNotesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
