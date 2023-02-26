package com.example.coalba2.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coalba2.data.response.HomeWorkspaceData
import com.example.coalba2.databinding.ItemHomeWorkspaceBinding

class HomeWorkspaceAdapter: RecyclerView.Adapter<HomeWorkspaceAdapter.ViewHolder>() {
    lateinit var items: ArrayList<HomeWorkspaceData>

    fun build(i: ArrayList<HomeWorkspaceData>): HomeWorkspaceAdapter{
        items = i
        return this
    }
    class ViewHolder(val binding:ItemHomeWorkspaceBinding, val context: Context): RecyclerView.ViewHolder(binding.root){
        fun bind(item: HomeWorkspaceData){
            with(binding){
                Glide.with(itemView).load(item.img).into(ivHomeWorkspace)
                tvHomeWorkspace.text = item.name
                rvHomeWorkspaceStaff.apply {
                    adapter = HomeWorkspaceStaffAdapter().build(item.staffList)
                    layoutManager = LinearLayoutManager(context)
                }

                /*
                ivHomeWorkspaceDropdown.setOnClickListener {
                    if(rvHomeWorkspaceStaff.getVisibility() == View.INVISIBLE){
                        rvHomeWorkspaceStaff.visibility = View.VISIBLE
                    }
                    else{
                        rvHomeWorkspaceStaff.visibility = View.INVISIBLE
                    }
                }*/
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemHomeWorkspaceBinding.inflate(LayoutInflater.from(parent.context), parent, false), parent.context)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}