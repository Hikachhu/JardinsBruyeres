package fr.JardinBruyere.gauche.database.sensorTypes

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.JardinBruyere.gauche.R


class SensorTypesAdapter internal constructor(context: Context) : RecyclerView.Adapter<SensorTypesAdapter.ListComponentViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var listComponent = emptyList<SensorTypes>() // Cached copy of words

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
        holder.wordItemView.text = current.Id.toString()+" | "+current.Unit
        holder.wordItemView.setTextColor(Color.WHITE);
    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setWords(words: List<SensorTypes>) {
        this.listComponent = words
        notifyDataSetChanged()
    }

    fun getWordAtPosition(position: Int): SensorTypes {
        return listComponent[position]
    }


    override fun getItemCount() = listComponent.size
}