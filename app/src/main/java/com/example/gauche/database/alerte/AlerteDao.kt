package com.example.gauche.database.component

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gauche.database.component.Component
import com.example.gauche.database.listComponent.ListComponent

@Dao
interface AlerteDao {
    @get:Query("SELECT * FROM Alerte_table")
    val alphabetizedWords: LiveData<List<Alerte>>

    @get:Query("SELECT count(*) FROM Component_table")
    val size: Int?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(component: Alerte?)

    @Query("SELECT * FROM Alerte_table where ID=:IDComponent")
    fun getSpecificListComponent(IDComponent: Int?): Alerte?

    @Query("DELETE FROM Alerte_table")
    fun deleteAll()

    @Delete
    fun deleteWord(component: Alerte?)
}