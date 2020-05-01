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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
                    loadFragment(ViewAllTypeNotesFragment())
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (currentFragment == null) {
            val fragment = ViewAllTypeNotesFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .addToBackStack("ViewAllTypeNotesFragment")
                .commit()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().also { fragmentTransaction ->
            fragmentTransaction.replace(R.id.fragmentContainer, fragment)
            fragmentTransaction.commit()
        }
    }
}