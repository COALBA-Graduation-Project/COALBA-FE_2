package com.example.coalba2.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coalba2.R
import com.example.coalba2.data.response.PartTimeManageData
import com.example.coalba2.data.response.SubstituteWorkDateData
import com.example.coalba2.data.response.SubstituteWorkScheduleData
import com.example.coalba2.databinding.ActivitySubstituteWorkManageBinding
import com.example.coalba2.ui.adapter.SubstituteWorkDateAdapter

class SubstituteWorkManageActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivitySubstituteWorkManageBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivitySubstituteWorkManageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycler()
    }
    private fun initRecycler(){
        var itemList = mutableListOf(
            SubstituteWorkDateData("2023년 1월", mutableListOf(SubstituteWorkScheduleData(R.drawable.ic_emptyimg, "신지연", R.drawable.ic_emptyimg, "김다은", "거절", "송이커피 숙대점", "01/28", "13:00", "18:00"), SubstituteWorkScheduleData(R.drawable.ic_emptyimg, "김다은", R.drawable.ic_emptyimg, "조예진", "수락", "송이커피 숙대점", "01/13", "16:00", "22:00"))),
            SubstituteWorkDateData("2022년 12월", mutableListOf(SubstituteWorkScheduleData(R.drawable.ic_emptyimg, "조예진", R.drawable.ic_emptyimg, "김다은", "수락", "송이마라탕 숙대점", "12/18", "11:00", "17:00"))
            )
        )
        binding.rvSubstituteworkDate.adapter = SubstituteWorkDateAdapter().build(itemList)
        binding.rvSubstituteworkDate.layoutManager = LinearLayoutManager(this)
    }
}