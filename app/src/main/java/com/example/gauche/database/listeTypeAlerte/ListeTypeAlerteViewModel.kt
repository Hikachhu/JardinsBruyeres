package com.example.gauche.database.component

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.gauche.database.RoomDatabaseCustom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class ListeTypeAlerteViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: ListeTypeAlerteRepository
    val allWords: LiveData<List<ListeTypeAlerte>>

    init {
        val wordsDao = RoomDatabaseCustom.getDatabase(application, scope).ListeTypeAlerteDao()
        repository = ListeTypeAlerteRepository(wordsDao)
        allWords = repository.allWords
    }

    fun insert(word: ListeTypeAlerte) = scope.launch(Dispatchers.IO) {
        repository.insert(word)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun deleteAll() {
        repository.deleteAll()
    }

    fun deleteWord(word: ListeTypeAlerte) {
        repository.deleteListComponent(word)
    }

}