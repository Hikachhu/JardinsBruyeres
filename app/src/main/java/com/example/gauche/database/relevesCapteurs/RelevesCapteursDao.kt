package com.example.gauche.database.relevesCapteurs

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gauche.database.listComponent.ListComponent

@Dao
interface RelevesCapteursDao {
    @get:Query("SELECT * FROM RelevesCapteurs_table ORDER BY ID ASC")
    val alphabetizedWords: LiveData<List<RelevesCapteurs>>

    @get:Query("SELECT count(*) FROM RelevesCapteurs_table")
    val size: Int?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(listComponent: RelevesCapteurs?)

    @Query("SELECT nameComponent FROM ListComponent_table where typComponent=:numberListComponent")
    fun getSpecificListComponent(numberListComponent: Int?): String?

    @Query("DELETE FROM ListComponent_table")
    fun deleteAll()

    @Delete
    fun deleteWord(relevesCapteurs: RelevesCapteurs?)
}