package com.example.gauche.database.relevesCapteurs

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import android.os.AsyncTask


class RelevesCapteursRepository(private val wordDao: RelevesCapteursDao) {

    val allWords: LiveData<List<RelevesCapteurs>> = wordDao.alphabetizedWords


    @WorkerThread
    fun insert(word: RelevesCapteurs) {
        wordDao.insert(word)
    }

    /* --------------- BORRAR TODOS LOS DATOS -------------- */

    fun deleteAll() {
        deleteAllWordsAsyncTask(wordDao).execute()
    }

    private class deleteAllWordsAsyncTask(private val mAsyncTaskDao: RelevesCapteursDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    /* ---------------- BORRAR UN SOLO DATO ---------------- */

    fun deleteListComponent(listComponent: RelevesCapteurs) {
        deleteWordAsyncTask(wordDao).execute(listComponent)
    }

    private class deleteWordAsyncTask(private val mAsyncTaskDao: RelevesCapteursDao) :
        AsyncTask<RelevesCapteurs, Void, Void>() {

        override fun doInBackground(vararg params: RelevesCapteurs): Void? {
            mAsyncTaskDao.deleteWord(params[0])
            return null
        }
    }

}