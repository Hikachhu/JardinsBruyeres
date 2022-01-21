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
import com.example.gauche.database.listeTypeAlerte.ListeTypeAlerteViewHolder
import java.text.SimpleDateFormat
import java.util.*


class ListeTypeAlerteAdapter internal constructor(context: Context) : RecyclerView.Adapter<ListeTypeAlerteAdapter.ListeTypeAlerteViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var listComponent = emptyList<ListeTypeAlerte>() // Cached copy of words

    inner class ListeTypeAlerteViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListeTypeAlerteViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return ListeTypeAlerteViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListeTypeAlerteViewHolder, position: Int) {
        val current = listComponent[position]
        holder.wordItemView.text =current.TypeAlerte.toString()+" | "+current.Criticite.toString()+" | "+current.MethodeNotification
        holder.wordItemView.setTextColor(Color.WHITE);
    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setWords(words: List<ListeTypeAlerte>) {
        this.listComponent = words
        notifyDataSetChanged()
    }

    fun getWordAtPosition(position: Int): ListeTypeAlerte {
        return listComponent[position]
    }


    override fun getItemCount() = listComponent.size



    @SuppressLint("SimpleDateFormat")
    fun getDate(milliSeconds: Long, dateFormat: String?): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }
}