package com.example.coalba2.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.coalba2.data.response.WorkHistoryData
import com.example.coalba2.databinding.ActivityWorkHistoryBinding
import com.example.coalba2.databinding.ActivityWorkspaceHomeBinding
import com.example.coalba2.ui.adapter.WorkHistoryAdapter

class WorkHistoryActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityWorkHistoryBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    lateinit var workhistoryAdapter: WorkHistoryAdapter
    val datas = mutableListOf<WorkHistoryData>()
    var workspaceArray: Array<String> = emptyArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityWorkHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 워크스페이스 spinner
        workspaceArray = workspaceArray.plus("안녕안녕")
        val workspaceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,workspaceArray)
        // Specify the layout to use when the list of choices appears
        workspaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        binding.spinWorkhistory.adapter = workspaceAdapter
        initRecycler()
    }
    private fun initRecycler(){
        workhistoryAdapter = WorkHistoryAdapter(this)
        binding.rvWorkhistory.adapter = workhistoryAdapter

        datas.apply {
            add(WorkHistoryData(name = "조예진", hour="28", minute = "30", workPay = "320,600"))
            add(WorkHistoryData(name = "신지연", hour="32", minute = "20", workPay = "320,900"))
            workhistoryAdapter.datas = datas
            workhistoryAdapter.notifyDataSetChanged()
        }
    }
    // 액티비티가 파괴될 때..
    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 함
        mBinding = null
        super.onDestroy()
    }
}