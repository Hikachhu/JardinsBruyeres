package com.example.gauche.database.component

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.gauche.database.ComponentRepository
import com.example.gauche.database.RoomDatabaseCustom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class ComponentViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: ComponentRepository
    val allWords: LiveData<List<Component>>

    init {
        val wordsDao = RoomDatabaseCustom.getDatabase(application, scope).ComponentDao()
        repository = ComponentRepository(wordsDao)
        allWords = repository.allWords
    }

    fun insert(word: Component) = scope.launch(Dispatchers.IO) {
        repository.insert(word)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun deleteAll() {
        repository.deleteAll()
    }

    fun deleteWord(word: Component) {
        repository.deleteListComponent(word)
    }

}