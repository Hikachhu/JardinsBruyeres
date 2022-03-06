package fr.JardinBruyere.gauche.database.sensorTypes

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SensorTypesDao {
    @get:Query("SELECT * FROM ListComponent_table ORDER BY Id ASC")
    val alphabetizedWords: LiveData<List<SensorTypes>>

    @get:Query("SELECT Unit FROM ListComponent_table ORDER BY Id ASC")
    val alphabetizedWordsContent: LiveData<List<String>>

    @get:Query("SELECT count(*) FROM ListComponent_table")
    val size:LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sensorTypes: SensorTypes?)

    @Query("SELECT Unit FROM ListComponent_table where Id=:numberListComponent")
    fun getSpecificListComponent(numberListComponent: Int?): String?

    @Query("DELETE FROM ListComponent_table")
    fun deleteAll()

    @Delete
    fun deleteWord(sensorTypes: SensorTypes?)
}