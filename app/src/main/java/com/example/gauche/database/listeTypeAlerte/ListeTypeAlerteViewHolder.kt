/*
 * Copyright (C) 2020 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.gauche.database.listeTypeAlerte

import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.gauche.R
import com.example.gauche.database.listComponent.ListComponentViewHolder

class ListeTypeAlerteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val wordItemView: TextView = itemView.findViewById(R.id.textView)
    fun bind(text: String?) {
        wordItemView.text = text
    }

    companion object {
        fun create(parent: ViewGroup): ListeTypeAlerteViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
            return ListeTypeAlerteViewHolder(view)
        }
    }

}