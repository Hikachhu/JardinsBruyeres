package com.example.gauche.ui.releves

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gauche.R
import com.example.gauche.database.GenerateData
import com.example.gauche.database.component.ComponentViewModel
import com.example.gauche.database.relevesCapteurs.RelevesCapteursAdapter
import com.example.gauche.database.relevesCapteurs.RelevesCapteursViewModel
import com.example.gauche.databinding.FragmentSlideshowBinding

class RelevesFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView: RecyclerView = root.findViewById(R.id.recycleur_view_RelevesCapteurs)
        val boutonDelete: Button=root.findViewById(R.id.buttonRelevesCapteurs)
        val mRelevesCapteursViewModel = ViewModelProvider(this)[RelevesCapteursViewModel::class.java]
        val spinnerAddData: AutoCompleteTextView =root.findViewById(R.id.spinnerReleves)
        val mComponentViewModel = ViewModelProvider(this)[ComponentViewModel::class.java]

        val generateData = activity?.let { GenerateData(it.application) }
        generateData?.GenerateRelevesCapteurs(viewLifecycleOwner,3)

        val adapter = activity?.let { RelevesCapteursAdapter(it.application) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        mRelevesCapteursViewModel.allWords.observe(viewLifecycleOwner, Observer { words ->
            words?.let {
                adapter?.setWords(it)
            }
        })

        boutonDelete.setOnClickListener{
            mRelevesCapteursViewModel.deleteAll()
        }

        val termsList = ArrayList<String>()
        val spinnerAdapter: ArrayAdapter<String>? = context?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, termsList) }
        spinnerAddData.setAdapter(spinnerAdapter)
        spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mComponentViewModel!!.allWords.observe(viewLifecycleOwner,
            { elem ->
                for (current in elem) {
                    termsList.add(current.name)
                }
            })

     /*   spinnerAddData.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, rowId ->
                mRelevesCapteursViewModel.allWordsOfCapteur(position).observe(viewLifecycleOwner, Observer { words ->
                    words?.let {
                        adapter?.setWords(it)
                    }
                })
            }
*/
        spinnerAdapter?.notifyDataSetChanged()


        Log.e("Passage","fin recycler view")
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}