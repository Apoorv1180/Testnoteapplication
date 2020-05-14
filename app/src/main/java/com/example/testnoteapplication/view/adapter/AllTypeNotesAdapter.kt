package com.example.testnoteapplication.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.testnoteapplication.R
import com.example.testnoteapplication.Util.NoteUtil
import com.example.testnoteapplication.data.model.AllNotesModel
import com.ms.square.android.expandabletextview.ExpandableTextView
import kotlinx.android.synthetic.main.adapter_all_type_expandable_notes.view.*

class AllTypeNotesAdapter(var allNotes: List<AllNotesModel>,private val listenerSub: (AllNotesModel) -> Unit) :
    RecyclerView.Adapter<AllTypeNotesAdapter.AllTypesNotesHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTypesNotesHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_all_type_expandable_notes, parent, false)
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
        holder.bind(note,listenerSub)
    }

    inner class AllTypesNotesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var allTypeNotes: AllNotesModel
        private val noteTitle: TextView = itemView.findViewById<TextView>(R.id.rvNoteTitle)
        private val noteDescription: ExpandableTextView = itemView.findViewById(R.id.rvNoteDescription)
        private val createdOn: TextView = itemView.findViewById<TextView>(R.id.rvCreatedOn)
        private val card: CardView = itemView.findViewById(R.id.card_id)
        private val eyeview: ImageView =itemView.findViewById(R.id.eyeview)


        fun bind(allTypeNotesModel: AllNotesModel,listenerSub: (AllNotesModel) -> Unit) {
            this.allTypeNotes = allTypeNotesModel
            this.noteTitle.text = this.allTypeNotes.noteTitle

            if(allTypeNotesModel.noteType.equals(NoteUtil.SUB)){
                this.createdOn.text = this.allTypeNotes.expiryDate

            }else
            this.createdOn.text = this.allTypeNotes.createdOn

            itemView.eyeview.setOnClickListener {

                listenerSub(allTypeNotes)
            }


            if(allTypeNotesModel.card_color.equals(1)) {
                this.card.setCardBackgroundColor(context.resources.getColor(R.color.colornote))
                this.card.radius = 15f
            }
            else if(allTypeNotesModel.card_color.equals(2)) {
                this.card.setCardBackgroundColor(context.resources.getColor(R.color.colorsubs))
                this.card.radius = 15f
            }
            else{
                    this.card.setCardBackgroundColor(context.resources.getColor(R.color.colorlist))
                    this.card.radius = 15f
            }
            if(allTypeNotesModel.noteType.equals(NoteUtil.LIST)){
                this.noteDescription.text = NoteUtil.generateDescription(allTypeNotesModel.noteDescription)
            }
            else
                this.noteDescription.text = this.allTypeNotes.noteDescription

        }
    }
}




