package fr.JardinBruyere.gauche.database.sensor

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SensorDao {
    @get:Query("SELECT * FROM Component_table")
    val alphabetizedWords: LiveData<List<Sensor>>

    @get:Query("SELECT count(*) FROM Component_table")
    val size: Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(component: Sensor?)

    @Query("SELECT * FROM Component_table where ID=:IDComponent")
    fun getSpecificListComponent(IDComponent: Int?): Sensor?

    @Query("DELETE FROM Component_table")
    fun deleteAll()

    @Delete
    fun deleteWord(component: Sensor?)
}