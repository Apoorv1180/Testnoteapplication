package com.example.testnoteapplication.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testnoteapplication.R
import com.example.testnoteapplication.data.model.ListModel
import java.util.ArrayList

class AddListAdapter(val userList: ArrayList<ListModel>) : RecyclerView.Adapter<AddListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tvName)
        // val checkbox = itemView.findViewById<TextView>(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.list_adapter_item, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(parent: ViewHolder, p1: Int) {
        parent.name?.text = userList[p1].name
        // parent.count?.text = userList[p1].count.toString()
    }

}