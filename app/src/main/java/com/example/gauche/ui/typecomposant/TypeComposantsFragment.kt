package com.example.gauche.ui.typecomposant

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gauche.database.listComponent.ListComponent
import com.example.gauche.R
import com.example.gauche.database.component.*
import com.example.gauche.database.listComponent.ListComponentAdapter
import com.example.gauche.database.listComponent.ListComponentViewModel
import com.example.gauche.databinding.FragmentHomeBinding
import java.lang.Thread.sleep

class TypeComposantsFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var mComponentViewModel: ListComponentViewModel? = null
    private var mListeTypeAlerteViewModel: ListeTypeAlerteViewModel? = null
    private var mAlerteViewModel: AlerteViewModel? = null
    private var mBacViewModel: BacViewModel? = null
    private var mListeBacPositionViewModel: ListeBacPositionViewModel? = null

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
}