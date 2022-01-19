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


class AlerteViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: AlerteRepository
    val allWords: LiveData<List<Alerte>>

    init {
        val wordsDao = RoomDatabaseCustom.getDatabase(application, scope).AlerteDao()
        repository = AlerteRepository(wordsDao)
        allWords = repository.allWords
    }

    fun insert(word: Alerte) = scope.launch(Dispatchers.IO) {
        repository.insert(word)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun deleteAll() {
        repository.deleteAll()
    }

    fun deleteWord(word: Alerte) {
        repository.deleteListComponent(word)
    }

}