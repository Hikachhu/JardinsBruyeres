package com.example.gauche.ui.slideshow

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
import com.example.gauche.R
import com.example.gauche.database.GenerateData
import com.example.gauche.database.relevesCapteurs.RelevesCapteurs
import com.example.gauche.database.relevesCapteurs.RelevesCapteursAdapter
import com.example.gauche.database.relevesCapteurs.RelevesCapteursViewModel
import com.example.gauche.databinding.FragmentSlideshowBinding
import java.sql.Timestamp

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = root.findViewById(R.id.recycleur_view_RelevesCapteurs)
        val mRelevesCapteursViewModel = ViewModelProvider(this)[RelevesCapteursViewModel::class.java]


        var generateData = activity?.let { GenerateData(it.application) }
        generateData?.GenerateRelevesCapteurs(15)

        val adapter = activity?.let { RelevesCapteursAdapter(it.application) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        mRelevesCapteursViewModel.allWords.observe(viewLifecycleOwner, Observer { words ->
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