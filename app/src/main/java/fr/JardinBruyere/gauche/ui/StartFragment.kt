package fr.JardinBruyere.gauche.ui

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import fr.JardinBruyere.gauche.R
import fr.JardinBruyere.gauche.databinding.FragmentStartBinding
import fr.JardinBruyere.gauche.database.sensor.Sensor
import fr.JardinBruyere.gauche.database.sensor.SensorViewModel
import fr.JardinBruyere.gauche.database.sensorReading.SensorReading
import fr.JardinBruyere.gauche.database.sensorReading.SensorReadingViewModel
import fr.JardinBruyere.gauche.database.sensorTypes.SensorTypes
import fr.JardinBruyere.gauche.database.sensorTypes.SensorTypesViewModel
import fr.JardinBruyere.gauche.database.station.Station
import fr.JardinBruyere.gauche.database.station.StationViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import fr.JardinBruyere.gauche.ApiHelper
import retrofit2.Call
import java.lang.reflect.Type
import java.text.SimpleDateFormat

class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!


    private var mSensorViewModel: SensorViewModel? = null
    private var mStationViewModel: StationViewModel? = null
    private var mSensorTypesViewModel: SensorTypesViewModel? = null
    private var mSensorReadingViewModel: SensorReadingViewModel? = null

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        val root: View = binding.root


        mSensorTypesViewModel =                ViewModelProvider(this)[SensorTypesViewModel::class.java]
        mStationViewModel =                 ViewModelProvider(this)[StationViewModel::class.java]
        mSensorViewModel =           ViewModelProvider(this)[SensorViewModel::class.java]
        mSensorReadingViewModel =              ViewModelProvider(this)[SensorReadingViewModel::class.java]


        val element = root.findViewById(R.id.changeIP) as EditText
        val button = root.findViewById(R.id.buttonIP) as Button
        val refreshReading = root.findViewById(R.id.refreshReading) as Button
        val refreshTable = root.findViewById(R.id.refreshTable) as Button
        val tableText=root.findViewById(R.id.textView4) as TextView
        val relevesText=root.findViewById(R.id.textView5) as TextView

        val pref = context?.getSharedPreferences("MyPref", 0)
        val editor: SharedPreferences.Editor? = pref?.edit()
        editor?.commit()
        var ip = pref?.getString("ipServeur", null)
        if (ip == null) {
            editor?.putString("ipServeur", "ns328061.ip-37-187-112.eu");
            editor?.commit()
            ip="ns328061.ip-37-187-112.eu"
            Log.e("IP=",ip)
        }

        mSensorViewModel!!.deleteAll()
        mSensorTypesViewModel!!.deleteAll()
        mStationViewModel!!.deleteAll()
        val service = ApiHelper.create(ip)

        button.setOnClickListener {
            var newip = element.text
            editor?.putString("ipServeur", newip.toString());
            editor?.apply()
            view?.let { it1 ->
                Snackbar.make(it1, "L'ip à correctement été changée en \"$newip\"", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }
        element.setText(ip)
        refreshTable.setOnClickListener {

            mSensorViewModel!!.deleteAll()
            mSensorTypesViewModel!!.deleteAll()
            mStationViewModel!!.deleteAll()
            val pref = context?.getSharedPreferences("MyPref", 0)
            var ip = pref?.getString("ipServeur", null).toString()
            val service = ApiHelper.create(ip)
            service.getSensor().enqueue(object : retrofit2.Callback<Any> {
                override fun onFailure(call: Call<Any>?, t: Throwable?) {
                    if (t != null) {
                        Log.e("retrofit ko", "call failed "+t.localizedMessage)
                        Toast.makeText(context,"fail "+t.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
                @SuppressLint("SimpleDateFormat")
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call<Any>?, response: retrofit2.Response<Any>?) {
                    if (response != null) {
                        val value= response.body()
                        var test = listOf(value)[0].toString()

                        val regex = Regex("([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})", RegexOption.MULTILINE)
                        val results = regex.findAll(test).map{ result -> result.value }.toList()
                        for(z in results.indices) {
                            test = test.replace(results[z], results[z].replace(":","_"))
                        }

                        val regex1 = Regex("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}", RegexOption.MULTILINE)
                        val results1 = regex1.findAll(test).map{ result -> result.value }.toList()
                        for(z in results1.indices) {
                            test = test.replace(results1[z], convertDateToLong(results1[z]).toString())
                        }

                        val gson = GsonBuilder().setLenient().create()
                        val type: Type = object : TypeToken<Map<Any?, Any?>?>() {}.type
                        val myMap: Map<Any, Any> =gson.fromJson(test, type)
                        val lComposant =(gson.fromJson((myMap["data"] as List<*>)[0].toString(), object : TypeToken<Map<Any?, Any?>?>() {}.type)as Map<Any, Any>)["Sensor"] as List<*>

                        for(i in lComposant.indices){
                            val myMap3: Map<Any, Any> =gson.fromJson(lComposant[i].toString(),  object : TypeToken<Map<Any?, Any?>?>() {}.type)
                            val current= Sensor((myMap3["Id"] as Double).toInt(),(myMap3["Type"] as Double).toInt(),(myMap3["DateAdded"] as Double).toLong(),(myMap3["Station"] as Double).toInt(),(myMap3["Name"] as String),myMap3["MacAdress"] as String)
                            Log.e("ELEM OF lComposant $i","value ${current.Id} ${current.DateAdded} ${current.MacAdress} ${current.Name} ${current.Station} ${current.Type}")
                            mSensorViewModel!!.insert(current)
                        }

                    }
                }
            })
            //____________________________________________________________________________________________________________________________
            service.getSensorTypes().enqueue(object : retrofit2.Callback<Any> {
                override fun onFailure(call: Call<Any>?, t: Throwable?) {
                    if (t != null) {
                        Log.e("retrofit ko", "call failed "+t.localizedMessage)
                        Toast.makeText(context,"fail "+t.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
                @SuppressLint("SimpleDateFormat")
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call<Any>?, response: retrofit2.Response<Any>?) {
                    if (response != null) {
                        val value= response.body()
                        var test = listOf(value)[0].toString()

                        val regex = Regex("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}", RegexOption.MULTILINE)
                        val results = regex.findAll(test).map{ result -> result.value }.toList()
                        for(z in results.indices) {
                            test = test.replace(results[z], convertDateToLong(results[z]).toString())
                        }

                        val gson = GsonBuilder().setLenient().create()
                        val type: Type = object : TypeToken<Map<Any?, Any?>?>() {}.type
                        val myMap: Map<Any, Any> =gson.fromJson(test, type)
                        val lRelevesCapteurs =(gson.fromJson((myMap["data"] as List<*>)[0].toString(), object : TypeToken<Map<Any?, Any?>?>() {}.type)as Map<Any, Any>)["SensorTypes"] as List<*>
                        for(i in lRelevesCapteurs.indices){
                            val myMap3: Map<Any, Any> =gson.fromJson(lRelevesCapteurs[i].toString(),  object : TypeToken<Map<Any?, Any?>?>() {}.type)
                            val current= SensorTypes((myMap3["Id"] as Double).toInt(),myMap3["Unit"] as String)
                            Log.e("ELEM OF lComposant $i","value ${current.Id} ${current.Unit}")
                            mSensorTypesViewModel!!.insert(current)
                        }
                        Log.e("getSensorTypes", lRelevesCapteurs.toString())

                    }
                }
            })
            //____________________________________________________________________________________________________________________________
            service.getStation().enqueue(object : retrofit2.Callback<Any> {
                override fun onFailure(call: Call<Any>?, t: Throwable?) {
                    if (t != null) {
                        Log.e("retrofit ko", "call failed "+t.localizedMessage)
                        Toast.makeText(context,"fail "+t.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
                @SuppressLint("SimpleDateFormat")
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call<Any>?, response: retrofit2.Response<Any>?) {
                    if (response != null) {
                        val value= response.body()
                        var test = listOf(value)[0].toString()

                        val regex = Regex("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}", RegexOption.MULTILINE)
                        val results = regex.findAll(test).map{ result -> result.value }.toList()
                        for(z in results.indices) {
                            test = test.replace(results[z], convertDateToLong(results[z]).toString())
                        }
                        test=test.replace("Name=","Name=\"").replace("},","\"},").replace("}]}],","\"}]}],")


                        val regex1 = Regex("Name=\"(\\w| )*\"", RegexOption.MULTILINE)
                        val results1 = regex1.findAll(test).map{ result1 -> result1.value }.toList()
                        for(z in results1.indices) {
                            test = test.replace(results1[z],results1[z].replace(" ","_"))
                        }
                        val gson = GsonBuilder().setLenient().create()
                        val type: Type = object : TypeToken<Map<String?, Any?>?>() {}.type
                        val myMap: Map<String, Any> =gson.fromJson(test, type)
                        val lRelevesCapteurs =(gson.fromJson((myMap["data"] as List<*>)[0].toString(), object : TypeToken<Map<Any?, Any?>?>() {}.type)as Map<Any, Any>)["Station"] as List<*>

                        for(i in lRelevesCapteurs.indices){
                            val myMap3: Map<Any, Any> =gson.fromJson(lRelevesCapteurs[i].toString(),  object : TypeToken<Map<Any?, Any?>?>() {}.type)
                            val current= Station((myMap3["Id"] as Double).toInt(),(myMap3["Name"] as String))
                            Log.e("debug$i","value ${current.Id} ${current.Name}")
                            mStationViewModel!!.insert(current)
                        }

                        Log.e("getStation", lRelevesCapteurs.toString())

                    }


                    view?.let { it1 ->
                        Snackbar.make(it1, "Les données des capteurs, stations et types ont correctment été chargées", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                    }
                    tableText.text="Telechargé"
                }
            })
        }

        refreshReading.setOnClickListener{

            mSensorReadingViewModel!!.deleteAll()
            val pref = context?.getSharedPreferences("MyPref", 0)
            var ip = pref?.getString("ipServeur", null).toString()
            val service = ApiHelper.create(ip)

            val listing2: LiveData<List<Sensor>> = mSensorViewModel!!.allWords
            listing2.observe(viewLifecycleOwner,
                { list: List<Sensor> ->
                    for (now in list) {

                Log.e("Call for", now.toString())
                        now.Id?.let { it1 ->
                            service.search(it1).enqueue(object : retrofit2.Callback<Any> {
                                override fun onFailure(call: Call<Any>?, t: Throwable?) {
                                    if (t != null) {
                                        Log.e("retrofit ko", "call failed "+t.localizedMessage)
                                        Toast.makeText(context,"fail "+t.localizedMessage, Toast.LENGTH_SHORT).show()
                                    }
                                }

                                @SuppressLint("SimpleDateFormat")
                                @RequiresApi(Build.VERSION_CODES.O)
                                override fun onResponse(call: Call<Any>?, response: retrofit2.Response<Any>?) {
                                    if (response != null) {
                                        val value= response.body()
                                        var test = listOf(value)[0].toString()

                                        val regex = Regex("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}", RegexOption.MULTILINE)
                                        val results = regex.findAll(test).map{ result -> result.value }.toList()
                                        for(z in results.indices) {
                                            test = test.replace(results[z], convertDateToLong(results[z]).toString())
                                        }

                                        val gson = GsonBuilder()
                                            .setLenient()
                                            .create()
                                        val type: Type = object : TypeToken<Map<Any?, Any?>?>() {}.type
                                        val myMap: Map<Any, Any> =gson.fromJson(test, type)
                                        val lRelevesCapteurs =(gson.fromJson((myMap["data"] as List<*>)[0].toString(), object : TypeToken<Map<Any?, Any?>?>() {}.type)as Map<Any, Any>)["SensorReading"] as List<*>

                                        for(i in lRelevesCapteurs){
                                            val myMap3: Map<Any, Any> =gson.fromJson(i.toString(),  object : TypeToken<Map<Any?, Any?>?>() {}.type)
                                            val current= SensorReading((myMap3["Id"] as Double).toInt(),(myMap3["SensorId"] as Double).toInt(),(myMap3["DateAdded"] as Double).toLong(),(myMap3["Value"] as Double).toInt())

                                            mSensorReadingViewModel!!.insert(current)
                                        }

                                        Log.e("search", lRelevesCapteurs.toString())

                                    }

                                    view?.let { it1 ->
                                        Snackbar.make(it1, "Les relevés des capteurs disponibles ont correctement été chargés", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show()
                                    }
                                    relevesText.text="Telechargé"
                                }
                            })
                        }
                    }
                })

        }
        return root
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateToLong(date: String): Any {
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return df.parse(date).time
    }
}