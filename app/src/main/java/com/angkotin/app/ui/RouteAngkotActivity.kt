package com.angkotin.app.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.angkotin.app.adapter.RouteAdapter
import com.angkotin.app.data.TrekAngkot
import com.angkotin.app.databinding.RuteAngkotMalangBinding

class RouteAngkotActivity: AppCompatActivity(), RouteAdapter.OnMoveClickListener {
    private lateinit var binding: RuteAngkotMalangBinding
    private val list = TrekAngkot().angkotList
    private var angkotName: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RuteAngkotMalangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvRuteAngkot.setHasFixedSize(true)

        for(k in list.keys) {
            angkotName.add(k)
        }
        showRecyclerList()
        binding.buttonBack.setOnClickListener { moveToHome() }
    }

    private fun showRecyclerList() {
        binding.rvRuteAngkot.layoutManager = LinearLayoutManager(this)
        val listTrekAngkotAdapter = RouteAdapter(angkotName,this)
        binding.rvRuteAngkot.adapter = listTrekAngkotAdapter
    }

    private fun moveToHome(){
        val mIntent = Intent(this@RouteAngkotActivity, HomeActivity::class.java)
        startActivity(mIntent)
    }

    override fun onMoveItemClicked(position: Int) {
        val moveToDetailRute = Intent(this@RouteAngkotActivity, DetailRouteAngkotActivity::class.java)
        moveToDetailRute.putExtra(DetailRouteAngkotActivity.Extra_Routes, angkotName[position])
        startActivity(moveToDetailRute)
    }}