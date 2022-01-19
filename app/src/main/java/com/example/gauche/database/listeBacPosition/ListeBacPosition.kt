package com.example.gauche.database.component

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import com.example.gauche.database.listComponent.ListComponent

@Entity(
    tableName = "ListeBacPosition_table",
)
class ListeBacPosition(
    @field:ColumnInfo(name = "ID",index = true)  @field:PrimaryKey(autoGenerate = true) var ID: Int?,
    @field:ColumnInfo(name = "Capteur",index = true) var Capteur: Int,
    @field:ColumnInfo(name = "Bac",index = true) var Bac: Int,
)
{
    constructor(Capteur: Int, Bac:Int):this(null,
        Capteur,Bac)
}