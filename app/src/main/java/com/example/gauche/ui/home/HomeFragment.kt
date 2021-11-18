package com.example.gauche.ui.home

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
import com.example.gauche.database.listComponent.ListComponentAdapter
import com.example.gauche.database.listComponent.ListComponentViewModel
import com.example.gauche.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var mComponentViewModel: ListComponentViewModel? = null
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
        for (i in 1..5) {
            mComponentViewModel?.insert(ListComponent(i, "test $i","°C"))
        }
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