package com.example.coalba2.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coalba2.R
import com.example.coalba2.data.response.MessageBoxData
import com.example.coalba2.ui.view.MessageBoxActivity
import com.example.coalba2.ui.view.MessageDetailActivity

class MessageBoxAdapter(private val context: Context): RecyclerView.Adapter<MessageBoxAdapter.ViewHolder>() {
    var datas = mutableListOf<MessageBoxData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(context).inflate(R.layout.item_messagebox, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val imgProfile: ImageView = itemView.findViewById(R.id.iv_messagebox_profile)
        private val txtName: TextView = itemView.findViewById(R.id.tv_messagebox_name)
        private val txtDate: TextView = itemView.findViewById(R.id.tv_messagebox_date)
        private val txtContent: TextView = itemView.findViewById(R.id.tv_messagebox_content)

        fun bind(item: MessageBoxData){
            Glide.with(itemView).load(item.img).into(imgProfile)
            txtName.text = item.name
            txtDate.text = item.latestDateTime
            txtContent.text = item.latestMessage

            itemView.setOnClickListener {
                Intent(context, MessageDetailActivity::class.java).apply {
                    putExtra("dataForMessageDetail", item)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }
        }
    }
}