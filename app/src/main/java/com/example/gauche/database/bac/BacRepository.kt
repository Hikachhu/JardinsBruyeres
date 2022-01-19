package com.example.gauche.database.component

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import android.os.AsyncTask


class BacRepository(private val wordDao: BacDao) {

    val allWords: LiveData<List<Bac>> = wordDao.alphabetizedWords

    @WorkerThread
    fun insert(word: Bac) {
        wordDao.insert(word)
    }


    /* --------------- BORRAR TODOS LOS DATOS -------------- */

    fun deleteAll() {
        deleteAllWordsAsyncTask(wordDao).execute()
    }

    private class deleteAllWordsAsyncTask(private val mAsyncTaskDao: BacDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    /* ---------------- BORRAR UN SOLO DATO ---------------- */

    fun deleteListComponent(listComponent: Bac) {
        deleteWordAsyncTask(wordDao).execute(listComponent)
    }

    private class deleteWordAsyncTask(private val mAsyncTaskDao: BacDao) :
        AsyncTask<Bac, Void, Void>() {

        override fun doInBackground(vararg params: Bac): Void? {
            mAsyncTaskDao.deleteWord(params[0])
            return null
        }
    }

}