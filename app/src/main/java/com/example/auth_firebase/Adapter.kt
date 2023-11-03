package com.example.auth_firebase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val userlist:ArrayList<Userlist>) : RecyclerView.Adapter<Adapter.viewholder>() {
    class viewholder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val name = itemview.findViewById<TextView>(R.id.tname)
        val email = itemview.findViewById<TextView>(R.id.temail)
        val phone = itemview.findViewById<TextView>(R.id.tphone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list, parent, false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val data = userlist[position]
        holder.name.text=data.name
        holder.email.text=data.email
        holder.phone.text=data.phone
    }

    override fun getItemCount(): Int {
        return userlist.size

    }
}