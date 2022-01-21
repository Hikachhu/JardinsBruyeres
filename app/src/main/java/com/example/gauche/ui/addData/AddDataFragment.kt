package com.example.gauche.ui.releves

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gauche.R
import com.example.gauche.database.listComponent.ListComponent
import com.example.gauche.database.listComponent.ListComponentViewModel
import com.example.gauche.databinding.FragmentAdddataBinding
import android.widget.AdapterView.OnItemClickListener
import com.example.gauche.database.component.Component
import com.example.gauche.database.component.ComponentViewModel


class AddDataFragment : Fragment() {

    private var _binding: FragmentAdddataBinding? = null
    private var mListComponentViewModel: ListComponentViewModel? = null
    private var mComponentViewModel: ComponentViewModel? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentAdddataBinding.inflate(inflater, container, false)
        mListComponentViewModel = ViewModelProvider(this)[ListComponentViewModel::class.java]
        mComponentViewModel = ViewModelProvider(this)[ComponentViewModel::class.java]
        val root: View = binding.root
        val boutonAddData: Button =root.findViewById(R.id.validateNewCaptor)
        val boutonSpinner:Button = root.findViewById(R.id.validateSpinner)
        val nameCaptor:TextView=root.findViewById(R.id.newNameCaptor)
        val unityCaptor:TextView=root.findViewById(R.id.newUnityCaptor)
        val spinnerAddData: AutoCompleteTextView = root.findViewById(R.id.autoCompleteTextView)
        val nomNouveauComposant:TextView=root.findViewById(R.id.nomNouveauComposant)
        var selection=0

        val termsList = ArrayList<String>()
        val spinnerAdapter: ArrayAdapter<String>? = context?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, termsList) }
        spinnerAddData.setAdapter(spinnerAdapter)
        spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mListComponentViewModel!!.allWords.observe(viewLifecycleOwner,
            { elem ->
                for (current in elem) {
                    termsList.add(current.nameComponent)

                }
            })

        spinnerAdapter?.notifyDataSetChanged()

        //Quand j'appuie sur un item de la liste
        spinnerAddData.onItemClickListener = OnItemClickListener { parent, view, position, rowId ->
            selection = position
        }

        //Quand j'appuie pour afficher la liste /!\ PAS TOUCHER /!\
        spinnerAddData.setOnClickListener {
            val manager:InputMethodManager=activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view?.windowToken,0)

        }

        //Bouton pour insert les 2 lignes en haut
        boutonAddData.setOnClickListener{
            mListComponentViewModel?.insert(ListComponent(
                nameCaptor.text.toString(),
                unityCaptor.text as Double
            ))
            Toast.makeText(context,  "Ajout de " +nameCaptor.text.toString()+ " | "+unityCaptor.text.toString(),Toast.LENGTH_LONG).show()
        }

        spinnerAdapter?.notifyDataSetChanged()

        //Quand on appuie sur le bouton a coté du truc qui ressemble à un spinner
        boutonSpinner.setOnClickListener{
            mComponentViewModel?.insert(Component(selection,System.currentTimeMillis(),0))
            Toast.makeText(context,  selection.toString()+" "+nomNouveauComposant.text,Toast.LENGTH_LONG).show()
        }

        Log.e("Passage","fin recycler view")
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}