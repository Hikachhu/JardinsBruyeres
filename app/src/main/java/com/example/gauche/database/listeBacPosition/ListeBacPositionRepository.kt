package com.example.gauche.database.component

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import android.os.AsyncTask


class ListeBacPositionRepository(private val wordDao: ListeBacPositionDao) {

    val allWords: LiveData<List<ListeBacPosition>> = wordDao.alphabetizedWords

    @WorkerThread
    fun insert(word: ListeBacPosition) {
        wordDao.insert(word)
    }


    /* --------------- BORRAR TODOS LOS DATOS -------------- */

    fun deleteAll() {
        deleteAllWordsAsyncTask(wordDao).execute()
    }

    private class deleteAllWordsAsyncTask(private val mAsyncTaskDao: ListeBacPositionDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    /* ---------------- BORRAR UN SOLO DATO ---------------- */

    fun deleteListComponent(listComponent: ListeBacPosition) {
        deleteWordAsyncTask(wordDao).execute(listComponent)
    }

    private class deleteWordAsyncTask(private val mAsyncTaskDao: ListeBacPositionDao) :
        AsyncTask<ListeBacPosition, Void, Void>() {

        override fun doInBackground(vararg params: ListeBacPosition): Void? {
            mAsyncTaskDao.deleteWord(params[0])
            return null
        }
    }

}