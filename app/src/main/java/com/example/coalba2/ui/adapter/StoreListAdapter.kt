package com.example.coalba2.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba2.R
import com.example.coalba2.data.response.StoreListData
import com.example.coalba2.ui.view.WorkspaceHomeActivity

class StoreListAdapter(private val context: Context) : RecyclerView.Adapter<StoreListAdapter.ViewHolder>() {
    var datas = mutableListOf<StoreListData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_storelist,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val txtName: TextView = itemView.findViewById(R.id.tv_storelist_name)

        fun bind(item:StoreListData){
            txtName.text = item.name

            itemView.setOnClickListener {
                Intent(context, WorkspaceHomeActivity::class.java).apply {
                    putExtra("data", item)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }
        }
    }
}