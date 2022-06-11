package com.angkotin.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.angkotin.app.adapter.DetailRouteAdapter
import com.angkotin.app.data.TrekAngkot
import com.angkotin.app.databinding.DetailRuteBinding

class DetailRouteAngkotActivity: AppCompatActivity() {
    private lateinit var binding: DetailRuteBinding
    private val list = TrekAngkot().angkotList
    private var angkotRute: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailRuteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailRoute = intent.getStringExtra(Extra_Routes)
        binding.rute.text = detailRoute

        list[detailRoute]?.map { angkotRute.add(it) }

        showRecyclerList()
    }

    private fun showRecyclerList() {
        binding.rvDetailRute.layoutManager = LinearLayoutManager(this)
        val listTrekAngkotAdapter = DetailRouteAdapter(angkotRute)
        binding.rvDetailRute.adapter = listTrekAngkotAdapter
    }

    companion object{
        const val Extra_Routes = "Extra_Routes"
    }
}