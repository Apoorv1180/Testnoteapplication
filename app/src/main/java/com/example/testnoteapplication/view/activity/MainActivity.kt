package com.example.testnoteapplication.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.core.content.ContextCompat
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
    companion object{
        var isnoteChecked : Boolean = true
        var isSubChecked : Boolean = false
        var isListChecked : Boolean = false


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //loading default fragment
        loadDefaultFragment(ViewAllTypeNotesFragment())
        note_img.isSelected =true
        note_img.isFocusable = true
        initListner()
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
            fragmentTransaction.replace(R.id.fragmentContainer, fragment)
            fragmentTransaction.commit()
        }
    }

    private fun loadDefaultFragment(fragment: Fragment) {
        //currentFragment= fragment
        supportFragmentManager.beginTransaction().also { fragmentTransaction ->
            fragmentTransaction.replace(R.id.fragmentContainer, fragment)
            fragmentTransaction.addToBackStack("VIEW_ALL_TYPE_NOTES")
            fragmentTransaction.commit()
        }
    }
    //UI Code
    private fun initListner() {
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
        home.setOnClickListener {
            //todo 1 change active inactive state

            isnoteChecked = true
            isSubChecked = false
            isListChecked = false
            home.isSelected= isnoteChecked
            list.isSelected = isListChecked
            subscription.isSelected= isSubChecked

            if (home.isSelected) {
                note_img.background =
                    ContextCompat.getDrawable(this, R.drawable.btn_ic_home_enabled)
                subs_img.background =
                    ContextCompat.getDrawable(this, R.drawable.btn_ic_home_disabled)
                list_img.background =
                    ContextCompat.getDrawable(this, R.drawable.btn_ic_home_disabled)
                loadDefaultFragment(ViewAllTypeNotesFragment())
            }
        }
        //subscription= View All subscriptions
        subscription.setOnClickListener {
            loadFragment(AllNotesFragment())
            Toast.makeText(
                application,
                "subs" + subs_img.isPressed + "" + home_img.isPressed + "" + home.isPressed,
                Toast.LENGTH_SHORT
            )
                .show()
            //todo 2 change active inactive state
            isnoteChecked = false
            isSubChecked = true
            isListChecked = false
            home.isSelected= isnoteChecked
            list.isSelected = isListChecked
            subscription.isSelected= isSubChecked
            if (subscription.isSelected) {
                note_img.background =
                    ContextCompat.getDrawable(this, R.drawable.btn_ic_home_disabled)
                subs_img.background =
                    ContextCompat.getDrawable(this, R.drawable.btn_ic_home_enabled)
                list_img.background =
                    ContextCompat.getDrawable(this, R.drawable.btn_ic_home_disabled)
            }
        }
        //todo 3 list= View All Lists fragment
        list.setOnClickListener {
            loadFragment(AllNotesFragment())
            //todo 4 change active inactive state
            isnoteChecked = false
            isSubChecked = false
            isListChecked = true
            home.isSelected= isnoteChecked
            list.isSelected = isListChecked
            subscription.isSelected= isSubChecked
            if (list.isSelected) {
                note_img.background =
                    ContextCompat.getDrawable(this, R.drawable.btn_ic_home_disabled)
                subs_img.background =
                    ContextCompat.getDrawable(this, R.drawable.btn_ic_home_disabled)
                list_img.background =
                    ContextCompat.getDrawable(this, R.drawable.btn_ic_home_enabled)
            }
        }
    }


    private fun openMenu() {
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
    }
}
