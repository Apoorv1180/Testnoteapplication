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
import com.example.testnoteapplication.data.db.async.DeleteSubscriptionTask
import com.example.testnoteapplication.data.db.async.UndoSubscriptionTask
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.viewmodel.AllSubscriptionViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class AllSubscriptionAdapter(var allSubscription: List<AllNotesModel>,private val listenerSub: (AllNotesModel) -> Unit) :
    RecyclerView.Adapter<AllSubscriptionAdapter.AllSubscriptionHolder>() {
    private var removedPosition: Int = 0
    private lateinit var removedNote: AllNotesModel
    private lateinit var context: Context
    val icons_array = intArrayOf(R.drawable.disney, R.drawable.googleplay, R.drawable.hbo, R.drawable.hulu, R.drawable.netflix, R.drawable.primevideo, R.drawable.ic_add)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllSubscriptionHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_subscription_card_view, parent, false)
        context = parent.context
        return AllSubscriptionHolder(view)
    }

    override fun getItemCount(): Int {
        Log.e("ADAPTER", allSubscription.size.toString())
        return allSubscription.size
    }

    inner class AllSubscriptionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var allTypeNotes: AllNotesModel
        private val subTitle: TextView = itemView.findViewById<TextView>(R.id.cv_sub_title)
       // private val subIcon:
        private val subDescription: TextView = itemView.findViewById<TextView>(R.id.cv_sub_des)
        private val expiryDate: TextView = itemView.findViewById<TextView>(R.id.cv_expire_date)
        private val card: CardView = itemView.findViewById(R.id.card_sub)
        private val icon: ImageView = itemView.findViewById(R.id.sub_icon)

        fun bind(notesModel: AllNotesModel,
                 listenerSub: (AllNotesModel) -> Unit) {
            this.allTypeNotes = notesModel
            this.subTitle.text = this.allTypeNotes.noteTitle
            this.subDescription.text = this.allTypeNotes.noteDescription
            this.expiryDate.text = this.allTypeNotes.expiryDate
            this.card.setCardBackgroundColor(context.resources.getColor(R.color.colorsubs))
            this.card.radius = 15f

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Note Single clicked!", Toast.LENGTH_SHORT)
                        .show()
                //callbackInterface.passDataCallback(allTypeNotes)

                listenerSub(allTypeNotes)
            }

            Picasso.get().load(icons_array[notesModel.sub_icon])
                    .error(R.drawable.ic_launcher_background)
                    .into(icon)
        }

    }

    fun removeItem(
        position: Int,
        viewHolder: RecyclerView.ViewHolder,
        viewModel: AllSubscriptionViewModel
    ) {
        removedNote = allSubscription[position]
        removedPosition = position
        DeleteSubscriptionTask(context, viewModel, removedNote).execute()

    }

    override fun onBindViewHolder(
        holder: AllSubscriptionHolder,
        position: Int
    ) {
        val note = allSubscription[position]
        Log.e("ADAPTER", "Adapter called")
        holder.bind(note,listenerSub)
    }
}


