package com.angkotin.app.fragment.fragmentSearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.angkotin.app.R
import com.angkotin.app.databinding.FragmentSearchPageBinding

class SearchPageFragment : Fragment() {
    private var _binding: FragmentSearchPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchPageBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_searchPageFragment_to_mapFragment)
        }
    }
}