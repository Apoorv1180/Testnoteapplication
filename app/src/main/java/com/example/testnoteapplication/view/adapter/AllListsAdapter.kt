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
import com.example.testnoteapplication.data.model.AllNotesModel

class AllListsAdapter(var allLists: List<AllNotesModel>) :
        RecyclerView.Adapter<AllListsAdapter.AllListsHolder>() {

    //private var removedPosition: Int = 0
    //private lateinit var removedNote: AllNotesModel
    private lateinit var context: Context

    inner class AllListsHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            private lateinit var allTypeNotes: AllNotesModel
            private val listTitle: TextView = itemView.findViewById<TextView>(R.id.rvNoteTitle)
            private val listDescription: TextView =
                    itemView.findViewById<TextView>(R.id.rvNoteDescription)
            private val createdOn: TextView = itemView.findViewById<TextView>(R.id.rvCreatedOn)
            private val card: CardView = itemView.findViewById(R.id.card_id);

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
        }

        fun bind(notesModel: AllNotesModel) {
            this.allTypeNotes = notesModel
            this.listTitle.text = this.allTypeNotes.noteTitle
            this.listDescription.text = NoteUtil.generateDescription(notesModel.noteDescription)
            this.createdOn.text = this.allTypeNotes.createdOn
        }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllListsHolder {
        val view =
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.adapter_all_type_notes, parent, false)
        context = parent.context
        return AllListsHolder(view)
    }

    override fun getItemCount(): Int {
        Log.e("ADAPTER", allLists.size.toString())
        return allLists.size
    }

    override fun onBindViewHolder(holder: AllListsHolder, position: Int) {
        val list = allLists[position]
        Log.e("ADAPTER", "Adapter called")
        holder.bind(list)
    }

}


