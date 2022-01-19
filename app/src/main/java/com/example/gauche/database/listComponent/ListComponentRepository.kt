package com.example.gauche.database.listComponent

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import android.os.AsyncTask


class ListComponentRepository(private val wordDao: ListComponentDao) {

    val size: LiveData<Int> = wordDao.size
    val allWords: LiveData<List<ListComponent>> = wordDao.alphabetizedWords
    val allWordsContent: LiveData<List<String>> = wordDao.alphabetizedWordsContent
    @WorkerThread
    fun insert(word: ListComponent) {
        wordDao.insert(word)
    }


    /* --------------- BORRAR TODOS LOS DATOS -------------- */

    fun deleteAll() {
        deleteAllWordsAsyncTask(wordDao).execute()
    }

    private class deleteAllWordsAsyncTask(private val mAsyncTaskDao: ListComponentDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    /* ---------------- BORRAR UN SOLO DATO ---------------- */

    fun deleteListComponent(listComponent: ListComponent) {
        deleteWordAsyncTask(wordDao).execute(listComponent)
    }

    private class deleteWordAsyncTask(private val mAsyncTaskDao: ListComponentDao) :
        AsyncTask<ListComponent, Void, Void>() {

        override fun doInBackground(vararg params: ListComponent): Void? {
            mAsyncTaskDao.deleteWord(params[0])
            return null
        }
    }

}