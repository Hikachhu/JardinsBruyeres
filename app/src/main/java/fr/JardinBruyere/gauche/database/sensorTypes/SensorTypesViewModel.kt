package fr.JardinBruyere.gauche.database.sensorTypes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import fr.JardinBruyere.gauche.database.RoomDatabaseCustom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class SensorTypesViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: SensorTypesRepository
    val allWords: LiveData<List<SensorTypes>>
    val size: LiveData<Int>
    val allWordsContent: LiveData<List<String>>

    init {
        val wordsDao = RoomDatabaseCustom.getDatabase(application, scope).ListComponentDao()
        repository = SensorTypesRepository(wordsDao)
        allWords = repository.allWords
        allWordsContent=repository.allWordsContent
        size=repository.size
    }

    fun insert(word: SensorTypes) = scope.launch(Dispatchers.IO) {
        repository.insert(word)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun deleteAll() {
        repository.deleteAll()
    }

    fun deleteWord(word: SensorTypes) {
        repository.deleteListComponent(word)
    }

}