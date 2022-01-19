package com.example.gauche.database.listComponent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.gauche.database.RoomDatabaseCustom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class ListComponentViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: ListComponentRepository
    val allWords: LiveData<List<ListComponent>>
    val size: LiveData<Int>
    val allWordsContent: LiveData<List<String>>

    init {
        val wordsDao = RoomDatabaseCustom.getDatabase(application, scope).ListComponentDao()
        repository = ListComponentRepository(wordsDao)
        allWords = repository.allWords
        allWordsContent=repository.allWordsContent
        size=repository.size
    }

    fun insert(word: ListComponent) = scope.launch(Dispatchers.IO) {
        repository.insert(word)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun deleteAll() {
        repository.deleteAll()
    }

    fun deleteWord(word: ListComponent) {
        repository.deleteListComponent(word)
    }

}