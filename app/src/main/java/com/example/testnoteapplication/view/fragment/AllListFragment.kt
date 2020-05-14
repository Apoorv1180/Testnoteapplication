package com.example.testnoteapplication.view.fragment

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.testnoteapplication.R
import com.example.testnoteapplication.Util.NoteUtil
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.view.activity.MainActivity
import com.example.testnoteapplication.view.adapter.AllListAdapter
import com.example.testnoteapplication.view.adapter.AllNotesAdapter
import com.example.testnoteapplication.viewmodel.AllListViewModel
import kotlinx.android.synthetic.main.all_notes_fragment.*
import kotlinx.android.synthetic.main.all_notes_fragment.progress

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
    private var adapter: AllListAdapter? = null
    lateinit var notesList: List<AllNotesModel>

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

        colorDrawableBackground = ColorDrawable(Color.parseColor("#538cc6"))
        deleteIcon = ContextCompat.getDrawable(this.context!!, R.drawable.ic_list_red_24dp)!!

        // CallBack for Swipe
        val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDirection: Int) {
                (adapter as AllListAdapter).removeItem(
                    viewHolder.adapterPosition,
                    viewHolder,
                    viewModel
                )
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val iconMarginVertical =
                    (viewHolder.itemView.height - deleteIcon.intrinsicHeight) / 2

                if (dX > 0) {
                    colorDrawableBackground.setBounds(
                        itemView.left,
                        itemView.top,
                        dX.toInt(),
                        itemView.bottom
                    )
                    deleteIcon.setBounds(
                        itemView.left + iconMarginVertical,
                        itemView.top + iconMarginVertical,
                        itemView.left + iconMarginVertical + deleteIcon.intrinsicWidth,
                        itemView.bottom - iconMarginVertical
                    )
                } else {
                    colorDrawableBackground.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                    deleteIcon.setBounds(
                        itemView.right - iconMarginVertical - deleteIcon.intrinsicWidth,
                        itemView.top + iconMarginVertical,
                        itemView.right - iconMarginVertical,
                        itemView.bottom - iconMarginVertical
                    )
                    deleteIcon.level = 0
                }
                colorDrawableBackground.draw(c)
                c.save()

                if (dX > 0)
                    c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                else
                    c.clipRect(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )

                deleteIcon.draw(c)
                c.restore()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(allListsRecyclerView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AllListViewModel::class.java)
        viewModel.getAllNotes(NoteUtil.LIST).observe(
                viewLifecycleOwner,
                Observer { listNotes ->
                    listNotes?.let {
                        Log.i("Notes", "Got crimeLiveData ${listNotes.size}")
                        notesList= emptyList()
                        notesList=listNotes
                        if (notesList.size > 0) {
                            updateUI(notesList)
                            emptynote.visibility = View.GONE
                            allListsRecyclerView.visibility=View.VISIBLE
                        } else {
                            allListsRecyclerView.visibility=View.INVISIBLE
                            progress.visibility = View.GONE
                            emptynote.visibility = View.VISIBLE
                        }
                    }
                }
        )
        adapter = AllListAdapter(allLists) {
            openEditNoteDialogueFragment(it)
        }
        allListsRecyclerView.adapter = adapter
    }

    
    override fun onStart() {
        super.onStart()

        adapter = AllListAdapter(allLists) {
            openEditNoteDialogueFragment(it)
        }
        allListsRecyclerView.adapter = adapter
    }


    private fun updateUI(allNotesRe: List<AllNotesModel>) {
        adapter?.let {
            it.allLists = allNotesRe
        } ?: run {
            adapter = AllListAdapter(allLists) {
                openEditNoteDialogueFragment(it)
            }
        }
        allListsRecyclerView.adapter = adapter
        progress.visibility = View.GONE
    }

    private fun openEditNoteDialogueFragment(note: AllNotesModel) {
        Log.e("TAG-ADAPTER_LIST", note.noteTitle)
        (activity as MainActivity?)?.callEditFragment(note)
    }
}
