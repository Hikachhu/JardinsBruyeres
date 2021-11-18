package com.example.gauche.database

import android.app.Application
import com.example.gauche.database.relevesCapteurs.RelevesCapteurs
import com.example.gauche.database.relevesCapteurs.RelevesCapteursViewModel

class GenerateData(application: Application) {

    val mRelevesCapteursViewModel = RelevesCapteursViewModel(application)

    fun GenerateRelevesCapteurs(nombreData:Int) {
        for (i in 1..nombreData) {
            mRelevesCapteursViewModel.insert(RelevesCapteurs(i,1, System.currentTimeMillis(),3+i))
        }
    }

}