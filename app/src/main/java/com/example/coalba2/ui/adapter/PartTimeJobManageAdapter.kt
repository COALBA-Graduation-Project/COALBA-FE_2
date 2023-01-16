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
import com.example.coalba2.data.response.PartTimeManageData

class PartTimeJobManageAdapter(private val context: Context) : RecyclerView.Adapter<PartTimeJobManageAdapter.ViewHolder>() {
    var datas = mutableListOf<PartTimeManageData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(context).inflate(R.layout.item_parttime_manage, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val imgProfile: ImageView = itemView.findViewById(R.id.iv_parttime_manage_profile)
        private val txtName: TextView = itemView.findViewById(R.id.tv_parttime_manage_name)
        private val txtPhonenumber: TextView = itemView.findViewById(R.id.tv_parttime_manage_phonenumber2)
        private val txtBirth: TextView = itemView.findViewById(R.id.tv_parttime_manage_birth2)

        fun bind(item: PartTimeManageData){
            Glide.with(itemView).load(item.img).into(imgProfile)
            txtName.text = item.name
            txtPhonenumber.text = item.phonenumber
            txtBirth.text = item.birth
        }
    }
}