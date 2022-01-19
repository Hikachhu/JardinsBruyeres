package com.example.gauche.database.component

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import com.example.gauche.database.listComponent.ListComponent

@Entity(
    tableName = "Alerte_table",
)
class Alerte(
    @field:ColumnInfo(name = "ID",index = true)  @field:PrimaryKey(autoGenerate = true) var ID: Int?,
    @field:ColumnInfo(name = "TyoeAlerte",index = true) var TyoeAlerte: Int,
    @field:ColumnInfo(name = "ComposantCible",index = true) var ComposantCible: Int,
    @field:ColumnInfo(name = "Seuil",index = true) var Seuil: Int,
)
{
    constructor(TyoeAlerte: Int, ComposantCible:Int, Seuil:Int):this(null,
        TyoeAlerte,ComposantCible,Seuil)
}