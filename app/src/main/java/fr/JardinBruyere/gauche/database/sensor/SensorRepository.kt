package fr.JardinBruyere.gauche.database.sensor

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import android.os.AsyncTask
import android.util.Log


class SensorRepository(private val wordDao: SensorDao) {

    val allWords: LiveData<List<Sensor>> = wordDao.alphabetizedWords

    @WorkerThread
    fun insert(word: Sensor) {
        wordDao.insert(word)
        Log.e("Debug ajout","ajout de  ${word.Name}")
    }


    /* --------------- BORRAR TODOS LOS DATOS -------------- */

    fun deleteAll() {
        deleteAllWordsAsyncTask(wordDao).execute()
    }

    private class deleteAllWordsAsyncTask(private val mAsyncTaskDao: SensorDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    /* ---------------- BORRAR UN SOLO DATO ---------------- */

    fun deleteListComponent(listComponent: Sensor) {
        deleteWordAsyncTask(wordDao).execute(listComponent)
    }

    private class deleteWordAsyncTask(private val mAsyncTaskDao: SensorDao) :
        AsyncTask<Sensor, Void, Void>() {

        override fun doInBackground(vararg params: Sensor): Void? {
            mAsyncTaskDao.deleteWord(params[0])
            return null
        }
    }

}