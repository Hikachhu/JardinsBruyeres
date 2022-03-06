package fr.JardinBruyere.gauche.database.station

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.JardinBruyere.gauche.R
import java.text.SimpleDateFormat
import java.util.*


class StationAdapter internal constructor(context: Context) : RecyclerView.Adapter<StationAdapter.BacViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var listComponent = emptyList<Station>() // Cached copy of words

    inner class BacViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BacViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return BacViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BacViewHolder, position: Int) {
        val current = listComponent[position]
        holder.wordItemView.text =current.Id.toString()+" | "+current.Name
        holder.wordItemView.setTextColor(Color.WHITE);
    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setWords(words: List<Station>) {
        this.listComponent = words
        notifyDataSetChanged()
    }

    fun getWordAtPosition(position: Int): Station {
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