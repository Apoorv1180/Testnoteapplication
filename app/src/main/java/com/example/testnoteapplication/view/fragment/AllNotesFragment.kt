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
import com.example.testnoteapplication.view.activity.MainActivity
import com.example.testnoteapplication.view.adapter.AllNotesAdapter
import com.example.testnoteapplication.viewmodel.AllNotesViewModel
import kotlinx.android.synthetic.main.all_notes_fragment.*
import kotlinx.android.synthetic.main.view_all_type_notes_fragment.progress

class AllNotesFragment : Fragment() {
    var allNotes = mutableListOf<AllNotesModel>()
    private lateinit var colorDrawableBackground: ColorDrawable
    private lateinit var deleteIcon: Drawable

    companion object {
        fun newInstance() =
            AllNotesFragment()
    }

    private lateinit var viewModel: AllNotesViewModel
    private lateinit var allNotesRecyclerView: RecyclerView
    private var adapter: AllNotesAdapter? = null
    private var notesList = mutableListOf<AllNotesModel>()
    lateinit var noteList: List<AllNotesModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.all_notes_fragment, container, false)
        allNotesRecyclerView =
            view.findViewById(R.id.noteAllRecycler) as RecyclerView
        allNotesRecyclerView.layoutManager =
            LinearLayoutManager(this.context)
        allNotesRecyclerView.adapter = adapter

        colorDrawableBackground = ColorDrawable(Color.parseColor("#9E9E9E"))
        deleteIcon = ContextCompat.getDrawable(this.context!!, R.drawable.btn_ic_note_enabled)!!

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
                (adapter as AllNotesAdapter).removeItem(
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
        itemTouchHelper.attachToRecyclerView(allNotesRecyclerView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AllNotesViewModel::class.java)

        viewModel.getAllNotes(NoteUtil.NOTE).observe(
                viewLifecycleOwner,
                Observer { listNotes ->
                    listNotes?.let {
                        noteList= emptyList()
                        noteList=listNotes
                        if(noteList.size >0) {
                            updateUI(noteList)
                            emptynote.visibility = View.GONE
                            allNotesRecyclerView.visibility=View.VISIBLE
                        }else {
                            allNotesRecyclerView.visibility=View.INVISIBLE
                            progress.visibility = View.GONE
                            emptynote.visibility = View.VISIBLE
                        }
                    }
                }
        )
        adapter = AllNotesAdapter(allNotes) {
            openEditNoteDialogueFragment(it)
        }
        allNotesRecyclerView.adapter = adapter
    }


    override fun onStart() {
        super.onStart()
          }


    private fun updateUI(allNotesRe: List<AllNotesModel>) {
        adapter?.let {
            it.allNotes = allNotesRe
        } ?: run {
            adapter = AllNotesAdapter(allNotes) {
                openEditNoteDialogueFragment(it)
            }
        }
        allNotesRecyclerView.adapter = adapter
        progress.visibility = View.GONE
    }

    private fun openEditNoteDialogueFragment(note: AllNotesModel) {
        Log.e("TAG-ADAPTER", note.noteTitle)
        (activity as MainActivity?)?.callEditFragment(note)
    }
}
