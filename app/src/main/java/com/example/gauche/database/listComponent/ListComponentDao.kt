package com.example.gauche.database.listComponent

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gauche.database.listComponent.ListComponent

@Dao
interface ListComponentDao {
    @get:Query("SELECT * FROM ListComponent_table ORDER BY typComponent ASC")
    val alphabetizedWords: LiveData<List<ListComponent>>

    @get:Query("SELECT nameComponent FROM ListComponent_table ORDER BY typComponent ASC")
    val alphabetizedWordsContent: LiveData<List<String>>

    @get:Query("SELECT count(*) FROM ListComponent_table")
    val size:LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(listComponent: ListComponent?)

    @Query("SELECT nameComponent FROM ListComponent_table where typComponent=:numberListComponent")
    fun getSpecificListComponent(numberListComponent: Int?): String?

    @Query("DELETE FROM ListComponent_table")
    fun deleteAll()

    @Delete
    fun deleteWord(listComponent: ListComponent?)
}