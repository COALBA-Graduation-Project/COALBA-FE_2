package com.example.coalba2.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba2.data.response.SubstituteWorkDateData
import com.example.coalba2.data.response.SubstituteWorkScheduleData
import com.example.coalba2.databinding.ItemSubstituteworkManageDateBinding

class SubstituteWorkDateAdapter: RecyclerView.Adapter<SubstituteWorkDateAdapter.ViewHolder>() {
    lateinit var itemList: MutableList<SubstituteWorkDateData>

    fun build(i: MutableList<SubstituteWorkDateData>): SubstituteWorkDateAdapter{
        itemList = i
        return this
    }

    class ViewHolder(val binding: ItemSubstituteworkManageDateBinding, val context: Context) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: SubstituteWorkDateData) {
            with(binding){
                tvSubstituteworkManageDate.text = item.date
                rvSubstituteworkSchedule.apply {
                    adapter = SubstituteWorkScheduleAdapter().build(item.innerList)
                    layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemSubstituteworkManageDateBinding.inflate(LayoutInflater.from(parent.context), parent, false), parent.context)

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

}