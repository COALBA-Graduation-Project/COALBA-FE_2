package com.example.coalba2.ui.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.coalba2.api.retrofit.RetrofitManager
import com.example.coalba2.data.response.*
import com.example.coalba2.databinding.ActivityWorkspaceHomeBinding
import com.example.coalba2.ui.adapter.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class WorkspaceHomeActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityWorkspaceHomeBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    lateinit var scheduleAdapter: ScheduleAdapter
    val datas = mutableListOf<ScheduleData>()
    var storeId: Long = 0
    var sId: Long = 0
    var pos: Int = 0

    // 삭제 다이얼로그
    val positiveButtonClick = { dialogInterface: DialogInterface, i: Int ->
        // 해당 스케줄 삭제 서버 연동
        RetrofitManager.scheduleService?.scheduleDelete(sId)?.enqueue(object :
            Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("ScheduleDelete", "success")
                    datas.removeAt(pos)
                    scheduleAdapter.notifyDataSetChanged()
                } else {
                    // 이곳은 에러 발생할 경우 실행됨
                    Log.d("ScheduleDelete", "fail")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("ScheduleDelete", "error")
            }
        })
        Toast.makeText(this@WorkspaceHomeActivity, "삭제되었습니다", Toast.LENGTH_SHORT).show()
    }
    val negativeButtonClick = { dialogInterface: DialogInterface, i: Int ->
        Toast.makeText(this@WorkspaceHomeActivity, "취소", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityWorkspaceHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<StoreListData>("data")
        binding.tvWorkspacehome.text = data!!.name
        var workspaceID = data!!.workspaceId

        scheduleAdapter = ScheduleAdapter(this@WorkspaceHomeActivity, object : ScheduleAdapter.ScheduleDeleteListener{
            // 삭제 버튼 누를 시에 삭제 다이얼로그 창 띄우기
            override fun click(scheduleId: Long, position: Int) {
                val builder = AlertDialog.Builder(this@WorkspaceHomeActivity)
                builder.setTitle("정말 삭제하시겠습니까?")
                    .setMessage("해당 스케줄을 삭제합니다.")
                    .setPositiveButton("확인",positiveButtonClick)
                    .setNegativeButton("취소", negativeButtonClick)
                val alertDialog = builder.create()
                alertDialog.show()
                sId = scheduleId
                pos = position
                val button1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                with(button1){
                    setTextColor(Color.RED)
                }
                val button2 = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                with(button2){
                    setTextColor(Color.BLUE)
                }
            }
        })

        // 나의 워크스페이스 요약 리스트 조회 서버 연동
        RetrofitManager.workspaceService?.workspaceBriefListLook()?.enqueue(object:
            Callback<WorkspaceBriefListLookResponseData> {
            override fun onResponse(
                call: Call<WorkspaceBriefListLookResponseData>,
                response: Response<WorkspaceBriefListLookResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("Network_WorkspaceBriefListLook", "success")
                    val data = response.body()
                    val num = data!!.workspaceList.count()
                    Log.d("num 값", "num 값 " + num)

                    for(i in 0..num-1){
                        val itemdata = response.body()?.workspaceList?.get(i)
                        if (itemdata!!.name == binding.tvWorkspacehome.text){
                            storeId = itemdata.workspaceId
                        }
                    }
                }else{
                    // 이곳은 에러 발생할 경우 실행됨
                    Log.d("Network_WorkspaceBriefListLook", "fail")
                }
            }
            override fun onFailure(call: Call<WorkspaceBriefListLookResponseData>, t: Throwable) {
                Log.d("Network_WorkspaceBriefListLook", "error")
            }
        })
        // 해당 워크스페이스 홈 달력 정보 조회 서버 연동
        RetrofitManager.scheduleService?.scheduleCalendar(workspaceID)?.enqueue(object:
            Callback<ScheduleCalendarResponseData> {
            override fun onResponse(
                call: Call<ScheduleCalendarResponseData>,
                response: Response<ScheduleCalendarResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("Network_ScheduleCalendar", "success")
                    val data = response.body()
                    Log.d("ScheduleCalendarData", data.toString())
                    val num = data!!.selectedSubPage!!.selectedScheduleList.count()
                    if(num == 0){
                        Toast.makeText(this@WorkspaceHomeActivity, "오늘은 휴무입니다!", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        binding.rvSchedule.adapter = scheduleAdapter

                        for(i in 0..num-1){
                            val itemdata = response.body()?.selectedSubPage?.selectedScheduleList?.get(i)
                            datas.add(ScheduleData(itemdata!!.scheduleId, itemdata.worker!!.name, itemdata.scheduleStartTime, itemdata.scheduleEndTime, itemdata.status))
                        }
                        scheduleAdapter.datas = datas
                        scheduleAdapter.notifyDataSetChanged()
                    }
                }else{
                    // 이곳은 에러 발생할 경우 실행됨
                    Log.d("Network_ScheduleCalendar", "fail")
                }
            }
            override fun onFailure(call: Call<ScheduleCalendarResponseData>, t: Throwable) {
                Log.d("Network_ScheduleCalendar", "error")
            }
        })

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

        binding.ivWorkspacehomeBack.setOnClickListener {
            finish()
        }
        binding.ivWorkspacehomeManage.setOnClickListener {
            val intent = Intent(this, PartTimeJobManageActivity::class.java)
            intent.putExtra("storeId", storeId)
            startActivity(intent)
        }
        binding.ivWorkspacehomeScheduleplus.setOnClickListener {
            val intent = Intent(this, ScheduleAddActivity::class.java)
            intent.putExtra("storeId", storeId)
            startActivity(intent)
        }
    }

    fun dayClick(year: Int, month: Int, day: Int){
        // 해당 워크스페이스 홈 해당 날짜 스케줄 조회 서버 연동
        RetrofitManager.scheduleService?.scheduleEachWorkspaceSchedule(storeId, year, month, day)?.enqueue(object:
            Callback<ScheduleEachWorkspaceScheduleResponseData> {
            override fun onResponse(
                call: Call<ScheduleEachWorkspaceScheduleResponseData>,
                response: Response<ScheduleEachWorkspaceScheduleResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("ScheduleCalendarClick", "success")
                    val data = response.body()
                    datas.removeAll(datas)
                    scheduleAdapter.notifyDataSetChanged()

                    val num = data!!.selectedScheduleList.count()
                    if(num == 0){
                        Toast.makeText(this@WorkspaceHomeActivity, "오늘은 휴무입니다!", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        binding.rvSchedule.adapter = scheduleAdapter

                        for(i in 0..num-1){
                            val itemdata = response.body()?.selectedScheduleList?.get(i)
                            datas.add(ScheduleData(itemdata!!.scheduleId, itemdata!!.worker!!.name, itemdata.scheduleStartTime, itemdata.scheduleEndTime, itemdata.status))
                        }
                        scheduleAdapter.datas = datas
                        scheduleAdapter.notifyDataSetChanged()
                    }
                }else{ // 이곳은 에러 발생할 경우 실행됨
                    Log.d("ScheduleCalendarClick", "fail")
                }
            }
            override fun onFailure(call: Call<ScheduleEachWorkspaceScheduleResponseData>, t: Throwable) {
                Log.d("ScheduleCalendarClick", "error")
            }
        })
    }

    // 액티비티가 파괴될 때..
    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 함
        mBinding = null
        super.onDestroy()
    }
}