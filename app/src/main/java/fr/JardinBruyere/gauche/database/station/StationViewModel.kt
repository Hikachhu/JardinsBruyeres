package fr.JardinBruyere.gauche.database.station

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import fr.JardinBruyere.gauche.database.RoomDatabaseCustom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class StationViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: StationRepository
    val allWords: LiveData<List<Station>>

    init {
        val wordsDao = RoomDatabaseCustom.getDatabase(application, scope).BacDao()
        repository = StationRepository(wordsDao)
        allWords = repository.allWords
    }

    fun insert(word: Station) = scope.launch(Dispatchers.IO) {
        repository.insert(word)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun deleteAll() {
        repository.deleteAll()
    }

    fun deleteWord(word: Station) {
        repository.deleteListComponent(word)
    }

}