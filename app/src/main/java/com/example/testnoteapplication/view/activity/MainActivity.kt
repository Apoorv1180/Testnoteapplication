package com.example.testnoteapplication.view.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.testnoteapplication.R
import com.example.testnoteapplication.Util.NoteUtil
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.view.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.minifab.*

class MainActivity : AppCompatActivity() {
    //var addedFragment: Boolean= false
    var isOpen = false

    companion object{
        var isnoteChecked : Boolean = false
        var isSubChecked : Boolean = false
        var isListChecked : Boolean = false
        var isHomeChecked:Boolean =true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //loading default fragment
        loadDefaultFragment(ViewAllTypeNotesFragment())
        setStateValues()
        initListener()

        //Initialize All Notes View Model
    }
//

    private fun showEditNoteDialogueFragment(notesModel: AllNotesModel) {
        // Creating the new Fragment with the name passed in.
        val editNotesFragment = EditNoteFragment.newInstance(notesModel)
        supportFragmentManager?.let {editNotesFragment.show(supportFragmentManager, "Edit Note")}
            // loadFragment(editNotesFragment)
    }

    private fun showEditListDialogueFragment(notesModel: AllNotesModel) {
        // Creating the new Fragment with the name passed in.
        val editListFragment = EditListFragment.newInstance(notesModel)
        supportFragmentManager?.let {editListFragment.show(supportFragmentManager, "Edit Note")}
        // loadFragment(editNotesFragment)
    }

    private fun showEditSubDialogueFragment(notesModel: AllNotesModel) {
        val editSubFragment = EditSubscriptionFragment.newInstance(notesModel)
        supportFragmentManager?.let {editSubFragment.show(supportFragmentManager, "Edit Note")}

    }

    private fun showAddSubscriptionDialogFragment() {
        val addSubscriptionFragment = AddSubscriptionFragment()
        addSubscriptionFragment.show(supportFragmentManager, "Add Subscription")
    }

    private fun showAddNoteDialogFragment() {
        val addNoteFragment = AddNoteFragment()
        addNoteFragment.show(supportFragmentManager, "Add Note")
    }

    private fun showAddListDialogFragment() {
        val addListFragment = AddListFragment()
        addListFragment.show(supportFragmentManager, "Add List")
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
    private fun initListener() {
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
           // loadFragment(AddListFragment())
            showAddListDialogFragment()
            //showAddListDialogFragment()
        }
        //fab3=Add Subscription
        fab3.setOnClickListener {
            showAddSubscriptionDialogFragment()
        }
        //home= View All note fragment
        home.setOnClickListener {
            //todo 1 change active inactive state
            isHomeChecked=true
            isnoteChecked = false
            isSubChecked = false
            isListChecked = false
            setStateValues()
            if (home.isSelected) {
                loadDefaultFragment(ViewAllTypeNotesFragment())
            }
        }
        //subscription= View All subscriptions
        subscription.setOnClickListener {
            //todo 2 change active inactive state
            isHomeChecked =false
            isnoteChecked = false
            isSubChecked = true
            isListChecked = false
            setStateValues()
            if (subscription.isSelected) {
                loadFragment(AllSubscriptionFragment())
            }
        }

        //note= View All notes
        note.setOnClickListener {
            //todo 2 change active inactive state
            isHomeChecked =false
            isnoteChecked = true
            isSubChecked = false
            isListChecked = false
            setStateValues()
            if (note.isSelected) {
                loadFragment(AllNotesFragment())
            }
        }
        //todo 3 list= View All Lists fragment
        list.setOnClickListener {
            //todo 4 change active inactive state
            isHomeChecked =false
            isnoteChecked = false
            isSubChecked = false
            isListChecked = true
            setStateValues()
            if (list.isSelected) {
                loadFragment(AllListFragment())
                /*Toast.makeText(application, "View List Dialog fragment", Toast.LENGTH_SHORT)
                    .show()*/
            }
        }
    }

    private fun setStateValues() {
        home.isSelected = isHomeChecked
        list.isSelected = isListChecked
        subscription.isSelected = isSubChecked
        note.isSelected = isnoteChecked
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

    fun callEditFragment(note: AllNotesModel) {
        Log.e("yes ","YES" + note.noteTitle)
        if(note.noteType==NoteUtil.NOTE) {
            showEditNoteDialogueFragment(note)
        }
        else if(note.noteType==NoteUtil.LIST){
        showEditListDialogueFragment(note)
        }else if(note.noteType==NoteUtil.SUB){
            showEditSubDialogueFragment(note)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}
