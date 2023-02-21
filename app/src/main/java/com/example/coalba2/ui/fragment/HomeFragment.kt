package com.example.coalba2.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coalba2.R
import com.example.coalba2.api.retrofit.RetrofitManager
import com.example.coalba2.data.response.PartTimeManageData
import com.example.coalba2.data.response.ProfileLookResponseData
import com.example.coalba2.data.response.ScheduleMainResponseData
import com.example.coalba2.data.response.WeekCalendarData
import com.example.coalba2.databinding.FragmentHomeBinding
import com.example.coalba2.ui.adapter.WeekCalendarAdapter
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter.ofPattern
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: FragmentHomeBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    lateinit var calendarAdapter: WeekCalendarAdapter
    private var calendarList = ArrayList<WeekCalendarData>()

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
        calendarAdapter = WeekCalendarAdapter(calendarList)

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
                        calendarList.add(WeekCalendarData(itemdata!!.date!!.dayOfWeek, itemdata!!.date!!.day.toString()))
                    }
                    binding.rvHomeWeek.adapter = calendarAdapter
                    binding.rvHomeWeek.layoutManager = GridLayoutManager(context, 7)

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