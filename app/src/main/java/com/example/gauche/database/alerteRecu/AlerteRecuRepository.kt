package com.example.gauche.database.component

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import android.os.AsyncTask


class AlerteRecuRepository(private val wordDao: AlerteRecuDao) {

    val allWords: LiveData<List<AlerteRecu>> = wordDao.alphabetizedWords

    @WorkerThread
    fun insert(word: AlerteRecu) {
        wordDao.insert(word)
    }


    /* --------------- BORRAR TODOS LOS DATOS -------------- */

    fun deleteAll() {
        deleteAllWordsAsyncTask(wordDao).execute()
    }

    private class deleteAllWordsAsyncTask(private val mAsyncTaskDao: AlerteRecuDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    /* ---------------- BORRAR UN SOLO DATO ---------------- */

    fun deleteListComponent(listComponent: AlerteRecu) {
        deleteWordAsyncTask(wordDao).execute(listComponent)
    }

    private class deleteWordAsyncTask(private val mAsyncTaskDao: AlerteRecuDao) :
        AsyncTask<AlerteRecu, Void, Void>() {

        override fun doInBackground(vararg params: AlerteRecu): Void? {
            mAsyncTaskDao.deleteWord(params[0])
            return null
        }
    }

}