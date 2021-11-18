package com.example.gauche.database

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import android.os.AsyncTask
import com.example.gauche.database.component.Component
import com.example.gauche.database.component.ComponentDao


class ComponentRepository(private val wordDao: ComponentDao) {

    val allWords: LiveData<List<Component>> = wordDao.alphabetizedWords

    @WorkerThread
    fun insert(word: Component) {
        wordDao.insert(word)
    }


    /* --------------- BORRAR TODOS LOS DATOS -------------- */

    fun deleteAll() {
        deleteAllWordsAsyncTask(wordDao).execute()
    }

    private class deleteAllWordsAsyncTask(private val mAsyncTaskDao: ComponentDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    /* ---------------- BORRAR UN SOLO DATO ---------------- */

    fun deleteListComponent(listComponent: Component) {
        deleteWordAsyncTask(wordDao).execute(listComponent)
    }

    private class deleteWordAsyncTask(private val mAsyncTaskDao: ComponentDao) :
        AsyncTask<Component, Void, Void>() {

        override fun doInBackground(vararg params: Component): Void? {
            mAsyncTaskDao.deleteWord(params[0])
            return null
        }
    }

}