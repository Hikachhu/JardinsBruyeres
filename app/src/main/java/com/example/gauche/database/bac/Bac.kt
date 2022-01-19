package com.example.gauche.database.component

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import com.example.gauche.database.listComponent.ListComponent

@Entity(
    tableName = "Bac_table",
)
class Bac(
    @field:ColumnInfo(name = "ID",index = true)  @field:PrimaryKey(autoGenerate = true) var ID: Int?,
    @field:ColumnInfo(name = "X",index = true) var X: Int,
    @field:ColumnInfo(name = "Y",index = true) var Y: Int,
    @field:ColumnInfo(name = "Etage",index = true) var Etage: Int,
    @field:ColumnInfo(name = "NomBac",index = true) var NomBac: String,
)
{
    constructor(X: Int, Y:Int, Etage:Int, NomBac:String):this(null,
        X,Y,Etage,NomBac)
}