package com.example.gauche.ui.typecomposant

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gauche.ApiHelper
import com.example.gauche.R
import com.example.gauche.ResultApiObject
import com.example.gauche.database.component.*
import com.example.gauche.database.listComponent.ListComponent
import com.example.gauche.database.listComponent.ListComponentAdapter
import com.example.gauche.database.listComponent.ListComponentViewModel
import com.example.gauche.databinding.FragmentHomeBinding
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import retrofit2.Call
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type;
import com.google.gson.GsonBuilder







class TypeComposantsFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var mComponentViewModel: ListComponentViewModel? = null
    private var mListeTypeAlerteViewModel: ListeTypeAlerteViewModel? = null
    private var mAlerteViewModel: AlerteViewModel? = null
    private var mBacViewModel: BacViewModel? = null
    private var mListeBacPositionViewModel: ListeBacPositionViewModel? = null
    private var mAlerteRecuViewModel: AlerteRecuViewModel? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView: RecyclerView = root.findViewById(R.id.recycleur_view_ListCompenent)
        mComponentViewModel = ViewModelProvider(this)[ListComponentViewModel::class.java]
        mComponentViewModel?.allWords?.observe(viewLifecycleOwner, { words ->
            if (words.size<2){
                for (i in 1..5) {
                    mComponentViewModel?.insert(ListComponent( "test $i", "°C"))
                }
            }
        })

        val service = ApiHelper.create()
        service.search().enqueue(object : retrofit2.Callback<Any> {
            override fun onFailure(call: Call<Any>?, t: Throwable?) {
                Log.e("retrofit ko", "call failed")

                Toast.makeText(context,"ERROR", Toast.LENGTH_SHORT).show();
                if (t != null) {
                    Toast.makeText(context,"fail "+t.localizedMessage, Toast.LENGTH_SHORT).show()
                };
            }
            override fun onResponse(call: Call<Any>?, response: retrofit2.Response<Any>?) {
                if (response != null) {
                    val value= response.body()
                    val test = listOf(value)[0].toString()
                    val gson = GsonBuilder()
                        .setLenient()
                        .create()
                    val type: Type = object : TypeToken<Map<Any?, Any?>?>() {}.type
                    val myMap: Map<Any, Any> =gson.fromJson(test, type)
                    val ListeTypeComposants: Map<Any, Any> =gson.fromJson((myMap["data"] as List<*>)[0].toString(), object : TypeToken<Map<Any?, Any?>?>() {}.type)
                    val ListeTypeAlerte: Map<Any, Any> =gson.fromJson((myMap["data"] as List<*>)[1].toString(), object : TypeToken<Map<Any?, Any?>?>() {}.type)


                    val myMap3_0: Map<Any, Any> =gson.fromJson((ListeTypeComposants["ListeTypeComposants"] as List<*>)[0].toString(), object : TypeToken<Map<Any?, Any?>?>() {}.type)
                    Log.e("debug","value ${myMap3_0["NomComposant"]} ${myMap3_0["typeComposant"]}");

                    val myMap3: Map<Any, Any> =gson.fromJson((ListeTypeAlerte["ListeTypeAlerte"] as List<*>)[1].toString(),  object : TypeToken<Map<Any?, Any?>?>() {}.type)
                    Log.e("debug","value ${myMap3["Criticite"]} ${myMap3["MethodeNotification"]} ${myMap3["TypeAlerte"]}");

                }
            }
        })

        automaticAddData()
        val adapter = activity?.let { ListComponentAdapter(it.application) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        mComponentViewModel?.allWords?.observe(viewLifecycleOwner, Observer { words ->
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

        mListeTypeAlerteViewModel = ViewModelProvider(this)[ListeTypeAlerteViewModel::class.java]
        mListeTypeAlerteViewModel?.allWords?.observe(viewLifecycleOwner, { words ->
            if (words.size<2){
                for (i in 1..5) {
                    mListeTypeAlerteViewModel?.insert(ListeTypeAlerte(i+1,i*2+1))
                }
            }
        })
        mAlerteViewModel = ViewModelProvider(this)[AlerteViewModel::class.java]
        mAlerteViewModel?.allWords?.observe(viewLifecycleOwner, { words ->
            if (words.size<2){
                for (i in 1..5) {
                    mAlerteViewModel?.insert(Alerte(i+1,i,i*2+10))
                }
            }
        })
        mBacViewModel = ViewModelProvider(this)[BacViewModel::class.java]
        mBacViewModel?.allWords?.observe(viewLifecycleOwner, { words ->
            if (words.size<2){
                for (i in 1..5) {
                    mBacViewModel?.insert(Bac(i+1,i+1,(i+2)%3, "bac num $i"))
                }
            }
        })
        mListeBacPositionViewModel = ViewModelProvider(this)[ListeBacPositionViewModel::class.java]
        mListeBacPositionViewModel?.allWords?.observe(viewLifecycleOwner, { words ->
            if (words.size<2){
                for (i in 1..5) {
                    mListeBacPositionViewModel?.insert(ListeBacPosition(i,i+1,i+2))
                }
            }
        })
        mAlerteRecuViewModel = ViewModelProvider(this)[AlerteRecuViewModel::class.java]
        mAlerteRecuViewModel?.allWords?.observe(viewLifecycleOwner, { words ->
            if (words.size<2){
                for (i in 1..5) {
                    mAlerteRecuViewModel?.insert(AlerteRecu(i,System.currentTimeMillis(),i+2))
                }
            }
        })
    }
}