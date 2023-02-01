package com.example.coalba2.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coalba2.R
import com.example.coalba2.data.response.SubstituteWorkScheduleData
import com.example.coalba2.databinding.ItemSubstituteworkManageScheduleBinding

class SubstituteWorkScheduleAdapter: RecyclerView.Adapter<SubstituteWorkScheduleAdapter.ViewHolder>() {
    lateinit var itemList: MutableList<SubstituteWorkScheduleData>

    fun build(i: MutableList<SubstituteWorkScheduleData>) : SubstituteWorkScheduleAdapter{
        itemList = i
        return this
    }
    inner class ViewHolder(val binding: ItemSubstituteworkManageScheduleBinding) : RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
        fun bind(item: SubstituteWorkScheduleData){
            Glide.with(itemView).load(item.imgProfile1).into(binding.ivSubstituteworkManageProfile1)
            Glide.with(itemView).load(item.imgProfile2).into(binding.ivSubstituteworkManageProfile2)
            with(binding){
                tvSubstituteworkManageName1.text = item.name1
                tvSubstituteworkManageName2.text = item.name2
                tvSubstituteworkManageState.text = item.state
                tvSubstituteworkManageStore.text = item.storename
                tvSubstituteworkManageDate.text = item.date
                tvSubstituteworkManageStarttime.text = item.starttime
                tvSubstituteworkManageEndtime.text = item.endtime
                if(tvSubstituteworkManageState.text == "거절"){
                    tvSubstituteworkManageState.visibility = View.VISIBLE
                    tvSubstituteworkManageState.setTextColor(ContextCompat.getColor(context, R.color.refuse))
                }
                else if(tvSubstituteworkManageState.text == "수락"){
                    tvSubstituteworkManageState.visibility = View.VISIBLE
                }
            }
            /*
            itemView.setOnClickListener {
                val intent = Intent(context, DiaryDetailActivity::class.java)
                intent.run { context.startActivity(this) }
            }*/
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemSubstituteworkManageScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}