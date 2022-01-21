package com.example.gauche.database.component

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import com.example.gauche.database.listComponent.ListComponent

@Entity(
    tableName = "ListeTypeAlerte_table",
)
class ListeTypeAlerte(
    @field:ColumnInfo(name = "TypeAlerte",index = true)  @field:PrimaryKey(autoGenerate = true) var TypeAlerte: Int?,
    @field:ColumnInfo(name = "Criticite",index = true) var Criticite: Int,
    @field:ColumnInfo(name = "MethodeNotification",index = true) var MethodeNotification: Int
)
{
    constructor(type: Int, position:Int):this(null,
        type,position)
}