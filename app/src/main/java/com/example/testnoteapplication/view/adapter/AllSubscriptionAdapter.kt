package com.example.testnoteapplication.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.testnoteapplication.R
import com.example.testnoteapplication.data.model.AllNotesModel

class AllSubscriptionAdapter (var allSubscription: List<AllNotesModel>) : RecyclerView.Adapter<AllSubscriptionAdapter.AllSubscriptionHolder>() {
    private var removedPosition: Int = 0
    private lateinit var removedNote: AllNotesModel
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllSubscriptionHolder {
        val view =
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.subscription_card_view, parent, false)
        context = parent.context
        return AllSubscriptionHolder(view)
    }

    override fun getItemCount(): Int {
        Log.e("ADAPTER", allSubscription.size.toString())
        return allSubscription.size
    }

    inner class AllSubscriptionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var allTypeNotes: AllNotesModel
        private val noteTitle: TextView = itemView.findViewById<TextView>(R.id.cv_sub_title)
        private val noteDescription: TextView = itemView.findViewById<TextView>(R.id.cv_sub_des)
        private val expiryDate: TextView = itemView.findViewById<TextView>(R.id.cv_expire_date)

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
            this.noteTitle.text = this.allTypeNotes.noteTitle
            this.noteDescription.text = this.allTypeNotes.noteDescription
            this.expiryDate.text = this.allTypeNotes.createdOn
        }
    }

    override fun onBindViewHolder(holder: AllSubscriptionAdapter.AllSubscriptionHolder, position: Int) {
        val note = allSubscription[position]
        Log.e("ADAPTER", "Adapter called")
        holder.bind(note)
    }
}


