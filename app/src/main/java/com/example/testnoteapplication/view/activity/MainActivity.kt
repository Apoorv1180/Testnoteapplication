package com.example.testnoteapplication.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.testnoteapplication.R
import com.example.testnoteapplication.view.fragment.*
import com.example.testnoteapplication.view.adapter.AllTypeNotesAdapter
import com.example.testnoteapplication.view.fragment.AddNoteFragment
import com.example.testnoteapplication.view.fragment.AllNotesFragment
import com.example.testnoteapplication.view.fragment.ViewAllTypeNotesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //var addedFragment: Boolean= false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //loading default fragment
        loadDefaultFragment(ViewAllTypeNotesFragment())
        //Load fragment on bottom navigation option select
        bottom_nav.setOnNavigationItemSelectedListener { menuItem ->
            when {
                menuItem.itemId == R.id.add -> {
                    showAddNoteDialogFragment()
                    return@setOnNavigationItemSelectedListener true
                }
                menuItem.itemId == R.id.note -> {
                    loadFragment(AllNotesFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                menuItem.itemId ==R.id.list ->{
                    showAddSubscriptionDialogFragment()
                    return@setOnNavigationItemSelectedListener true
                }
                menuItem.itemId == R.id.subscription -> {
                    loadFragment(ViewAllTypeNotesFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }

    private fun showAddSubscriptionDialogFragment() {
        val addSubscriptionFragment = AddSubscriptionFragment()
        addSubscriptionFragment.show(supportFragmentManager, "Add Subscription")
    }

    private fun showAddNoteDialogFragment() {
        val addNoteFragment = AddNoteFragment()
        addNoteFragment.show(supportFragmentManager, "Add Note")
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().also { fragmentTransaction ->
            fragmentTransaction.add(R.id.fragmentContainer, fragment)
            fragmentTransaction.commit()
        }
    }

    private fun loadDefaultFragment(fragment: Fragment) {
        //currentFragment= fragment
        supportFragmentManager.beginTransaction().also { fragmentTransaction ->
            fragmentTransaction.add(R.id.fragmentContainer, fragment)
            fragmentTransaction.addToBackStack("VIEW_ALL_TYPE_NOTES")
            fragmentTransaction.commit()
        }
    }
}
