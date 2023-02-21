package com.example.coalba2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba2.R
import com.example.coalba2.data.response.WeekCalendarData
import com.example.coalba2.databinding.ItemWeekcalendarBinding
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter.ofPattern

class WeekCalendarAdapter(private val wcList: List<WeekCalendarData>) : RecyclerView.Adapter<WeekCalendarAdapter.WeekCalendarViewHolder>() {
    class WeekCalendarViewHolder(private val binding: ItemWeekcalendarBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item:WeekCalendarData){
            binding.tvDate.text = item.date
            binding.tvDay.text = item.day
            val today = binding.tvDate.text.toString()
            // 오늘 날짜
            val now = LocalDate.now().format(ofPattern("d"))

            // 오늘 날짜와 캘린더의 오늘 날짜가 같을 경우 background_blue 적용하기
            if (today == now) {
                binding.tvDate.setBackgroundResource(R.drawable.bg_weekcalendar_choose)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekCalendarViewHolder {
        val binding = ItemWeekcalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeekCalendarViewHolder(binding)
    }
    override fun onBindViewHolder(holder: WeekCalendarViewHolder, position: Int) {
        holder.bind(wcList[position])
    }
    override fun getItemCount() = wcList.size
}