package com.example.gauche.database.component

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gauche.database.component.Component
import com.example.gauche.database.listComponent.ListComponent

@Dao
interface ListeBacPositionDao {
    @get:Query("SELECT * FROM ListeBacPosition_table")
    val alphabetizedWords: LiveData<List<ListeBacPosition>>

    @get:Query("SELECT count(*) FROM Component_table")
    val size: Int?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(component: ListeBacPosition?)

    @Query("SELECT * FROM ListeBacPosition_table where ID=:IDComponent")
    fun getSpecificListComponent(IDComponent: Int?): ListeBacPosition?

    @Query("DELETE FROM ListeBacPosition_table")
    fun deleteAll()

    @Delete
    fun deleteWord(component: ListeBacPosition?)
}