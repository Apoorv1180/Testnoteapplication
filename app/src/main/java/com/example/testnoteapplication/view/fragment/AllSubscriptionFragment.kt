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
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.testnoteapplication.R
import com.example.testnoteapplication.Util.NoteUtil
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.view.activity.MainActivity
import com.example.testnoteapplication.view.adapter.AllNotesAdapter
import com.example.testnoteapplication.view.adapter.AllSubscriptionAdapter
import com.example.testnoteapplication.viewmodel.AllNotesViewModel
import com.example.testnoteapplication.viewmodel.AllSubscriptionViewModel
import kotlinx.android.synthetic.main.all_subscription_fragment.*
import kotlinx.android.synthetic.main.view_all_type_notes_fragment.*
import kotlinx.android.synthetic.main.view_all_type_notes_fragment.progress

class AllSubscriptionFragment : Fragment() {
    var allSubscription = mutableListOf<AllNotesModel>()
    private lateinit var colorDrawableBackground: ColorDrawable
    private lateinit var deleteIcon: Drawable

    companion object {
        fun newInstance() = AllSubscriptionFragment()
    }

    private lateinit var viewModel: AllSubscriptionViewModel
    private lateinit var allSubscriptionRecyclerView: RecyclerView
    private var adapter: AllSubscriptionAdapter? = null
    lateinit var subList: List<AllNotesModel>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.all_subscription_fragment, container, false)

      //  val icons_array = intArrayOf(R.drawable.disney, R.drawable.googleplay, R.drawable.hbo, R.drawable.hulu, R.drawable.netflix, R.drawable.primevideo, R.drawable.ic_add)
        allSubscriptionRecyclerView =
            view.findViewById(R.id.subAllRecycler) as RecyclerView
        allSubscriptionRecyclerView.layoutManager =
            LinearLayoutManager(this.context)

        allSubscriptionRecyclerView.adapter = adapter

        colorDrawableBackground = ColorDrawable(Color.parseColor("#9E9E9E"))
        deleteIcon = ContextCompat.getDrawable(this.context!!, R.drawable.btn_ic_subs_enabled)!!

        // CallBack for Swipe
        val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDirection: Int) {
                (adapter as AllSubscriptionAdapter).removeItem(
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
        itemTouchHelper.attachToRecyclerView(allSubscriptionRecyclerView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AllSubscriptionViewModel::class.java)
        viewModel.getAllNotes(NoteUtil.SUB).observe(
                viewLifecycleOwner,
                Observer { listSubscription ->
                    listSubscription?.let {
                        Log.i("Notes", "Got Subscriptions ${listSubscription.size}")
                        subList= emptyList()
                        subList=listSubscription
                        if(subList.size >0) {
                            allSubscriptionRecyclerView.visibility =View.VISIBLE
                            updateUI(subList)
                            emptysubs.visibility = View.GONE
                        }else{
                            allSubscriptionRecyclerView.visibility=View.INVISIBLE
                            emptysubs.visibility = View.VISIBLE
                            progress.visibility = View.GONE
                        }
                    }
                }
        )
        adapter = AllSubscriptionAdapter(allSubscription){
            openEditSubDialogueFragment(it)
        }
        allSubscriptionRecyclerView.adapter = adapter

    }

    override fun onStart() {
        super.onStart()



        adapter = AllSubscriptionAdapter(allSubscription){
            openEditSubDialogueFragment(it)
        }
        allSubscriptionRecyclerView.adapter = adapter
    }

    private fun openEditSubDialogueFragment(sub: AllNotesModel) {
        Log.e("TAG-ADAPTER", sub.noteTitle)
        (activity as MainActivity?)?.callEditFragment(sub)
    }

    private fun updateUI(allNotesRe: List<AllNotesModel>) {
        adapter?.let {
            it.allSubscription = allNotesRe
        } ?: run {
            adapter = AllSubscriptionAdapter(allSubscription){
                openEditSubDialogueFragment(it)
            }
        }
        allSubscriptionRecyclerView.adapter = adapter
        progress.visibility = View.GONE
    }
}
