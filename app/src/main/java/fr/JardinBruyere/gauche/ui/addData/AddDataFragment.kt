package fr.JardinBruyere.gauche.ui.addData

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.JardinBruyere.gauche.CustomAdapter
import fr.JardinBruyere.gauche.R
import fr.JardinBruyere.gauche.databinding.FragmentAdddataBinding
import fr.JardinBruyere.gauche.database.sensor.SensorViewModel
import fr.JardinBruyere.gauche.database.sensorTypes.SensorTypesViewModel
import fr.JardinBruyere.gauche.CustomAdapter.modelArrayList
import fr.JardinBruyere.gauche.MultiLineChartActivity

import fr.JardinBruyere.gauche.database.sensor.Sensor


class AddDataFragment : Fragment() {

    private var _binding: FragmentAdddataBinding? = null
    private var mSensorTypesViewModel: SensorTypesViewModel? = null
    private var mSensorViewModel: SensorViewModel? = null
    private val binding get() = _binding!!

    val listOfClicked = ArrayList<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentAdddataBinding.inflate(inflater, container, false)
        mSensorTypesViewModel = ViewModelProvider(this)[SensorTypesViewModel::class.java]
        mSensorViewModel = ViewModelProvider(this)[SensorViewModel::class.java]
        val root: View = binding.root
        val boutonAddData: Button =root.findViewById(R.id.validateNewCaptor)
        boutonAddData.setOnClickListener {
            Log.e("Checked", CustomAdapter.list.toString())

            startActivity(Intent(context, MultiLineChartActivity::class.java))
        }
        val lv = root.findViewById(R.id.lv) as ListView

        val list = ArrayList<Sensor>()
        mSensorViewModel =                ViewModelProvider(this)[SensorViewModel::class.java]
        mSensorViewModel!!.allWords.observe(viewLifecycleOwner, { it ->
            it.forEach {
                Log.e("hello",it.Name)
                list.add(it)
            }

            modelArrayList = list
            val customAdapter = CustomAdapter(context, modelArrayList)
           if(list.size!=0)   lv.adapter = customAdapter
        })

        Log.e("Passage","fin recycler view")
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}