package com.example.coalba2.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.coalba2.R
import com.example.coalba2.data.response.ScheduleData
import com.example.coalba2.data.response.StoreListData
import com.example.coalba2.databinding.ActivityWorkspaceHomeBinding
import com.example.coalba2.ui.adapter.MonthAdapter
import com.example.coalba2.ui.adapter.ScheduleAdapter
import com.example.coalba2.ui.fragment.MessageFragment

class WorkspaceHomeActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityWorkspaceHomeBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    lateinit var scheduleAdapter: ScheduleAdapter
    val datas = mutableListOf<ScheduleData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityWorkspaceHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // rv_workspacehome_calendar 리사이클러뷰는 각 월을 나타낼 리스트로서 가로로 전환하기 위하여 LinearLayoutManager의 Horizontal 속성을 준다
        val monthListManager= LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val monthListAdapter = MonthAdapter()

        binding.rvWorkspacehomeCalendar.apply {
            layoutManager = monthListManager
            adapter = monthListAdapter
            // scrollToPosition은 리스트를 item의 위치를 지정한 곳에서 시작. 해당 위치에서 리스트를 시작하는 이유는 Adapter부분에서 설명
            scrollToPosition(Int.MAX_VALUE/2)
        }
        // PagerSnapHelper()를 설정함으로써 한 항목씩 스크롤이 되도록 만들 수 있다.
        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(binding.rvWorkspacehomeCalendar)
        initRecycler()

        val data = intent.getParcelableExtra<StoreListData>("data")
        binding.tvWorkspacehome.text = data!!.name

        binding.ivWorkspacehomeBack.setOnClickListener {
            finish()
        }
        binding.ivWorkspacehomeManage.setOnClickListener {
            val intent = Intent(this, PartTimeJobManageActivity::class.java)
            startActivity(intent)
        }
        binding.ivWorkspacehomeScheduleplus.setOnClickListener {
            val intent = Intent(this, ScheduleAddActivity::class.java)
            startActivity(intent)
        }
        binding.ivWorkspacehomeMessagebox.setOnClickListener {
            val intent = Intent(this, MessageBoxActivity::class.java)
            startActivity(intent)
        }
    }

    // todo : 통신 후 서버에서 넘겨주는 스케줄 상태값이 BEFORE_WORK일 경우에만 ic_delete 이미지가 보여지도록 수정해야 함!
    private fun initRecycler(){
        scheduleAdapter = ScheduleAdapter(this)
        binding.rvSchedule.adapter = scheduleAdapter

        datas.apply {
            add(ScheduleData(name = "신지연", time = "14:00-19:00"))
            add(ScheduleData(name = "조예진", time = "15:00-21:00"))
            add(ScheduleData(name = "김다은", time = "11:00-13:30"))
            scheduleAdapter.datas = datas
            scheduleAdapter.notifyDataSetChanged()
        }
    }

    // 액티비티가 파괴될 때..
    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 함
        mBinding = null
        super.onDestroy()
    }
}