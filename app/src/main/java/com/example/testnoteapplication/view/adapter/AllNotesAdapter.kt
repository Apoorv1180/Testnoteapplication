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
import com.example.testnoteapplication.data.db.async.UndoTask
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.view.fragment.AddNoteFragment
import com.example.testnoteapplication.viewmodel.AllNotesViewModel
import com.google.android.material.snackbar.Snackbar
import com.sdsu.noteapp.data.db.async.DeleteTask
import kotlinx.android.synthetic.main.adapter_all_type_notes.view.*

class AllNotesAdapter (var allNotes: List<AllNotesModel>) :
    RecyclerView.Adapter<AllNotesAdapter.AllNotesHolder>() {

    private var removedPosition: Int = 0
    private lateinit var removedNote: AllNotesModel
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllNotesHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_all_type_notes, parent, false)
        context = parent.context
        return AllNotesHolder(view)
    }

    override fun getItemCount(): Int {
        Log.e("ADAPTER", allNotes.size.toString())
        return allNotes.size
    }

    override fun onBindViewHolder(holder: AllNotesHolder, position: Int) {
        val note = allNotes[position]
        Log.e("ADAPTER", "Adapter called")
        holder.bind(note)
    }

    fun removeItem(
        position: Int,
        viewHolder: RecyclerView.ViewHolder,
        viewModel: AllNotesViewModel
    ) {
        removedNote = allNotes[position]
        removedPosition = position
        DeleteTask(context, viewModel, removedNote).execute()
        notifyItemRemoved(position)

        Snackbar.make(viewHolder.itemView, "$removedNote removed", Snackbar.LENGTH_LONG).setAction("UNDO") {
            /*allNotes.toMutableList().add(removedPosition, removedNote)
            viewModel.addNoteVm(removedNote)*/
            UndoTask(context, viewModel, removedNote).execute()
            notifyItemInserted(removedPosition)
        }.show()
    }

    inner class AllNotesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var allTypeNotes: AllNotesModel
        private val noteTitle: TextView = itemView.findViewById<TextView>(R.id.rvNoteTitle)
        private val noteDescription: TextView = itemView.findViewById<TextView>(R.id.rvNoteDescription)
        private val createdOn: TextView = itemView.findViewById<TextView>(R.id.rvCreatedOn)
        private val card:CardView = itemView.findViewById(R.id.card_id);

        init {
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Note Single clicked!", Toast.LENGTH_SHORT)
                    .show()

            }

            itemView.setOnLongClickListener {
                Toast.makeText(itemView?.context, "Note Long Press clicked!", Toast.LENGTH_SHORT)
                    .show()
                true
            }

            /*itemView.setOnDragListener {
                Toast.makeText(itemView?.context, "${allTypeNotes.noteTitle} Dragged !", Toast.LENGTH_SHORT)
                    .show()
                true
            }*/
            itemView.card_id.setBackgroundColor(context.resources.getColor(R.color.colorsubs))

        }
        fun bind(notesModel: AllNotesModel) {
            this.allTypeNotes = notesModel
            this.noteTitle.text = this.allTypeNotes.noteTitle
            this.noteDescription.text = this.allTypeNotes.noteDescription
            this.createdOn.text = this.allTypeNotes.createdOn
            this.card.setBackgroundColor(context.resources.getColor(R.color.colorsubs))
        }
    }
}