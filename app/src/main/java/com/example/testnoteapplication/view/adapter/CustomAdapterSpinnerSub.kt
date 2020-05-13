package com.example.testnoteapplication.view.adapter

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.testnoteapplication.R
import com.squareup.picasso.Picasso
import java.security.AccessController.getContext

class CustomAdapterSpinnerSub(var context: Context, var icons: IntArray, var subscriptions: Array<String>) :
    BaseAdapter() {
    internal var inflter: LayoutInflater

    init {
        inflter = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return icons.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {

        val view = inflter.inflate(R.layout.spinner_custom_subscription,null)
        val icon = view.findViewById<View>(R.id.sub_icon) as ImageView?
        val names = view.findViewById<View>(R.id.sub_name) as TextView?
        Picasso.get().load(icons[i])
            .error(R.drawable.ic_launcher_background)
            .into(icon);
        names!!.text = subscriptions[i]
        return view
    }
}