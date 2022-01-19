package com.example.gauche.database.component

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gauche.database.component.Component
import com.example.gauche.database.listComponent.ListComponent

@Dao
interface BacDao {
    @get:Query("SELECT * FROM Bac_table")
    val alphabetizedWords: LiveData<List<Bac>>

    @get:Query("SELECT count(*) FROM Component_table")
    val size: Int?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(component: Bac?)

    @Query("SELECT * FROM Bac_table where ID=:IDComponent")
    fun getSpecificListComponent(IDComponent: Int?): Bac?

    @Query("DELETE FROM Bac_table")
    fun deleteAll()

    @Delete
    fun deleteWord(component: Bac?)
}