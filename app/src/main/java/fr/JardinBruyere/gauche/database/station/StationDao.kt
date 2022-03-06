package fr.JardinBruyere.gauche.database.station

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StationDao {
    @get:Query("SELECT * FROM Bac_table")
    val alphabetizedWords: LiveData<List<Station>>

    @get:Query("SELECT count(*) FROM Component_table")
    val size: Int?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(component: Station?)

    @Query("SELECT * FROM Bac_table where ID=:IDComponent")
    fun getSpecificListComponent(IDComponent: Int?): Station?

    @Query("DELETE FROM Bac_table")
    fun deleteAll()

    @Delete
    fun deleteWord(component: Station?)
}