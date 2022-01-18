package com.example.gauche.database.relevesCapteurs

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.NO_ACTION
import com.example.gauche.database.component.Component

/*
 * Copyright (C) 2017 Google Inc.
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
 */ /**
 * A basic class representing an entity that is a row in a one-column database table.
 *
 * @ Entity - You must annotate the class as an entity and supply a table name if not class name.
 * @ PrimaryKey - You must identify the primary key.
 * @ ColumnInfo - You must supply the column name if it is different from the variable name.
 *
 * See the documentation for the full rich set of annotations.
 * https://developer.android.com/topic/libraries/architecture/room.html
 */
@Entity(
    tableName = "RelevesCapteurs_table",
    foreignKeys = [ForeignKey(
        entity = Component::class,
        parentColumns = ["ID"],
        childColumns = ["IdCapteur"],

        onDelete= NO_ACTION
    )]
)
class RelevesCapteurs(
    @field:ColumnInfo(name = "ID",index = true) @field:PrimaryKey(autoGenerate = true) var ID: Int?,
    @field:ColumnInfo(name = "IdCapteur",index = true) var IdCapteur: Int,
    @field:ColumnInfo(name = "dateAjout",index = true) var dateAjout: Long,
    @field:ColumnInfo(name = "valeur",index = true) var valeur: Int
)
{
    constructor(IdCapteur: Int,dateAjout:Long,valeur:Int):this(null,
        IdCapteur,dateAjout,valeur)
}