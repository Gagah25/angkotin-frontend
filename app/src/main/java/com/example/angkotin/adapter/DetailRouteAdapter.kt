package com.example.angkotin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.angkotin.databinding.ItemDetailRuteBinding

class DetailRouteAdapter(private val listTrek: ArrayList<String>): RecyclerView.Adapter<DetailRouteAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemDetailRuteBinding): RecyclerView.ViewHolder(binding.root) {
        var tvRute: TextView = binding.ruteAngkotMalang
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDetailRuteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listTrek[position]
        holder.tvRute.text = data
    }

    override fun getItemCount(): Int {
        return listTrek.size
    }
}