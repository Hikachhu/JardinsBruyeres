package com.example.gauche.database

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import com.example.gauche.database.component.ComponentViewModel
import com.example.gauche.database.listComponent.ListComponentViewModel
import com.example.gauche.database.relevesCapteurs.RelevesCapteurs
import com.example.gauche.database.relevesCapteurs.RelevesCapteursViewModel

class GenerateData(application: Application) {

    private val mRelevesCapteursViewModel = RelevesCapteursViewModel(application)
    private val mComponentViewModel = ComponentViewModel(application)

    fun GenerateRelevesCapteurs(lifecycleOwner: LifecycleOwner, nombreData: Int) {
        var a=0
        mComponentViewModel.allWords.observe(lifecycleOwner, { words ->
            for (j in 1..words.size) {
                for (i in 1..nombreData) {
                    mRelevesCapteursViewModel.insert(
                        RelevesCapteurs(
                            a,
                            j,
                            System.currentTimeMillis(),
                            3 + i
                        )
                    )
                    a++
                }
            }

        })
    }
}