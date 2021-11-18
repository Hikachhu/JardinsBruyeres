package com.example.gauche.database.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gauche.R
import com.example.gauche.database.listComponent.ListComponent
import com.example.gauche.database.listComponent.ListComponentViewHolder


class ComponentAdapter internal constructor(context: Context) : RecyclerView.Adapter<ComponentAdapter.ComponentViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var listComponent = emptyList<Component>() // Cached copy of words

    inner class ComponentViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return ComponentViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ComponentViewHolder, position: Int) {
        val current = listComponent[position]
        holder.wordItemView.text = current.ID.toString() +" | "+current.dateAjout.toString()+" | "+current.type.toString()+" | "+current.name
        holder.wordItemView.setTextColor(Color.WHITE);
    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setWords(words: List<Component>) {
        this.listComponent = words
        notifyDataSetChanged()
    }

    fun getWordAtPosition(position: Int): Component {
        return listComponent[position]
    }


    override fun getItemCount() = listComponent.size
}