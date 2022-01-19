package com.example.gauche.database.component

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import android.os.AsyncTask


class ListeTypeAlerteRepository(private val wordDao: ListeTypeAlerteDao) {

    val allWords: LiveData<List<ListeTypeAlerte>> = wordDao.alphabetizedWords

    @WorkerThread
    fun insert(word: ListeTypeAlerte) {
        wordDao.insert(word)
    }


    /* --------------- BORRAR TODOS LOS DATOS -------------- */

    fun deleteAll() {
        deleteAllWordsAsyncTask(wordDao).execute()
    }

    private class deleteAllWordsAsyncTask(private val mAsyncTaskDao: ListeTypeAlerteDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    /* ---------------- BORRAR UN SOLO DATO ---------------- */

    fun deleteListComponent(listComponent: ListeTypeAlerte) {
        deleteWordAsyncTask(wordDao).execute(listComponent)
    }

    private class deleteWordAsyncTask(private val mAsyncTaskDao: ListeTypeAlerteDao) :
        AsyncTask<ListeTypeAlerte, Void, Void>() {

        override fun doInBackground(vararg params: ListeTypeAlerte): Void? {
            mAsyncTaskDao.deleteWord(params[0])
            return null
        }
    }

}