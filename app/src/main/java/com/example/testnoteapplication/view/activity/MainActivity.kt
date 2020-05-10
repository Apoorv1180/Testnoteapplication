package com.example.testnoteapplication.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.testnoteapplication.R
import com.example.testnoteapplication.view.fragment.*
import com.example.testnoteapplication.view.adapter.AllTypeNotesAdapter
import com.example.testnoteapplication.view.fragment.AddNoteFragment
import com.example.testnoteapplication.view.fragment.AllNotesFragment
import com.example.testnoteapplication.view.fragment.ViewAllTypeNotesFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.minifab.*

class MainActivity : AppCompatActivity() {
    //var addedFragment: Boolean= false
    var isOpen = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //loading default fragment
        loadDefaultFragment(ViewAllTypeNotesFragment())

       // initListner()
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
    //UI Code
   /* private fun initListner() {
        fab.setOnClickListener {
            if (!isOpen) {
                openMenu()
            } else {
                closeMenu()
            }
        }
        //fab1=Add Note
        fab1.setOnClickListener {
            showAddNoteDialogFragment()
        }
        //fab2= Add List
        fab2.setOnClickListener {
            Toast.makeText(application, "Add List Dialog fragment", Toast.LENGTH_SHORT)
                    .show()
        }
        //fab3=Add Subscription
        fab3.setOnClickListener {
            showAddSubscriptionDialogFragment()
        }
        //home= View All note fragment
        home.setOnClickListener{
            loadDefaultFragment(ViewAllTypeNotesFragment())
            //todo 1 change active inactive state
        }
        //subscription= View All subscriptions
        subscription.setOnClickListener{
            loadFragment(AllNotesFragment())
            //todo 2 change active inactive state
        }
        //todo 3 list= View All Lists fragment
        list.setOnClickListener{
            loadFragment(AllNotesFragment())
            //todo 4 change active inactive state
        }
    }


    private fun openMenu() {
        Toast.makeText(application, "Floating Action inside open menu", Toast.LENGTH_SHORT)
                .show()
        isOpen = true
        fab1.animate().translationY(-resources.getDimension(R.dimen.stan_55))
        fab2.animate().translationY(-resources.getDimension(R.dimen.stan_105))
        fab3.animate().translationY(-resources.getDimension(R.dimen.stan_155))
    }

    private fun closeMenu() {
        isOpen = false
        fab1.animate().translationY(0f)
        fab2.animate().translationY(0f)
        fab3.animate().translationY(0f)
    }
    private fun Context.toast(message: CharSequence) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 325)
        toast.show()
    }*/
}
