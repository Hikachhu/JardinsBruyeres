package fr.JardinBruyere.gauche.database.sensorReading

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import android.os.AsyncTask


class SensorReadingRepository(private val wordDao: SensorReadingDao) {

    val allWords: LiveData<List<SensorReading>> = wordDao.alphabetizedWords


    fun getCapteur(sensor:Int): List<SensorReading> {
        return wordDao.allWordsOfCapteur(sensor)
    }

    @WorkerThread
    fun insert(word: SensorReading) {
        wordDao.insert(word)
    }

    /* --------------- BORRAR TODOS LOS DATOS -------------- */

    fun deleteAll() {
        deleteAllWordsAsyncTask(wordDao).execute()
    }

    private class deleteAllWordsAsyncTask(private val mAsyncTaskDao: SensorReadingDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    /* ---------------- BORRAR UN SOLO DATO ---------------- */

    fun deleteListComponent(listComponent: SensorReading) {
        deleteWordAsyncTask(wordDao).execute(listComponent)
    }

    private class deleteWordAsyncTask(private val mAsyncTaskDao: SensorReadingDao) :
        AsyncTask<SensorReading, Void, Void>() {

        override fun doInBackground(vararg params: SensorReading): Void? {
            mAsyncTaskDao.deleteWord(params[0])
            return null
        }
    }

}