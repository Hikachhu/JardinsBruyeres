package com.example.gauche.database.listComponent

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gauche.R


class ListComponentAdapter internal constructor(context: Context) : RecyclerView.Adapter<ListComponentAdapter.ListComponentViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var listComponent = emptyList<ListComponent>() // Cached copy of words

    inner class ListComponentViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListComponentViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return ListComponentViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListComponentViewHolder, position: Int) {
        val current = listComponent[position]
        holder.wordItemView.text = current.nameComponent+" | "+current.ID+" | "+current.typeComposant
        holder.wordItemView.setTextColor(Color.WHITE);
    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setWords(words: List<ListComponent>) {
        this.listComponent = words
        notifyDataSetChanged()
    }

    fun getWordAtPosition(position: Int): ListComponent {
        return listComponent[position]
    }


    override fun getItemCount() = listComponent.size
}