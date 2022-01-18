package com.example.gauche.database.relevesCapteurs

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gauche.R
import com.example.gauche.database.component.ComponentAdapter
import java.text.SimpleDateFormat
import java.util.*


class RelevesCapteursAdapter internal constructor(context: Context) : RecyclerView.Adapter<RelevesCapteursAdapter.ListComponentViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var listComponent = emptyList<RelevesCapteurs>() // Cached copy of words

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
        holder.wordItemView.text = getDate(current.dateAjout,"dd/MM/yyyy hh:mm:ss")+" | "+current.IdCapteur+ " | "+current.valeur
        holder.wordItemView.setTextColor(Color.WHITE);
    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setWords(words: List<RelevesCapteurs>) {
        this.listComponent = words
        notifyDataSetChanged()
    }

    fun getWordAtPosition(position: Int): RelevesCapteurs {
        return listComponent[position]
    }


    override fun getItemCount() = listComponent.size


    fun getDate(milliSeconds: Long, dateFormat: String?): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(milliSeconds)
        return formatter.format(calendar.getTime())
    }
}