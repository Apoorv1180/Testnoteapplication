package com.example.testnoteapplication.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.testnoteapplication.R
import com.example.testnoteapplication.view.fragment.AddNoteFragment
import com.example.testnoteapplication.view.fragment.AllNotesFragment
import com.example.testnoteapplication.view.fragment.ViewAllTypeNotesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var addedFragment: Boolean= false
    var currentFragment : Fragment = ViewAllTypeNotesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //loading default fragment
        loadDefaultFragment(currentFragment)
        //Load fragment on bottom navigation option select
        bottom_nav.setOnNavigationItemSelectedListener { menuItem ->
            when {
                menuItem.itemId == R.id.add -> {
                    loadFragment(AddNoteFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                menuItem.itemId == R.id.note -> {
                    loadFragment(AllNotesFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }

    }

    private fun loadFragment(fragment: Fragment) {
        addedFragment=true
        currentFragment= fragment
        supportFragmentManager.beginTransaction().also { fragmentTransaction ->
            fragmentTransaction.add(R.id.fragmentContainer, fragment)
            fragmentTransaction.commit()
        }
    }


    private fun loadDefaultFragment(fragment: Fragment) {
        currentFragment= fragment
        supportFragmentManager.beginTransaction().also { fragmentTransaction ->
            fragmentTransaction.replace(R.id.fragmentContainer, fragment)
            fragmentTransaction.addToBackStack("VIEW_ALL_TYPE_NOTES")
            fragmentTransaction.commit()
        }
    }

    override fun onBackPressed() {
        if(addedFragment){
            supportFragmentManager.popBackStack("VIEW_ALL_TYPE_NOTES",0)
        }
        else{
        super.onBackPressed()}
    }
}
