package com.example.angkotin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.angkotin.databinding.ItemRuteAngkotMalangBinding

class RouteAdapter(private val listTrek: ArrayList<String>, private val onMoveClickListener: OnMoveClickListener): RecyclerView.Adapter<RouteAdapter.ListViewHolder>() {
    class ListViewHolder(private val binding: ItemRuteAngkotMalangBinding): RecyclerView.ViewHolder(binding.root) {
        var tvRute: TextView = binding.ruteAngkotMalang
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteAdapter.ListViewHolder {
        val binding = ItemRuteAngkotMalangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RouteAdapter.ListViewHolder, position: Int) {
        val data = listTrek[position]
        holder.tvRute.text = data
        holder.itemView.setOnClickListener {
            onMoveClickListener.onMoveItemClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return listTrek.size
    }

    interface OnMoveClickListener {
        fun onMoveItemClicked(position: Int)
    }
}