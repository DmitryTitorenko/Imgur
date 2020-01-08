package com.example.imgur.view.links

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.imgur.R

class LinksAdapter(private val linksList:List<String>,
                   var onItemClick: ((String) -> Unit)):RecyclerView.Adapter<LinksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val title = LayoutInflater.from(parent.context)
            .inflate(R.layout.link, parent, false) as TextView
        return ViewHolder(title).apply {
            title.setOnClickListener {
                onItemClick(linksList[adapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.link.text = linksList[position]
    }

    override fun getItemCount() = linksList.size

    class ViewHolder(val link: TextView) : RecyclerView.ViewHolder(link)

}