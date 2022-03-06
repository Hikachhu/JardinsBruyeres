package fr.JardinBruyere.gauche.database.sensor

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "Component_table"
)
class Sensor(
    @field:ColumnInfo(name = "Id",index = true)  @field:PrimaryKey var Id: Int?,
    @field:ColumnInfo(name = "Type",index = true) var Type: Int,
    @field:ColumnInfo(name = "DateAdded",index = true) var DateAdded: Long,
    @field:ColumnInfo(name = "Station",index = true) var Station: Int,
    @field:ColumnInfo(name = "Name",index = true) var Name: String,
    @field:ColumnInfo(name = "MacAdress",index = true) var MacAdress: String
)