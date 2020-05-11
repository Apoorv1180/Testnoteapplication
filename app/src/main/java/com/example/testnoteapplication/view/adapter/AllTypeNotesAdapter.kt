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
import com.example.testnoteapplication.data.model.AllNotesModel

class AllTypeNotesAdapter(var allNotes: List<AllNotesModel>) :
    RecyclerView.Adapter<AllTypeNotesAdapter.AllTypesNotesHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTypesNotesHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_all_type_notes, parent, false)
        context = parent.context
        return AllTypesNotesHolder(view)
    }

    override fun getItemCount(): Int {
        Log.e("ADAPTER", allNotes.size.toString())
        return allNotes.size
    }

    override fun onBindViewHolder(holder: AllTypesNotesHolder, position: Int) {
        val note = allNotes[position]
        Log.e("ADAPTER", "Adapter called")
        holder.bind(note)
    }

    inner class AllTypesNotesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var allTypeNotes: AllNotesModel
        private val noteTitle: TextView = itemView.findViewById<TextView>(R.id.rvNoteTitle)
        private val noteDescription: TextView = itemView.findViewById<TextView>(R.id.rvNoteDescription)
        private val createdOn: TextView = itemView.findViewById<TextView>(R.id.rvCreatedOn)
        private val card: CardView = itemView.findViewById(R.id.card_id);


        init {
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "All Type Single clicked!", Toast.LENGTH_SHORT)
                    .show()
            }

            itemView.setOnLongClickListener {
                Toast.makeText(itemView?.context, "All Type Long Press clicked!", Toast.LENGTH_SHORT)
                    .show()
                true
            }

            /*itemView.setOnDragListener {
                Toast.makeText(itemView?.context, "${allTypeNotes.noteTitle} Dragged !", Toast.LENGTH_SHORT)
                    .show()
                true
            }*/
        }
        fun bind(allTypeNotesModel: AllNotesModel) {
            this.allTypeNotes = allTypeNotesModel
            this.noteTitle.text = this.allTypeNotes.noteTitle
            this.noteDescription.text = this.allTypeNotes.noteDescription
            this.createdOn.text = this.allTypeNotes.createdOn
            if(allTypeNotesModel.card_color.equals(1)) {
                this.card.setCardBackgroundColor(context.resources.getColor(R.color.colornote))
                this.card.radius = 15f
            }
            else if(allTypeNotesModel.card_color.equals(2)) {
                this.card.setCardBackgroundColor(context.resources.getColor(R.color.colorsubs))
                this.card.radius = 15f
            }

        }
    }
}




