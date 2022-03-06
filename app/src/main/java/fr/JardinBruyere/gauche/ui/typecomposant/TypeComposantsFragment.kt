package fr.JardinBruyere.gauche.ui.typecomposant

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
import fr.JardinBruyere.gauche.R
import fr.JardinBruyere.gauche.databinding.FragmentHomeBinding
import fr.JardinBruyere.gauche.database.sensorTypes.SensorTypesAdapter
import fr.JardinBruyere.gauche.database.sensorTypes.SensorTypesViewModel


class TypeComposantsFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var mSensorTypesViewModel: SensorTypesViewModel? = null
    private val binding get() = _binding!!

    @SuppressLint("CommitPrefEdits", "NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView: RecyclerView = root.findViewById(R.id.recycleur_view_ListCompenent)

        mSensorTypesViewModel =                ViewModelProvider(this)[SensorTypesViewModel::class.java]

        val adapter = activity?.let { SensorTypesAdapter(it.application) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        mSensorTypesViewModel?.allWords?.observe(viewLifecycleOwner, { words ->
            words?.let {
                adapter?.setWords(it)
                Log.e("wtf ?", it.toString())
            }
        })
        adapter?.notifyDataSetChanged()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}