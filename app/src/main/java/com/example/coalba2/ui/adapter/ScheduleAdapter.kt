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

class ScheduleAdapter(private val context: Context, private val scheduleDeleteListener: ScheduleDeleteListener) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {
    var datas = mutableListOf<ScheduleData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleAdapter.ViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ScheduleAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
        holder.itemView.setOnClickListener {
            scheduleDeleteListener.click(datas[position].scheduleId, position)
        }
    }
    inner class ViewHolder(private val binding: ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: ScheduleData){
            binding.tvScheduleName.text = item.name
            binding.tvScheduleStarttime.text = item.starttime
            binding.tvScheduleEndtime.text = item.endtime
            if (item.status == "BEFORE_WORK"){
                binding.ivScheduleDelete.visibility = View.VISIBLE
            }
        }
    }
    interface ScheduleDeleteListener{
        fun click(scheduleId: Long, position: Int)
    }
}