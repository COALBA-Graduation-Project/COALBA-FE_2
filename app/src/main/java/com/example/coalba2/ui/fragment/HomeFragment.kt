package com.example.coalba2.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coalba2.R
import com.example.coalba2.api.jwt.CoalbaApplication
import com.example.coalba2.api.retrofit.RetrofitManager
import com.example.coalba2.data.response.*
import com.example.coalba2.databinding.FragmentHomeBinding
import com.example.coalba2.ui.adapter.HomeWorkspaceAdapter
import com.example.coalba2.ui.adapter.ScheduleAdapter
import com.example.coalba2.ui.adapter.WeekCalendarAdapter
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.fragment_home.*
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter.ofPattern
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: FragmentHomeBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    lateinit var calendarAdapter: WeekCalendarAdapter
    private var calendarList = ArrayList<WeekCalendarData>()
    private var homeWorkspaceList = ArrayList<HomeWorkspaceData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 바인딩
        mBinding = FragmentHomeBinding.inflate(inflater,container,false)
        val root: View = binding.root
        AndroidThreeTen.init(context)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarAdapter = WeekCalendarAdapter(calendarList, object:WeekCalendarAdapter.HomeDayClickListener {
            override fun click(year: Int, month: Int, day: Int) {
                binding.tvHomeDate.text = "${year}년 ${month}월" //날짜 클릭 시 년월 텍스트 변경

                // 홈 가게별 해당 날짜 스케줄 조회 서버 연동 완료
                RetrofitManager.scheduleService?.scheduleEachWorkspace(year,month,day)?.enqueue(object:
                    Callback<ScheduleEachWorkspaceResponseData> {
                    override fun onResponse(
                        call: Call<ScheduleEachWorkspaceResponseData>,
                        response: Response<ScheduleEachWorkspaceResponseData>
                    ) {
                        if(response.isSuccessful){
                            Log.d("Network_Schedule2", "success")
                            val data = response.body()
                            homeWorkspaceList.clear() //기존 데이터 clear
                            homeWorkspaceList.addAll(
                                data!!.workspaceList.map {workspace ->
                                    HomeWorkspaceData(workspace.imageUrl, workspace.name,
                                        workspace.selectedScheduleList.map {schedule ->
                                            HomeWorkspaceStaffData(schedule.scheduleStartTime, schedule.scheduleEndTime, schedule.logicalStartTime, schedule.logicalEndTime, schedule.worker!!.name, schedule.status)
                                        }.toMutableList()
                                    )
                                }
                            )
                            //어댑터에 변경된 데이터 적용
                            binding.rvHomeWorkspace.adapter!!.notifyItemRangeChanged(0, homeWorkspaceList.size)
                        }else{
                            // 이곳은 에러 발생할 경우 실행됨
                            Log.d("Network_Schedule2", "fail")
                        }
                    }
                    override fun onFailure(call: Call<ScheduleEachWorkspaceResponseData>, t: Throwable) {
                        Log.d("Network_Schedule2", "error")
                    }
                })
            }
        })

        // 홈 달력 정보 조회 서버 연동
        RetrofitManager.scheduleService?.scheduleMain()?.enqueue(object:
            Callback<ScheduleMainResponseData> {
            override fun onResponse(
                call: Call<ScheduleMainResponseData>,
                response: Response<ScheduleMainResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("ScheduleMain", "success")
                    val data = response.body()
                    calendarList.addAll(
                        data!!.dateList.map {date ->
                            WeekCalendarData(date.date!!.year, date.date.month, date.date.dayOfWeek, date.date.day, date.totalScheduleStatus)
                        }
                    )
                    val selectedDate = data.selectedSubPage!!.selectedDate!! //선택된 날짜 (오늘 날짜)
                    binding.tvHomeDate.text = "${selectedDate.year}년 ${selectedDate.month}월"
                    binding.rvHomeWeek.adapter = calendarAdapter
                    binding.rvHomeWeek.layoutManager = GridLayoutManager(context, 7)
                    homeWorkspaceList.addAll(
                        data.selectedSubPage.workspaceList.map {workspace ->
                            HomeWorkspaceData(workspace.imageUrl, workspace.name,
                                workspace.selectedScheduleList.map {schedule ->
                                    HomeWorkspaceStaffData(schedule.scheduleStartTime, schedule.scheduleEndTime, schedule.logicalStartTime, schedule.logicalEndTime, schedule.worker!!.name, schedule.status)
                                }.toMutableList()
                            )
                        }
                    )
                    binding.rvHomeWorkspace.apply {
                        adapter = HomeWorkspaceAdapter().build(homeWorkspaceList)
                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                    }
                }else{
                    // 이곳은 에러 발생할 경우 실행됨 => 401일 경우 token 만료된 것!
                    Log.d("ScheduleMain", "fail")
                }
            }
            override fun onFailure(call: Call<ScheduleMainResponseData>, t: Throwable) {
                Log.d("ScheduleMain", "error")
            }
        })
    }
}