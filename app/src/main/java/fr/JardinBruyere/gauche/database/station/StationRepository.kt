package fr.JardinBruyere.gauche.database.station

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import android.os.AsyncTask
import android.util.Log


class StationRepository(private val wordDao: StationDao) {

    val allWords: LiveData<List<Station>> = wordDao.alphabetizedWords

    @WorkerThread
    fun insert(word: Station) {
        wordDao.insert(word)
        Log.e("INSERT STATION","insert of ${word.Name}")
    }


    /* --------------- BORRAR TODOS LOS DATOS -------------- */

    fun deleteAll() {
        deleteAllWordsAsyncTask(wordDao).execute()
    }

    private class deleteAllWordsAsyncTask(private val mAsyncTaskDao: StationDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    /* ---------------- BORRAR UN SOLO DATO ---------------- */

    fun deleteListComponent(listComponent: Station) {
        deleteWordAsyncTask(wordDao).execute(listComponent)
    }

    private class deleteWordAsyncTask(private val mAsyncTaskDao: StationDao) :
        AsyncTask<Station, Void, Void>() {

        override fun doInBackground(vararg params: Station): Void? {
            mAsyncTaskDao.deleteWord(params[0])
            return null
        }
    }

}