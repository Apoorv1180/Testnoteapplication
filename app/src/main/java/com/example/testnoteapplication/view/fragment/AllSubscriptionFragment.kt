package com.example.testnoteapplication.view.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.testnoteapplication.R

class AllSubscriptionFragment : Fragment() {

    companion object {
        fun newInstance() = AllSubscriptionFragment()
    }

    private lateinit var viewModel: AllSubscriptionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.all_subscription_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AllSubscriptionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
