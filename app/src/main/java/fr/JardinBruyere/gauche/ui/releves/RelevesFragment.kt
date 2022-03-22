package fr.JardinBruyere.gauche.ui.releves

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
import fr.JardinBruyere.gauche.R
import fr.JardinBruyere.gauche.databinding.FragmentSlideshowBinding
import fr.JardinBruyere.gauche.database.sensor.SensorViewModel
import fr.JardinBruyere.gauche.database.sensorReading.SensorReadingAdapter
import fr.JardinBruyere.gauche.database.sensorReading.SensorReadingViewModel

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
        val mRelevesCapteursViewModel = ViewModelProvider(this)[SensorReadingViewModel::class.java]
        val mComponentViewModel = ViewModelProvider(this)[SensorViewModel::class.java]


        val adapter = activity?.let { SensorReadingAdapter(it.application) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        mRelevesCapteursViewModel.allWords.observe(viewLifecycleOwner, Observer { words ->
            words?.let {
                adapter?.setWords(it)
            }
        })


        val termsList = ArrayList<Int>()
        mComponentViewModel!!.allWords.observe(viewLifecycleOwner,
            { elem ->
                for (current in elem) {
                    current.Id?.let { termsList.add(it) }
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


        Log.e("Passage","fin recycler view")
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}