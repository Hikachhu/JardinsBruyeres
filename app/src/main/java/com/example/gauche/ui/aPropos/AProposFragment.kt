package com.example.gauche.ui.aPropos

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
import com.example.gauche.databinding.FragmentAproposBinding
import com.example.gauche.databinding.FragmentGalleryBinding
import java.sql.Timestamp

class AProposFragment : Fragment() {

    private var _binding: FragmentAproposBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAproposBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Log.e("Passage","fin recycler view A propos")
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}