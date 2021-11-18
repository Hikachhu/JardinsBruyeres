package com.example.gauche.ui.gallery

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gauche.R
import com.example.gauche.database.component.Component
import com.example.gauche.database.component.ComponentAdapter
import com.example.gauche.database.component.ComponentViewModel
import com.example.gauche.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView: RecyclerView = root.findViewById(R.id.recycleur_view_Component)
        var mComponentViewModel = ViewModelProvider(this)[ComponentViewModel::class.java]
        Log.e("Recherche","1")
        for (i in 1..5) {
            mComponentViewModel.insert(Component(i,1, "Composant $i", System.currentTimeMillis(),1))
        }

        Log.e("Recherche","2")
        val adapter = activity?.let { ComponentAdapter(it.application) }
        recyclerView.adapter = adapter
        Log.e("Recherche","3")
        recyclerView.layoutManager= LinearLayoutManager(context)
        Log.e("Recherche","4")
        mComponentViewModel.allWords.observe(viewLifecycleOwner, { words ->
            words?.let {
                Log.e("Verif",it.toString())
                adapter?.setWords(it)
            }
        })
        adapter?.notifyDataSetChanged()
        Log.e("Passage","fin recycler view")
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}