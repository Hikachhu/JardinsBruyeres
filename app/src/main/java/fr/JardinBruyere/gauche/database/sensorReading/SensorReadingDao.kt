package fr.JardinBruyere.gauche.database.sensorReading

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SensorReadingDao {
    @get:Query("SELECT * FROM RelevesCapteurs_table ORDER BY ID ASC")
    val alphabetizedWords: LiveData<List<SensorReading>>

    @get:Query("SELECT count(*) FROM RelevesCapteurs_table")
    val size: Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sensorReading: SensorReading?)

    @Query("DELETE FROM RelevesCapteurs_table")
    fun deleteAll()

    @Query("SELECT * FROM RelevesCapteurs_table WHERE SensorId=:position ORDER BY ID ASC")
    fun allWordsOfCapteur (position:Int):List<SensorReading>

    @Delete
    fun deleteWord(sensorReading: SensorReading?)
}