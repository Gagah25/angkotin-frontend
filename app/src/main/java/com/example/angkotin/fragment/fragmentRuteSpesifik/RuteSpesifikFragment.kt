package com.example.angkotin.fragment.fragmentRuteSpesifik

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.angkotin.R
import com.example.angkotin.databinding.FragmentRuteSpesifikBinding

class RuteSpesifikFragment: Fragment() {
    private var _binding: FragmentRuteSpesifikBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRuteSpesifikBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}