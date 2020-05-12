package com.example.testnoteapplication.view.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.testnoteapplication.R
import com.example.testnoteapplication.Util.NoteUtil
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.view.adapter.AllSubscriptionAdapter
import com.example.testnoteapplication.viewmodel.AllNotesViewModel
import com.example.testnoteapplication.viewmodel.AllSubscriptionViewModel
import kotlinx.android.synthetic.main.view_all_type_notes_fragment.*

class AllSubscriptionFragment : Fragment() {
    var allSubscription = mutableListOf<AllNotesModel>()

    companion object {
        fun newInstance() = AllSubscriptionFragment()
    }

    private lateinit var viewModel: AllSubscriptionViewModel
    private lateinit var allSubscriptionRecyclerView: RecyclerView
    private var adapter: AllSubscriptionAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.all_subscription_fragment, container, false)
        allSubscriptionRecyclerView =
                view.findViewById(R.id.subAllRecycler) as RecyclerView
        allSubscriptionRecyclerView.layoutManager =
                LinearLayoutManager(this.context)

        allSubscriptionRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AllSubscriptionViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        viewModel.getAllNotes(NoteUtil.SUB).observe(
                viewLifecycleOwner,
                Observer { listSubscription ->
                    listSubscription?.let {
                        Log.i("Notes", "Got Subscriptions ${listSubscription.size}")
                        updateUI(listSubscription)
                    }
                }
        )
        adapter = AllSubscriptionAdapter(allSubscription)
        allSubscriptionRecyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(AllSubscriptionViewModel::class.java)
        // TODO: Use the ViewModel
    }


    private fun updateUI(allNotesRe: List<AllNotesModel>) {
        adapter?.let {
            it.allSubscription = allNotesRe
        } ?: run {
            adapter = AllSubscriptionAdapter(allSubscription)
        }
        allSubscriptionRecyclerView.adapter = adapter
        progress.visibility = View.GONE
    }
}
