package fr.JardinBruyere.gauche.database.sensor

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import fr.JardinBruyere.gauche.database.RoomDatabaseCustom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class SensorViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: SensorRepository
    val allWords: LiveData<List<Sensor>>

    init {
        val wordsDao = RoomDatabaseCustom.getDatabase(application, scope).ComponentDao()
        repository = SensorRepository(wordsDao)
        allWords = repository.allWords
    }

    fun insert(word: Sensor) = scope.launch(Dispatchers.IO) {
        repository.insert(word)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun deleteAll() {
        repository.deleteAll()
    }

    fun deleteWord(word: Sensor) {
        repository.deleteListComponent(word)
    }

}