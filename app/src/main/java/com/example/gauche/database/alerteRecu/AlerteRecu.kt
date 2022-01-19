package com.example.gauche.database.component

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import com.example.gauche.database.listComponent.ListComponent

@Entity(
    tableName = "AlerteRecu_table",
)
class AlerteRecu(
    @field:ColumnInfo(name = "ID",index = true)  @field:PrimaryKey(autoGenerate = true) var ID: Int?,
    @field:ColumnInfo(name = "DateAjout",index = true) var DateAjout: Long,
    @field:ColumnInfo(name = "NumeroAlerte",index = true) var NumeroAlerte: Int,
)
{
    constructor(DateAjout: Long, NumeroAlerte:Int):this(null,
        DateAjout,NumeroAlerte)
}