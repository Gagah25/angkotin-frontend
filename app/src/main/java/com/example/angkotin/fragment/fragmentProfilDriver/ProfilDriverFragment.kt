package com.example.angkotin.fragment.fragmentProfilDriver

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.angkotin.databinding.FragmentProfilDriverBinding
import com.example.angkotin.fragment.fragmentMap.MapFragment
import com.example.angkotin.viewModel.MapViewModel
import com.example.angkotin.viewModel.SharedViewModel

class ProfilDriverFragment : Fragment(){
    private var _binding: FragmentProfilDriverBinding? = null
    private val binding get() = _binding!!
    private lateinit var mapViewModel: MapViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var name: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfilDriverBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MapViewModel::class.java)
        Log.d("DriverProfilName", sharedViewModel.getNameDriver().value.toString())
        mapViewModel.setDataFirebase()
        name = sharedViewModel.getNameDriver().value.toString()

        mapViewModel.getDataFirebase().observe(viewLifecycleOwner, {
            for (angkot in mapViewModel.getDataFirebase().value!!){
                if (name == angkot.name){
                    binding.apply {
                        userDetail.text = angkot.name
                        tvPlat.text = angkot.driverMeta?.angkotNumber
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}