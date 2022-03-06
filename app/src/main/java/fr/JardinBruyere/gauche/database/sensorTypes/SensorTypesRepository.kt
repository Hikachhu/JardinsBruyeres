package fr.JardinBruyere.gauche.database.sensorTypes

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import android.os.AsyncTask


class SensorTypesRepository(private val wordDao: SensorTypesDao) {

    val size: LiveData<Int> = wordDao.size
    val allWords: LiveData<List<SensorTypes>> = wordDao.alphabetizedWords
    val allWordsContent: LiveData<List<String>> = wordDao.alphabetizedWordsContent
    @WorkerThread
    fun insert(word: SensorTypes) {
        wordDao.insert(word)
    }


    /* --------------- BORRAR TODOS LOS DATOS -------------- */

    fun deleteAll() {
        deleteAllWordsAsyncTask(wordDao).execute()
    }

    private class deleteAllWordsAsyncTask(private val mAsyncTaskDao: SensorTypesDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    /* ---------------- BORRAR UN SOLO DATO ---------------- */

    fun deleteListComponent(sensorTypes: SensorTypes) {
        deleteWordAsyncTask(wordDao).execute(sensorTypes)
    }

    private class deleteWordAsyncTask(private val mAsyncTaskDao: SensorTypesDao) :
        AsyncTask<SensorTypes, Void, Void>() {

        override fun doInBackground(vararg params: SensorTypes): Void? {
            mAsyncTaskDao.deleteWord(params[0])
            return null
        }
    }

}