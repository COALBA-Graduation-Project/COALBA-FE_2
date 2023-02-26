package com.example.coalba2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba2.data.response.HomeWorkspaceStaffData
import com.example.coalba2.databinding.ItemHomeWorkspaceStaffBinding

class HomeWorkspaceStaffAdapter : RecyclerView.Adapter<HomeWorkspaceStaffAdapter.ViewHolder>(){
    lateinit var items: MutableList<HomeWorkspaceStaffData>

    fun build(i: MutableList<HomeWorkspaceStaffData>): HomeWorkspaceStaffAdapter{
        items = i
        return this
    }
    inner class ViewHolder(val binding: ItemHomeWorkspaceStaffBinding): RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
        fun bind(item: HomeWorkspaceStaffData){
            with(binding){
                tvHomeWorkspaceStaffStarttime.text = item.starttime
                tvHomeWorkspaceStaffEndtime.text = item.endtime
                tvHomeWorkspaceStaffName.text = item.name
                tvHomeWorkspaceStaffState.text = item.state
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemHomeWorkspaceStaffBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}