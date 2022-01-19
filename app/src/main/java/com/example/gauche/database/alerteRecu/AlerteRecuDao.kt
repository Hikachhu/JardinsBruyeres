package com.example.gauche.database.component

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gauche.database.component.Component
import com.example.gauche.database.listComponent.ListComponent

@Dao
interface AlerteRecuDao {
    @get:Query("SELECT * FROM AlerteRecu_table")
    val alphabetizedWords: LiveData<List<AlerteRecu>>

    @get:Query("SELECT count(*) FROM Component_table")
    val size: Int?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(component: AlerteRecu?)

    @Query("SELECT * FROM AlerteRecu_table where ID=:IDComponent")
    fun getSpecificListComponent(IDComponent: Int?): AlerteRecu?

    @Query("DELETE FROM AlerteRecu_table")
    fun deleteAll()

    @Delete
    fun deleteWord(component: AlerteRecu?)
}