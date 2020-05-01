package com.example.testnoteapplication.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.testnoteapplication.R
import com.example.testnoteapplication.data.model.NotesModel

class AllTypeNotesAdapter(var allNotes: List<NotesModel>) :
    RecyclerView.Adapter<AllTypesNotesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTypesNotesHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_all_type_notes, parent, false)
        return AllTypesNotesHolder(view)
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    override fun onBindViewHolder(holder: AllTypesNotesHolder, position: Int) {
        val note = allNotes[position]
        holder.bind(note)
    }
}

class AllTypesNotesHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    private lateinit var allTypeNotes: NotesModel

    private val noteTitle: TextView = view.findViewById<TextView>(R.id.rvNoteTitle)
    private val noteDescription: TextView = view.findViewById<TextView>(R.id.rvNoteDescription)

    fun bind(allTypeNotesModel: NotesModel) {
        this.allTypeNotes = allTypeNotesModel
        this.noteTitle.text = this.allTypeNotes.noteTitle
        this.noteDescription.text = this.allTypeNotes.noteDescription
    }

    override fun onClick(v: View) {
        Toast.makeText(v.context, "${allTypeNotes.noteTitle} clicked!", Toast.LENGTH_SHORT)
            .show()
    }
}


