package com.example.gauche.database.component

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gauche.database.component.Component
import com.example.gauche.database.listComponent.ListComponent

@Dao
interface ListeTypeAlerteDao {
    @get:Query("SELECT * FROM ListeTypeAlerte_table")
    val alphabetizedWords: LiveData<List<ListeTypeAlerte>>

    @get:Query("SELECT count(*) FROM Component_table")
    val size: Int?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(component: ListeTypeAlerte?)

    @Query("SELECT * FROM ListeTypeAlerte_table where TypeAlerte=:IDComponent")
    fun getSpecificListComponent(IDComponent: Int?): ListeTypeAlerte?

    @Query("DELETE FROM ListeTypeAlerte_table")
    fun deleteAll()

    @Delete
    fun deleteWord(component: ListeTypeAlerte?)
}