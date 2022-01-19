package com.example.gauche.database.component

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import android.os.AsyncTask


class AlerteRepository(private val wordDao: AlerteDao) {

    val allWords: LiveData<List<Alerte>> = wordDao.alphabetizedWords

    @WorkerThread
    fun insert(word: Alerte) {
        wordDao.insert(word)
    }


    /* --------------- BORRAR TODOS LOS DATOS -------------- */

    fun deleteAll() {
        deleteAllWordsAsyncTask(wordDao).execute()
    }

    private class deleteAllWordsAsyncTask(private val mAsyncTaskDao: AlerteDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    /* ---------------- BORRAR UN SOLO DATO ---------------- */

    fun deleteListComponent(listComponent: Alerte) {
        deleteWordAsyncTask(wordDao).execute(listComponent)
    }

    private class deleteWordAsyncTask(private val mAsyncTaskDao: AlerteDao) :
        AsyncTask<Alerte, Void, Void>() {

        override fun doInBackground(vararg params: Alerte): Void? {
            mAsyncTaskDao.deleteWord(params[0])
            return null
        }
    }

}