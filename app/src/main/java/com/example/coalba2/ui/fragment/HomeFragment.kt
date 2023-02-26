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
    private var homeWorkspaceList2 = ArrayList<HomeWorkspaceData>()

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
                            val num2 = data!!.workspaceList.count()
                            Log.d("num 값", "num 값 " + num2)
                            if (num2 == 0){
                                Toast.makeText(requireContext(), "등록한 워크스페이스가 존재하지 않습니다!", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                for(i in 0..num2-1){
                                    val itemdata2 = response.body()?.workspaceList?.get(i)
                                    Log.d("responsevalue", "workspace response 값 => "+ itemdata2)

                                    val num3 = itemdata2?.selectedScheduleList!!.count()
                                    Log.d("num 값", "num 값 " + num3)
                                    if (num3 == 0){
                                        homeWorkspaceList2.add(HomeWorkspaceData(itemdata2.imageUrl, itemdata2.name, mutableListOf(
                                            HomeWorkspaceStaffData("10:00","19:00","예진", "근무중")
                                        )))
                                    }
                                    else{
                                        for(j in 0..num3-1) {
                                            val itemdata3 = itemdata2.selectedScheduleList.get(j)
                                            Log.d("responsevalue", "schedule response 값 => " + itemdata3)

                                            homeWorkspaceList2.add(HomeWorkspaceData(itemdata2.imageUrl,itemdata2.name, mutableListOf(
                                                HomeWorkspaceStaffData(itemdata3.scheduleStartTime,itemdata3.scheduleEndTime,itemdata3.worker!!.name,itemdata3.status)
                                            )))
                                        }
                                    }
                                }
                                binding.rvHomeWorkspace.apply {
                                    adapter = HomeWorkspaceAdapter().build(homeWorkspaceList2)
                                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                                }
                            }
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
                    Log.d("Network_ScheduleMain", "success")
                    val data = response.body()
                    val num = data!!.dateList.count()
                    Log.d("num 값", "num 값 " + num)
                    for(i in 0..num-1){
                        val itemdata = response.body()?.dateList?.get(i)
                        Log.d("responsevalue", "itemdata1_response 값 => "+ itemdata)
                        if (i == 3){
                            binding.tvHomeDate.text = itemdata!!.date!!.year.toString() + "년" + itemdata!!.date!!.month.toString() + "월"
                        }
                        calendarList.add(WeekCalendarData(itemdata!!.date!!.year, itemdata!!.date!!.month, itemdata!!.date!!.dayOfWeek, itemdata!!.date!!.day, itemdata!!.totalScheduleStatus))
                    }
                    binding.rvHomeWeek.adapter = calendarAdapter
                    binding.rvHomeWeek.layoutManager = GridLayoutManager(context, 7)

                    val num2 = data.selectedSubPage!!.workspaceList.count()
                    Log.d("num 값", "num 값 " + num2)
                    if (num2 == 0){
                        Toast.makeText(requireContext(), "등록한 워크스페이스가 존재하지 않습니다!", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        for(i in 0..num2-1){
                            val itemdata2 = response.body()?.selectedSubPage?.workspaceList?.get(i)
                            Log.d("responsevalue", "workspace response 값 => "+ itemdata2)

                            val num3 = itemdata2?.selectedScheduleList!!.count()
                            Log.d("num 값", "num 값 " + num3)
                            if (num3 == 0){
                                // todo : 스케줄이 없을 때는 어떻게 할지 수정 필요
                                homeWorkspaceList.add(HomeWorkspaceData(itemdata2.imageUrl, itemdata2.name, mutableListOf(
                                    HomeWorkspaceStaffData("12:00","17:00","조예진", "근무중")
                                )))
                            }
                            else{
                                for(j in 0..num3-1) {
                                    val itemdata3 = itemdata2.selectedScheduleList.get(j)
                                    Log.d("responsevalue", "schedule response 값 => " + itemdata3)

                                    homeWorkspaceList.add(HomeWorkspaceData(itemdata2.imageUrl,itemdata2.name, mutableListOf(
                                        HomeWorkspaceStaffData(itemdata3.scheduleStartTime,itemdata3.scheduleEndTime,itemdata3.worker!!.name,itemdata3.status)
                                    )))
                                }
                            }
                        }
                        binding.rvHomeWorkspace.apply {
                            adapter = HomeWorkspaceAdapter().build(homeWorkspaceList)
                            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                        }
                    }
                }else{
                    // 이곳은 에러 발생할 경우 실행됨
                    Log.d("Network_ScheduleMain", "fail")
                }
            }
            override fun onFailure(call: Call<ScheduleMainResponseData>, t: Throwable) {
                Log.d("Network_ScheduleMain", "error")
            }
        })
    }
}