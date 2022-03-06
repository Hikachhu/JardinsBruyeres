package fr.JardinBruyere.gauche.database.station

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "Bac_table",
)
class Station(
    @field:ColumnInfo(name = "Id",index = true)  @field:PrimaryKey var Id: Int?,
    @field:ColumnInfo(name = "Name",index = true) var Name: String
)