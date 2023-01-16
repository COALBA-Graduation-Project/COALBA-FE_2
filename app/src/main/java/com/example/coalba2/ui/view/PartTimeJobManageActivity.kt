package com.example.coalba2.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coalba2.R
import com.example.coalba2.data.response.PartTimeManageData
import com.example.coalba2.databinding.ActivityPartTimeJobManageBinding
import com.example.coalba2.ui.adapter.PartTimeJobManageAdapter

class PartTimeJobManageActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityPartTimeJobManageBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    lateinit var partTimeJobManageAdapter: PartTimeJobManageAdapter
    val datas = mutableListOf<PartTimeManageData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityPartTimeJobManageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivParttimeManagePlus.setOnClickListener {
            val intent = Intent(this, PartTimeJobAddActivity::class.java)
            startActivity(intent)
        }
        initRecycler()
    }
    private fun initRecycler(){
        partTimeJobManageAdapter = PartTimeJobManageAdapter(this)
        binding.rvParttimeManage.adapter = partTimeJobManageAdapter

        datas.apply {
            add(PartTimeManageData(img = R.drawable.ic_emptyimg, name = "조예진", phonenumber = "01012345678", birth = "1999.05.29"))
            add(PartTimeManageData(img = R.drawable.ic_emptyimg, name = "신지연", phonenumber = "01087654321", birth = "1999.11.18"))
            add(PartTimeManageData(img = R.drawable.ic_emptyimg, name = "김다은", phonenumber = "01012234524", birth = "1999.07.03"))

            partTimeJobManageAdapter.datas = datas
            partTimeJobManageAdapter.notifyDataSetChanged()
        }
    }
}