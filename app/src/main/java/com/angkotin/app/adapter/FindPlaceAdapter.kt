package com.angkotin.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angkotin.app.data.PredictionsItem
import com.angkotin.app.databinding.ItemFindPlaceBinding

class FindPlaceAdapter(): RecyclerView.Adapter<FindPlaceAdapter.ViewHolder>() {

    private var list = ArrayList<PredictionsItem?>()
    private var onClickDetail: OnClickDetail? = null

    fun setOnClickDetail(onClickDetail: OnClickDetail){
        this.onClickDetail = onClickDetail
    }

    inner class ViewHolder(private val binding: ItemFindPlaceBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(find: PredictionsItem){
            binding.root.setOnClickListener {
                onClickDetail?.onItemClicked(find)
            }
            binding.apply {
                lokasi.text = find.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindPlaceAdapter.ViewHolder {
        val binding = ItemFindPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position]!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(places: ArrayList<PredictionsItem?>){
        list.clear()
        list.addAll(places)
        notifyDataSetChanged()
    }

    interface OnClickDetail {
        fun onItemClicked(dataUser: PredictionsItem)
    }
}