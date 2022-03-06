package fr.JardinBruyere.gauche.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import kotlin.jvm.Volatile
import kotlinx.coroutines.CoroutineScope
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import fr.JardinBruyere.gauche.database.*
import fr.JardinBruyere.gauche.database.sensor.Sensor
import fr.JardinBruyere.gauche.database.sensor.SensorDao
import fr.JardinBruyere.gauche.database.sensorReading.SensorReading
import fr.JardinBruyere.gauche.database.sensorReading.SensorReadingDao
import fr.JardinBruyere.gauche.database.sensorTypes.SensorTypes
import fr.JardinBruyere.gauche.database.sensorTypes.SensorTypesDao
import fr.JardinBruyere.gauche.database.station.Station
import fr.JardinBruyere.gauche.database.station.StationDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [  Sensor::class,
                        SensorTypes::class,
                        SensorReading::class,
                        Station::class], version = 1, exportSchema = false)
abstract class RoomDatabaseCustom : RoomDatabase() {

    abstract fun ComponentDao(): SensorDao
    abstract fun ListComponentDao(): SensorTypesDao
    abstract fun RelevesCapteursDao(): SensorReadingDao
    abstract fun BacDao(): StationDao

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