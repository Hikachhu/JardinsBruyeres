package com.example.gauche.ui.typecomposant

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gauche.ApiHelper
import com.example.gauche.R
import com.example.gauche.database.component.*
import com.example.gauche.database.listComponent.ListComponent
import com.example.gauche.database.listComponent.ListComponentAdapter
import com.example.gauche.database.listComponent.ListComponentViewModel
import com.example.gauche.database.relevesCapteurs.RelevesCapteurs
import com.example.gauche.database.relevesCapteurs.RelevesCapteursViewModel
import com.example.gauche.databinding.FragmentHomeBinding
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class TypeComposantsFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var mComponentViewModel: ComponentViewModel? = null
    private var mListeTypeAlerteViewModel: ListeTypeAlerteViewModel? = null
    private var mAlerteViewModel: AlerteViewModel? = null
    private var mBacViewModel: BacViewModel? = null
    private var mListeBacPositionViewModel: ListeBacPositionViewModel? = null
    private var mAlerteRecuViewModel: AlerteRecuViewModel? = null
    private var mListComponentViewModel: ListComponentViewModel? = null
    private var mRelevesCapteursViewModel: RelevesCapteursViewModel? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView: RecyclerView = root.findViewById(R.id.recycleur_view_ListCompenent)
        mListComponentViewModel =                ViewModelProvider(this)[ListComponentViewModel::class.java]
        mListeTypeAlerteViewModel =     ViewModelProvider(this)[ListeTypeAlerteViewModel::class.java]
        mAlerteViewModel =              ViewModelProvider(this)[AlerteViewModel::class.java]
        mBacViewModel =                 ViewModelProvider(this)[BacViewModel::class.java]
        mListeBacPositionViewModel =    ViewModelProvider(this)[ListeBacPositionViewModel::class.java]
        mComponentViewModel =           ViewModelProvider(this)[ComponentViewModel::class.java]
        mAlerteRecuViewModel =          ViewModelProvider(this)[AlerteRecuViewModel::class.java]
        mRelevesCapteursViewModel =              ViewModelProvider(this)[RelevesCapteursViewModel::class.java]

        val service = ApiHelper.create()

        service.search().enqueue(object : retrofit2.Callback<Any> {
            override fun onFailure(call: Call<Any>?, t: Throwable?) {
                if (t != null) {
                    Log.e("retrofit ko", "call failed "+t.localizedMessage)
                }
                if (t != null) {
                    Toast.makeText(context,"fail "+t.localizedMessage, Toast.LENGTH_SHORT).show()
                };
            }
            @SuppressLint("SimpleDateFormat")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<Any>?, response: retrofit2.Response<Any>?) {
                if (response != null) {
                    mListComponentViewModel!!.deleteAll()
                    mListeTypeAlerteViewModel!!.deleteAll()
                    mAlerteViewModel!!.deleteAll()
                    mBacViewModel!!.deleteAll()
                    mListeBacPositionViewModel!!.deleteAll()
                    mComponentViewModel!!.deleteAll()
                    mAlerteRecuViewModel!!.deleteAll()
                    mRelevesCapteursViewModel!!.deleteAll()
                    val value= response.body()
                    var test = listOf(value)[0].toString()

                    val regex = Regex("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}", RegexOption.MULTILINE)
                    val results = regex.findAll(test).map{ result -> result.value }.toList()
                    for(z in results.indices) {
                        test = test.replace(results[z], convertDateToLong(results[z]).toString())
                    }

                    Log.e("Replace try","from $test")
                    val gson = GsonBuilder()
                        .setLenient()
                        .create()
                    val type: Type = object : TypeToken<Map<Any?, Any?>?>() {}.type
                    val myMap: Map<Any, Any> =gson.fromJson(test, type)
                    val lListeTypeComposants =    (gson.fromJson((myMap["data"] as List<*>)[0].toString(), object : TypeToken<Map<Any?, Any?>?>() {}.type)as Map<Any, Any>)["ListeTypeComposants"] as List<*>
                    val lListeTypeAlerte =        (gson.fromJson((myMap["data"] as List<*>)[1].toString(), object : TypeToken<Map<Any?, Any?>?>() {}.type)as Map<Any, Any>)["ListeTypeAlerte"] as List<*>
                    val lAlerte =                 (gson.fromJson((myMap["data"] as List<*>)[2].toString(), object : TypeToken<Map<Any?, Any?>?>() {}.type)as Map<Any, Any>)["Alerte"] as List<*>
                    val lBac =                    (gson.fromJson((myMap["data"] as List<*>)[3].toString(), object : TypeToken<Map<Any?, Any?>?>() {}.type)as Map<Any, Any>)["Bac"] as List<*>
                    val lListeBacPosition =       (gson.fromJson((myMap["data"] as List<*>)[4].toString(), object : TypeToken<Map<Any?, Any?>?>() {}.type)as Map<Any, Any>)["ListeBacPosition"] as List<*>
                    val lComposant =              (gson.fromJson((myMap["data"] as List<*>)[5].toString(), object : TypeToken<Map<Any?, Any?>?>() {}.type)as Map<Any, Any>)["Composant"] as List<*>
                    val lAlerteRecu =             (gson.fromJson((myMap["data"] as List<*>)[6].toString(), object : TypeToken<Map<Any?, Any?>?>() {}.type)as Map<Any, Any>)["AlerteRecu"] as List<*>
                    val lRelevesCapteurs =        (gson.fromJson((myMap["data"] as List<*>)[7].toString(), object : TypeToken<Map<Any?, Any?>?>() {}.type)as Map<Any, Any>)["RelevesCapteurs"] as List<*>
                    for(i in lListeTypeComposants.indices){
                        val myMap3: Map<Any, Any> =gson.fromJson(lListeTypeComposants[i].toString(),  object : TypeToken<Map<Any?, Any?>?>() {}.type)
                        val current= ListComponent(i,myMap3["NomComposant"] as String,myMap3["typeComposant"] as Double)
                        Log.e("debug $i","value ${current.ID} ${current.nameComponent} ${current.typeComposant}")
                        mListComponentViewModel!!.insert(current)
                    }
                    var current= ListComponent(2,"Luminosité",3.0)
                    mListComponentViewModel!!.insert(current)
                    current= ListComponent(3,"Luminosité",4.0)
                    mListComponentViewModel!!.insert(current)
                    current= ListComponent(4,"Luminosité",5.0)
                    mListComponentViewModel!!.insert(current)
                    for(i in lListeTypeAlerte.indices){
                        val myMap3: Map<Any, Any> =gson.fromJson(lListeTypeAlerte[i].toString(),  object : TypeToken<Map<Any?, Any?>?>() {}.type)
                        val current= ListeTypeAlerte((myMap3["TypeAlerte"] as Double).toInt(),(myMap3["Criticite"] as Double).toInt(),(myMap3["MethodeNotification"] as Double).toInt())
                        Log.e("debug$i","value ${current.TypeAlerte} ${current.Criticite} ${current.MethodeNotification}")
                        mListeTypeAlerteViewModel!!.insert(current)
                    }
                    for(i in lAlerte.indices){
                        val myMap3: Map<Any, Any> =gson.fromJson(lAlerte[i].toString(),  object : TypeToken<Map<Any?, Any?>?>() {}.type)
                        val current= Alerte((myMap3["ComposantCible"] as Double).toInt(),(myMap3["TypeAlerte"] as Double).toInt(),(myMap3["id"] as Double).toInt(),(myMap3["seuil"] as Double).toInt())
                        Log.e("debug$i","value ${current.ID} ${current.ComposantCible} ${current.Seuil} ${current.TyoeAlerte}")
                        mAlerteViewModel!!.insert(current)
                    }
                    for(i in lBac.indices){
                        val myMap3: Map<Any, Any> =gson.fromJson(lBac[i].toString(),  object : TypeToken<Map<Any?, Any?>?>() {}.type)
                        val current= Bac((myMap3["id"] as Double).toInt(),(myMap3["x"] as Double).toInt(),(myMap3["y"] as Double).toInt(),(myMap3["etage"] as Double).toInt(),(myMap3["NomBac"] as String))
                        Log.e("debug$i","value ${current.ID} ${current.X} ${current.Y} ${current.NomBac} ${current.Etage}")
                        mBacViewModel!!.insert(current)
                    }
                    for(i in lListeBacPosition.indices){
                        val myMap3: Map<Any, Any> =gson.fromJson(lListeBacPosition[i].toString(),  object : TypeToken<Map<Any?, Any?>?>() {}.type)
                        val current= ListeBacPosition((myMap3["id"] as Double).toInt(),(myMap3["Bac"] as Double).toInt(),(myMap3["Capteur"] as Double).toInt())
                        Log.e("debug$i","value ${current.ID} ${current.Bac} ${current.Capteur}")
                        mListeBacPositionViewModel!!.insert(current)
                    }
                    for(i in lComposant.indices){
                        val myMap3: Map<Any, Any> =gson.fromJson(lComposant[i].toString(),  object : TypeToken<Map<Any?, Any?>?>() {}.type)
                        val current= Component((myMap3["id"] as Double).toInt(),(myMap3["type"] as Double).toInt(),(myMap3["DateAjout"] as Double).toLong(),(myMap3["Position"] as Double).toInt())
                        Log.e("debug$i","value ${current.ID} ${current.position} ${current.dateAjout} ${current.type}")
                        mComponentViewModel!!.insert(current)
                    }
                    var forMoment =Component(0,1,1642693188,1)
                    mComponentViewModel!!.insert(forMoment)
                    forMoment =Component(1,1,1642693188,1)
                    mComponentViewModel!!.insert(forMoment)
                    for(i in lAlerteRecu.indices){
                        val myMap3: Map<Any, Any> =gson.fromJson(lAlerteRecu[i].toString(),  object : TypeToken<Map<Any?, Any?>?>() {}.type)
                        val current= AlerteRecu((myMap3["id"] as Double).toInt(),(myMap3["DateAjout"] as Double).toLong(),(myMap3["NumeroDalerte"] as Double).toInt())
                        Log.e("debug$i","value ${current.ID} ${current.DateAjout} ${current.NumeroAlerte}")
                        mAlerteRecuViewModel!!.insert(current)
                    }
                    for(i in lRelevesCapteurs.indices){
                        val myMap3: Map<Any, Any> =gson.fromJson(lRelevesCapteurs[i].toString(),  object : TypeToken<Map<Any?, Any?>?>() {}.type)
                        val current= RelevesCapteurs((myMap3["id"] as Double).toInt(),(myMap3["IdCapteur"] as Double).toInt(),(myMap3["DateAjout"] as Double).toLong(),(myMap3["Valeur"] as Double).toInt())
                        Log.e("debug$i","value ${current.ID} ${current.IdCapteur} ${current.dateAjout} ${current.valeur}")
                        mRelevesCapteursViewModel!!.insert(current)
                    }


                }
            }
        })

        val adapter = activity?.let { ListComponentAdapter(it.application) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        mListComponentViewModel?.allWords?.observe(viewLifecycleOwner, Observer { words ->
            words?.let {
                adapter?.setWords(it)
            }
        })
        Log.e("Passage","fin recycler view")
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun automaticAddData(){

        mListeTypeAlerteViewModel?.allWords?.observe(viewLifecycleOwner, { words ->
            if (words.size<2){
                for (i in 1..5) {
                    mListeTypeAlerteViewModel?.insert(ListeTypeAlerte(i+1,i*2+1))
                }
            }
        })
        mAlerteViewModel?.allWords?.observe(viewLifecycleOwner, { words ->
            if (words.size<2){
                for (i in 1..5) {
                    mAlerteViewModel?.insert(Alerte(i+1,i,i*2+10))
                }
            }
        })
        mBacViewModel?.allWords?.observe(viewLifecycleOwner, { words ->
            if (words.size<2){
                for (i in 1..5) {
                    mBacViewModel?.insert(Bac(i+1,i+1,(i+2)%3, "bac num $i"))
                }
            }
        })
        mListeBacPositionViewModel?.allWords?.observe(viewLifecycleOwner, { words ->
            if (words.size<2){
                for (i in 1..5) {
                    mListeBacPositionViewModel?.insert(ListeBacPosition(i,i+1,i+2))
                }
            }
        })
        mAlerteRecuViewModel?.allWords?.observe(viewLifecycleOwner, { words ->
            if (words.size<2){
                for (i in 1..5) {
                    mAlerteRecuViewModel?.insert(AlerteRecu(i,System.currentTimeMillis(),i+2))
                }
            }
        })
    }
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
        return format.format(date)
    }

    fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }

    fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return df.parse(date).time
    }

}