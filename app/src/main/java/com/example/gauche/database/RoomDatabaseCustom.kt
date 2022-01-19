package com.example.gauche.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import kotlin.jvm.Volatile
import kotlinx.coroutines.CoroutineScope
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.gauche.database.component.*
import com.example.gauche.database.listComponent.ListComponent
import com.example.gauche.database.listComponent.ListComponentDao
import com.example.gauche.database.relevesCapteurs.RelevesCapteurs
import com.example.gauche.database.relevesCapteurs.RelevesCapteursDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [  Component::class,
                        ListComponent::class,
                        RelevesCapteurs::class,
                        ListeTypeAlerte::class,
                        Alerte::class,
                        Bac::class], version = 2, exportSchema = false)
abstract class RoomDatabaseCustom : RoomDatabase() {

    abstract fun ComponentDao(): ComponentDao
    abstract fun ListComponentDao(): ListComponentDao
    abstract fun RelevesCapteursDao(): RelevesCapteursDao
    abstract fun ListeTypeAlerteDao(): ListeTypeAlerteDao
    abstract fun AlerteDao(): AlerteDao
    abstract fun BacDao(): BacDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDatabaseCustom? = null

        fun getDatabase(context: Context, scope: CoroutineScope): RoomDatabaseCustom {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabaseCustom::class.java,
                    "word_database"
                )

                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                INSTANCE?.let {
                    scope.launch(Dispatchers.IO) {
                    }
                }
            }
        }

    }

}