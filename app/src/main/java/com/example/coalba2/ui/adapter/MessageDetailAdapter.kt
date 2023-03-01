package com.example.coalba2.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba2.R
import com.example.coalba2.data.response.MessageDetailData

class MessageDetailAdapter(private val context: Context): RecyclerView.Adapter<MessageDetailAdapter.ViewHolder>() {
    var datas = mutableListOf<MessageDetailData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_messagedetail,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val txtState: TextView = itemView.findViewById(R.id.tv_messagedetail_state)
        private val txtDate: TextView = itemView.findViewById(R.id.tv_messagedetail_date)
        private val txtContent: TextView = itemView.findViewById(R.id.tv_messagedetail_content)

        fun bind(item: MessageDetailData){
            txtState.text = item.state
            txtDate.text = item.date
            txtContent.text = item.messageContent
            if(txtState.text == "보낸쪽지"){
                txtState.setTextColor(getColor(context, R.color.send_message))
            }
        }
    }
}