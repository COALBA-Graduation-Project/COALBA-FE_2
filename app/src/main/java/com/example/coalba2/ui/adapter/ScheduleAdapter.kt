package com.example.coalba2.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba2.R
import com.example.coalba2.data.response.ScheduleData
import com.example.coalba2.databinding.ItemScheduleBinding
import com.example.coalba2.databinding.ItemWeekcalendarBinding

class ScheduleAdapter(private val context: Context) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {
    var datas = mutableListOf<ScheduleData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleAdapter.ViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ScheduleAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }
    inner class ViewHolder(private val binding: ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: ScheduleData){
            binding.tvScheduleName.text = item.name
            binding.tvScheduleStarttime.text = item.starttime
            binding.tvScheduleEndtime.text = item.endtime
            // todo : status값이 BEFORE_WORK일 경우에만 ic_delete 이미지가 보여지는거 확인해야함!
            if (item.status == "BEFORE_WORK"){
                binding.ivScheduleDelete.visibility = View.VISIBLE
            }
        }
    }
}