package com.example.testnoteapplication.view.fragment

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testnoteapplication.R
import com.example.testnoteapplication.Util.NoteUtil
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.view.adapter.AllListsAdapter
import com.example.testnoteapplication.view.adapter.AllNotesAdapter
import com.example.testnoteapplication.view.adapter.AllSubscriptionAdapter
import com.example.testnoteapplication.viewmodel.AllListViewModel
import com.example.testnoteapplication.viewmodel.AllNotesViewModel
import com.example.testnoteapplication.viewmodel.AllSubscriptionViewModel
import kotlinx.android.synthetic.main.all_notes_fragment.*
import kotlinx.android.synthetic.main.all_subscription_fragment.*
import kotlinx.android.synthetic.main.view_all_type_notes_fragment.*
import kotlinx.android.synthetic.main.view_all_type_notes_fragment.progress

class AllListFragment : Fragment() {
    var allLists = mutableListOf<AllNotesModel>()
    private lateinit var colorDrawableBackground: ColorDrawable
    private lateinit var deleteIcon: Drawable

    companion object {
        fun newInstance() =
                AllListFragment()
    }

    private lateinit var viewModel: AllListViewModel
    private lateinit var allListsRecyclerView: RecyclerView
    private var adapter: AllListsAdapter? = null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.all_list_fragment, container, false)
        allListsRecyclerView =
                view.findViewById(R.id.listAllRecycler) as RecyclerView
        allListsRecyclerView.layoutManager =
                LinearLayoutManager(this.context)
        allListsRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AllListViewModel::class.java)
    }


    override fun onStart() {
        super.onStart()

        viewModel.getAllNotes(NoteUtil.SUB).observe(
                viewLifecycleOwner,
                Observer { listSubscription ->
                    listSubscription?.let {
                        Log.i("Notes", "Got Subscriptions ${listSubscription.size}")
                        if (listSubscription.size > 0) {
                            updateUI(listSubscription)
                            emptysubs.visibility = View.GONE
                        } else {
                            emptysubs.visibility = View.VISIBLE
                            progress.visibility = View.GONE
                        }
                    }
                }
        )
        adapter = AllListsAdapter(allLists)
        allListsRecyclerView.adapter = adapter
    }

    private fun updateUI(allNotesRe: List<AllNotesModel>) {
        adapter?.let {
            it.allLists = allNotesRe
        } ?: run {
            adapter = AllListsAdapter(allLists)
        }
        allListsRecyclerView.adapter = adapter
        progress.visibility = View.GONE
    }
}