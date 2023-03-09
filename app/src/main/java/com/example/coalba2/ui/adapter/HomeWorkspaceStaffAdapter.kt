package com.example.coalba2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba2.R
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
                if (item.logicalStartTime == null) {
                    tvHomeWorkspaceStaffStarttime.text = item.starttime //출근 전일 때에는 스케줄 시작 시간
                } else {
                    tvHomeWorkspaceStaffStarttime.text = item.logicalStartTime
                    if (item.logicalStartTime == item.starttime) tvHomeWorkspaceStaffStarttime.setTextColor(ContextCompat.getColor(context, R.color.main)) //근무 중
                    else tvHomeWorkspaceStaffStarttime.setTextColor(ContextCompat.getColor(context, R.color.refuse)) //지각
                }

                if (item.logicalEndTime == null) {
                    tvHomeWorkspaceStaffEndtime.text = item.endtime //퇴근 전일 때에는 스케줄 종료 시간
                } else {
                    tvHomeWorkspaceStaffEndtime.text = item.logicalEndTime
                    if (item.logicalEndTime == item.endtime) tvHomeWorkspaceStaffEndtime.setTextColor(ContextCompat.getColor(context, R.color.main)) //정상 퇴근
                    else tvHomeWorkspaceStaffEndtime.setTextColor(ContextCompat.getColor(context, R.color.refuse)) //조기 퇴근
                }

                tvHomeWorkspaceStaffName.text = item.name

                when (item.state) {
                    "BEFORE_WORK" -> { tvHomeWorkspaceStaffState.text = "근무전" }
                    "ON_DUTY" -> { tvHomeWorkspaceStaffState.text = "근무중" }
                    "LATE" -> { tvHomeWorkspaceStaffState.text = "지각" }
                    "SUCCESS " -> { tvHomeWorkspaceStaffState.text = "근무 완료" }
                    "FAIL" -> { tvHomeWorkspaceStaffState.text = "근무 실패" }
                }
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