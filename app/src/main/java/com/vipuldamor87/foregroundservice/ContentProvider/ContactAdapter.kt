package com.vipuldamor87.foregroundservice.ContentProvider

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vipuldamor87.foregroundservice.R
import kotlinx.android.synthetic.main.contactchild.view.*


class ContactAdapter(items: List<ContactDTO>, ctx: Context) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    private var list = items
    private var context = ctx
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ViewHolder {
        return  ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.contactchild,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ContactAdapter.ViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.number.text = list[position].number
        holder.itemView.setOnClickListener {
            var name = list[position].name
            var number = list[position].number
            val i = Intent(context, ContactDetails::class.java)
            i.putExtra("name", "$name")
            i.putExtra("number", "$number")
            context.startActivity(i)
        }

    }

    fun removeItem(viewHolder: ViewHolder){

    }

    override fun getItemCount(): Int {
        return list.size
    }



    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val name = v.TvName!!
        val number = v.TvNum
    }
}