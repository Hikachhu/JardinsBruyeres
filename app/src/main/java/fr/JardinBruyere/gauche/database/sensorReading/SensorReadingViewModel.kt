package fr.JardinBruyere.gauche.database.sensorReading

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import fr.JardinBruyere.gauche.database.RoomDatabaseCustom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class SensorReadingViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: SensorReadingRepository
    val allWords: LiveData<List<SensorReading>>

    init {
        val wordsDao = RoomDatabaseCustom.getDatabase(application, scope).RelevesCapteursDao()
        repository = SensorReadingRepository(wordsDao)
        allWords = repository.allWords
    }

    fun getSensorReading(sensor:Int): List<SensorReading> {
        return repository.getCapteur(sensor)
    }

    fun insert(word: SensorReading) = scope.launch(Dispatchers.IO) {
        repository.insert(word)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun deleteAll() {
        repository.deleteAll()
    }

    fun deleteWord(word: SensorReading) {
        repository.deleteListComponent(word)
    }
}