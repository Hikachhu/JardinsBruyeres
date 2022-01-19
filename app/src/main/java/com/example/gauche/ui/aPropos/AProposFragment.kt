package com.example.gauche.ui.aPropos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gauche.ApiHelper
import com.example.gauche.databinding.FragmentAproposBinding
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.gauche.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AProposFragment : Fragment() {

    private var _binding: FragmentAproposBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAproposBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val text:TextView=root.findViewById(R.id.textView2)
        text.text="GO"


        Log.e("Passage","fin recycler view A propos")
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}