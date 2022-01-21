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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(relevesCapteurs: RelevesCapteurs?)

    @Query("DELETE FROM RelevesCapteurs_table")
    fun deleteAll()

    @Query("SELECT * FROM RelevesCapteurs_table WHERE IdCapteur=:position ORDER BY ID ASC")
    fun allWordsOfCapteur (position:Int):LiveData<List<RelevesCapteurs>>

    @Delete
    fun deleteWord(relevesCapteurs: RelevesCapteurs?)
}