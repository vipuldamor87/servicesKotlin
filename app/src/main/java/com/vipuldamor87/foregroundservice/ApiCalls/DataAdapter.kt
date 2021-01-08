package com.vipuldamor87.foregroundservice.ApiCalls

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vipuldamor87.foregroundservice.R
import kotlinx.android.synthetic.main.rawitems.view.*

class DataAdapter(val context: Context, val data:List<Data>) : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.imageView)
        var firstName = itemView.findViewById<TextView>(R.id.firstName)
        var lastName = itemView.findViewById<TextView>(R.id.lastName)
        var email = itemView.findViewById<TextView>(R.id.email)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
      val view = LayoutInflater.from(context).inflate(R.layout.rawitems,parent,false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
       val data  = data[position]
        holder.firstName.text = data.first_name
        holder.lastName.text = data.last_name
        holder.email.text = data.email
        Glide.with(context).load(data.avatar).into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return data.size

    }
}