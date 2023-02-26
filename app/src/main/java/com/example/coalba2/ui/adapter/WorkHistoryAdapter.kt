package com.example.coalba2.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba2.data.response.WorkHistoryData
import com.example.coalba2.databinding.ItemWorkhistoryBinding

class WorkHistoryAdapter(private val context: Context) : RecyclerView.Adapter<WorkHistoryAdapter.ViewHolder>() {
    var datas = mutableListOf<WorkHistoryData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkHistoryAdapter.ViewHolder {
        val binding = ItemWorkhistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: WorkHistoryAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }
    class ViewHolder(private val binding: ItemWorkhistoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: WorkHistoryData) {
            binding.tvWorkhistoryName.text = item.name
            binding.tvWorkhistoryHour.text = item.hour
            binding.tvWorkhistoryMinute.text = item.minute
            binding.tvWorkhistoryWon.text = item.workPay
        }
    }
}