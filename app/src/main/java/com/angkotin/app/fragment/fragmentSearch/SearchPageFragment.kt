package com.angkotin.app.fragment.fragmentSearch

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.angkotin.app.R
import com.angkotin.app.adapter.FindPlaceAdapter
import com.angkotin.app.data.PredictionsItem
import com.angkotin.app.databinding.FragmentSearchPageBinding
import com.angkotin.app.viewModel.GeocodingViewModel
import com.angkotin.app.viewModel.SearchPlacesViewModel
import com.angkotin.app.viewModel.SharedViewModel

class SearchPageFragment : Fragment() {
    private var _binding: FragmentSearchPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FindPlaceAdapter
    private lateinit var searchViewModel: SearchPlacesViewModel
    private lateinit var geocodingViewModel: GeocodingViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var placeId: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchPageBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FindPlaceAdapter()

        searchViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SearchPlacesViewModel::class.java)
        geocodingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(GeocodingViewModel::class.java)

        binding.apply {
            rvFindPlaces.setVisibility(View.VISIBLE)
            rvFindPlaces.layoutManager = LinearLayoutManager(requireActivity())
            rvFindPlaces.setHasFixedSize(true)
            rvFindPlaces.adapter = adapter
        }
        searchViewModel.getFindPlace().observe(viewLifecycleOwner, {
            if (it != null){
                adapter.setList(it)
            }
        })
        searchUser()

        adapter.setOnClickDetail(object : FindPlaceAdapter.OnClickDetail{
            override fun onItemClicked(dataUser: PredictionsItem) {
                placeId = searchViewModel.getPlaceId().value!!
                Log.d("PlaceID", placeId)
                sharedViewModel.setPlaceId(placeId)
                geocodingViewModel.setGeocode(placeId)
                Log.d("Geocoding", geocodingViewModel.getGeocode().toString())
                view?.findNavController()?.navigate(R.id.action_searchPageFragment_to_mapFragment)
            }
        })

        binding.buttonBack.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_searchPageFragment_to_mapFragment)
        }
    }

    private fun searchUser(){
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.searchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(R.string.find_place)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {


                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                searchViewModel.setFindPlace(newText)

                return false
            }
        })
    }
}