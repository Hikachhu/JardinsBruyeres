package com.example.gauche.database.component

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.gauche.database.listComponent.ListComponent

@Entity(
    tableName = "Component_table",
    foreignKeys = [ForeignKey(
        entity = ListComponent::class,
        parentColumns = ["typComponent"],
        childColumns = ["type"],
        onDelete = ForeignKey.NO_ACTION
    )],
)
class Component(
    @field:ColumnInfo(name = "ID",index = true)  @field:PrimaryKey(autoGenerate = true) var ID: Int?,
    @field:ColumnInfo(name = "type",index = true) var type: Int,
    @field:ColumnInfo(name = "name",index = true) var name: String,
    @field:ColumnInfo(name = "dateAjout",index = true) var dateAjout: Long,
    @field:ColumnInfo(name = "position",index = true) var position: Int
)
{
    constructor(type: Int,name: String, dateAjout: Long, position:Int):this(null,
        type,name,dateAjout,position)
}