package com.example.testnoteapplication.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.testnoteapplication.R
import com.example.testnoteapplication.data.model.AllNotesModel

class AllTypeNotesAdapter(var allNotes: List<AllNotesModel>) :
    RecyclerView.Adapter<AllTypesNotesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTypesNotesHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_all_type_notes, parent, false)
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
}

class AllTypesNotesHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    private lateinit var allTypeNotes: AllNotesModel

    private val noteTitle: TextView = view.findViewById<TextView>(R.id.rvNoteTitle)
    private val noteDescription: TextView = view.findViewById<TextView>(R.id.rvNoteDescription)
    private val createdOn: TextView = view.findViewById<TextView>(R.id.rvCreatedOn)

    fun bind(allTypeNotesModel: AllNotesModel) {
        this.allTypeNotes = allTypeNotesModel
        this.noteTitle.text = this.allTypeNotes.noteTitle
        this.noteDescription.text = this.allTypeNotes.noteDescription
       this.createdOn.text = this.allTypeNotes.createdOn
    }

    override fun onClick(v: View) {
        Toast.makeText(v.context, "${allTypeNotes.noteTitle} clicked!", Toast.LENGTH_SHORT)
            .show()
        Log.e("Listner","Clicked Note")
    }
}


