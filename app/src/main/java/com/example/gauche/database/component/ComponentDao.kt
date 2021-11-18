package com.example.gauche.database.component

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gauche.database.component.Component
import com.example.gauche.database.listComponent.ListComponent

@Dao
interface ComponentDao {
    @get:Query("SELECT * FROM Component_table")
    val alphabetizedWords: LiveData<List<Component>>

    @get:Query("SELECT count(*) FROM Component_table")
    val size: Int?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(component: Component?)

    @Query("SELECT * FROM Component_table where ID=:IDComponent")
    fun getSpecificListComponent(IDComponent: Int?): Component?

    @Query("DELETE FROM Component_table")
    fun deleteAll()

    @Delete
    fun deleteWord(component: Component?)
}