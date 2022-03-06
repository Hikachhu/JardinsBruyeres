package fr.JardinBruyere.gauche.database

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import fr.JardinBruyere.gauche.database.sensor.SensorViewModel
import fr.JardinBruyere.gauche.database.sensorReading.SensorReading
import fr.JardinBruyere.gauche.database.sensorReading.SensorReadingViewModel

class GenerateData(application: Application) {

    private val mRelevesCapteursViewModel = SensorReadingViewModel(application)
    private val mComponentViewModel = SensorViewModel(application)

    fun GenerateRelevesCapteurs(lifecycleOwner: LifecycleOwner, nombreData: Int) {
        var a=0
        mComponentViewModel.allWords.observe(lifecycleOwner, { words ->
            for (j in 1..words.size) {
                for (i in 1..nombreData) {
                    mRelevesCapteursViewModel.insert(
                        SensorReading(
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