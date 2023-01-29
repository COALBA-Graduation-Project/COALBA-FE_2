package com.example.coalba2.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coalba2.R
import com.example.coalba2.data.response.ScheduleAddPersonData

class ScheduleAddAdapter(private val context: Context): RecyclerView.Adapter<ScheduleAddAdapter.ViewHolder>() {
    var datas = mutableListOf<ScheduleAddPersonData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(context).inflate(R.layout.item_schedule_putperson, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val imgProfile: ImageView = itemView.findViewById(R.id.iv_schedule_putperson)
        private val txtName: TextView = itemView.findViewById(R.id.tv_schedule_putperson_name)

        fun bind(item: ScheduleAddPersonData) {
            Glide.with(itemView).load(item.img).into(imgProfile)
            txtName.text = item.name
        }
    }
}