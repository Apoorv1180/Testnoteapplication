package com.example.testnoteapplication.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.testnoteapplication.R
import com.example.testnoteapplication.Util.NoteUtil
import com.example.testnoteapplication.data.db.async.DeleteListTask
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.viewmodel.AllListViewModel

class AllListAdapter(
    var allLists: List<AllNotesModel>,
    private val listener: (AllNotesModel) -> Unit
) :
    RecyclerView.Adapter<AllListAdapter.AllListsHolder>() {

    private var removedPosition: Int = 0
    private lateinit var removedNote: AllNotesModel
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllListsHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_list_card_view, parent, false)
        context = parent.context
        return AllListsHolder(view)
    }

    override fun getItemCount(): Int {
        Log.e("ADAPTER", allLists.size.toString())
        return allLists.size
    }

    override fun onBindViewHolder(holder: AllListsHolder, position: Int) {
        val note = allLists[position]
        Log.e("ADAPTER", "Adapter called")
        holder.bind(note, listener)
    }

    fun removeItem(
        position: Int,
        viewHolder: RecyclerView.ViewHolder,
        viewModel: AllListViewModel
    ) {
        removedNote = allLists[position]
        removedPosition = position
        DeleteListTask(context, viewModel, removedNote).execute()
        /*notifyItemRemoved(position)

        Snackbar.make(viewHolder.itemView, "$removedNote removed", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                UndoListTask(context, viewModel, removedNote).execute()
                notifyItemInserted(removedPosition)
            }.show()*/
    }

    inner class AllListsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var allTypeNotes: AllNotesModel
        private val noteTitle: TextView = itemView.findViewById<TextView>(R.id.rvNoteTitle)
        private val noteDescription:TextView =
            itemView.findViewById(R.id.rvNoteDescription)
        private val createdOn: TextView = itemView.findViewById<TextView>(R.id.rvCreatedOn)
        private val card: CardView = itemView.findViewById(R.id.card_id);

        fun bind(
            notesModel: AllNotesModel,
            listener: (AllNotesModel) -> Unit
        ) {
            this.allTypeNotes = notesModel
            this.noteTitle.text = this.allTypeNotes.noteTitle
            this.noteDescription.text = NoteUtil.generateDescription(allTypeNotes.noteDescription)
            this.createdOn.text = this.allTypeNotes.createdOn
            this.card.setCardBackgroundColor(context.resources.getColor(R.color.colorlist))
            this.card.radius = 15f

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Note Single clicked!", Toast.LENGTH_SHORT)
                    .show()
                //callbackInterface.passDataCallback(allTypeNotes)
                listener(allTypeNotes)
            }

            itemView.setOnLongClickListener {
                Toast.makeText(itemView?.context, "Note Long Press clicked!", Toast.LENGTH_SHORT)
                    .show()
                true
            }
        }
    }
}