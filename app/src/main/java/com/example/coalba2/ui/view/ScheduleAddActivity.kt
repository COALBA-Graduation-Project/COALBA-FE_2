package com.example.coalba2.ui.view

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.airbnb.lottie.parser.IntegerParser
import com.example.coalba2.R
import com.example.coalba2.api.retrofit.RetrofitManager
import com.example.coalba2.data.request.ScheduleAddData
import com.example.coalba2.data.request.ScheduleDateData
import com.example.coalba2.data.response.ScheduleAddPersonData
import com.example.coalba2.data.response.ScheduleCalendarResponseData
import com.example.coalba2.data.response.ScheduleData
import com.example.coalba2.data.response.SchedulePossibleResponseData
import com.example.coalba2.databinding.ActivityScheduleAddBinding
import com.example.coalba2.databinding.DialogSchedulePersonBinding
import com.example.coalba2.databinding.DialogScheduleTimeBottomSheetBinding
import com.example.coalba2.ui.adapter.ScheduleAdapter
import com.example.coalba2.ui.adapter.ScheduleAddAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ScheduleAddActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityScheduleAddBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    var formatDate = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    var storeId: Long = 0
    var sId: Long = 0

    // 알바생 추가 recyclerview
    lateinit var scheduleAddAdapter: ScheduleAddAdapter
    val datas = mutableListOf<ScheduleAddPersonData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityScheduleAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storeId = intent.getLongExtra("storeId", 0)
        // 스케줄 일정 datepicker
        // todo: 추후에 bottom sheet로 보여지도록 수정하기
        binding.ivScheduleaddPutdate.setOnClickListener {
            val getDate: Calendar = Calendar.getInstance()
            val datepicker = DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->

                val selectDate = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, i)
                selectDate.set(Calendar.MONTH, i2)
                selectDate.set(Calendar.DAY_OF_MONTH, i3)
                val date = formatDate.format(selectDate.time)
                binding.tvScheduleaddPutdate.text = date
                binding.tvScheduleaddPutdate.setTextColor(getColor(R.color.black))
            }, getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
            datepicker.show()
        }
        binding.ivScheduleaddBack.setOnClickListener {
            finish()
        }
        timePickerClickListener()
        timePickerClickListener2()

        // 스케줄 추가 parttime person pick
        val putPersonDialog = BottomSheetDialog(this, R.style.BottomSheetTheme)
        scheduleAddAdapter = ScheduleAddAdapter(this,object :ScheduleAddAdapter.PossiblePersonListener{
            override fun click(staffId: Long, name: String) {
                binding.tvScheduleaddPutperson.apply {
                    text = name
                    setTextColor(getColor(R.color.black))
                }
                sId = staffId
                putPersonDialog.dismiss()
            }
        })
        binding.clPerson.setOnClickListener {
            val sheetView = DialogSchedulePersonBinding.inflate(layoutInflater)

            // 해당 날짜에 근무 가능한 해당 워크스페이스 내 알바 리스트 조회 (for 스케줄 추가) 서버 연동 완료
            RetrofitManager.scheduleService?.schedulePossible(storeId, binding.tvScheduleaddPutdate.text.toString()+" "+ binding.tvScheduleaddStarthour.text.toString()+":"+binding.tvScheduleaddStartminute.text.toString(), binding.tvScheduleaddPutdate.text.toString()+" "+ binding.tvScheduleaddEndhour.text.toString()+":"+binding.tvScheduleaddEndminute.text.toString())?.enqueue(object:
                Callback<SchedulePossibleResponseData> {
                override fun onResponse(
                    call: Call<SchedulePossibleResponseData>,
                    response: Response<SchedulePossibleResponseData>
                ) {
                    if(response.isSuccessful){
                        Log.d("SchedulePossible", "success")
                        val data = response.body()
                        Log.d("SchedulePossibleData", data.toString())
                        val num = data!!.staffList.count()
                        Log.d("num 값", "num 값 " + num)
                        if(num == 0){
                            Toast.makeText(this@ScheduleAddActivity, "가능한 알바생이 없습니다!", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            for(i in 0..num-1){
                                val itemdata = response.body()?.staffList?.get(i)
                                Log.d("responsevalue", "possible_response 값 => "+ itemdata)
                                datas.add(ScheduleAddPersonData(itemdata!!.imageUrl, itemdata.name, itemdata.staffId))
                            }
                            scheduleAddAdapter.datas = datas
                            scheduleAddAdapter.notifyDataSetChanged()
                        }
                    }else{
                        // 이곳은 에러 발생할 경우 실행됨
                        Log.d("SchedulePossible", "fail")
                    }
                }
                override fun onFailure(call: Call<SchedulePossibleResponseData>, t: Throwable) {
                    Log.d("SchedulePossible", "error")
                }
            })
            sheetView.rvSchedulePerson.adapter = scheduleAddAdapter

            putPersonDialog.setContentView(sheetView.root)
            putPersonDialog.show()
        }
        // todo: 모든 값이 입력되었을때 버튼 색 변경 및 활성화

        // 스케줄 생성 서버 연동
        binding.btnScheduleaddFinish.setOnClickListener {
            val requestScheduleAddData = ScheduleAddData(
                workspaceId = storeId,
                staffId = sId,
                scheduleDateTime = ScheduleDateData(binding.tvScheduleaddPutdate.text.toString()+" "+ binding.tvScheduleaddStarthour.text.toString()+":"+binding.tvScheduleaddStartminute.text.toString(),binding.tvScheduleaddPutdate.text.toString()+" "+ binding.tvScheduleaddEndhour.text.toString()+":"+binding.tvScheduleaddEndminute.text.toString()),
                hourlyWage = Integer.parseInt(binding.etScheduleaddWage.text.toString())
            )
            RetrofitManager.scheduleService?.scheduleAdd(requestScheduleAddData)?.enqueue(object:
                Callback<Void> {
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    if(response.isSuccessful){
                        Log.d("ScheduleAdd", "success")
                        finish()
                    }else{
                        // 이곳은 에러 발생할 경우 실행됨
                        Log.d("ScheduleAdd", "fail")
                        finish()
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("ScheduleAdd", "error")
                }
            })
        }
    }

    // 스케줄 추가 start timepicker
    private fun timePickerClickListener() {
        binding.clTime1.setOnClickListener {
            val starttimeDialog = BottomSheetDialog(this, R.style.BottomSheetTheme)
            val sheetView = DialogScheduleTimeBottomSheetBinding.inflate(layoutInflater)
            sheetView.npHour.apply {
                minValue = 0
                maxValue = 23
                displayedValues = arrayOf(
                    "00",
                    "01",
                    "02",
                    "03",
                    "04",
                    "05",
                    "06",
                    "07",
                    "08",
                    "09",
                    "10",
                    "11",
                    "12",
                    "13",
                    "14",
                    "15",
                    "16",
                    "17",
                    "18",
                    "19",
                    "20",
                    "21",
                    "22",
                    "23"
                )
                wrapSelectorWheel = false
            }
            sheetView.npMinute.apply { // 이 값을 가져올 때는 무조건 *10 으로 반환을 해주면 됩니다. ex. 10을 선택했다면 1 * 10 = 10 으로 되게끔
                // 이게 10분 단위로 끊다보니까 넘버 피킹에서는 그냥 0~5를 선택하는 것이고 우리가 값을 가져올떈 10을 곱해주면 됩니다.
                minValue = 0
                maxValue = 5
                displayedValues = arrayOf("00", "10", "20", "30", "40", "50")
                wrapSelectorWheel = false
            }

            starttimeDialog.setContentView(sheetView.root)
            starttimeDialog.show()

            sheetView.btnok.setOnClickListener {
                // isStartTimeChk = true
                // chkBtnActivate()

                when (sheetView.npHour.value) {
                    0 -> {
                        binding.tvScheduleaddStarthour.apply {
                            text = "00"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    1 -> {
                        binding.tvScheduleaddStarthour.apply {
                            text = "01"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    2 -> {
                        binding.tvScheduleaddStarthour.apply {
                            text = "02"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    3 -> {
                        binding.tvScheduleaddStarthour.apply {
                            text = "03"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    4 -> {
                        binding.tvScheduleaddStarthour.apply {
                            text = "04"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    5 -> {
                        binding.tvScheduleaddStarthour.apply {
                            text = "05"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    6 -> {
                        binding.tvScheduleaddStarthour.apply {
                            text = "06"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    7 -> {
                        binding.tvScheduleaddStarthour.apply {
                            text = "07"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    8 -> {
                        binding.tvScheduleaddStarthour.apply {
                            text = "08"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    9 -> {
                        binding.tvScheduleaddStarthour.apply {
                            text = "09"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    else -> {
                        binding.tvScheduleaddStarthour.apply {
                            text = sheetView.npHour.value.toString()
                            setTextColor(getColor(R.color.black))
                        }
                    }
                }

                if (sheetView.npMinute.value == 0) {
                    binding.tvScheduleaddStartminute.apply {
                        text = "00"
                        setTextColor(getColor(R.color.black))
                    }
                } else {
                    binding.tvScheduleaddStartminute.apply {
                        text = (sheetView.npMinute.value * 10).toString()
                        setTextColor(getColor(R.color.black))
                    }
                }
                binding.tvScheduleaddStartcolon.setTextColor(getColor(R.color.black))
                starttimeDialog.dismiss()
            }
            sheetView.btncancel.setOnClickListener {
                starttimeDialog.dismiss()
            }
        }
    }
    // 스케줄 추가 end timepicker
    private fun timePickerClickListener2() {
        binding.clTime2.setOnClickListener {
            val endtimeDialog = BottomSheetDialog(this, R.style.BottomSheetTheme)
            val sheetView = DialogScheduleTimeBottomSheetBinding.inflate(layoutInflater)
            sheetView.npHour.apply {
                minValue = 0
                maxValue = 23
                displayedValues = arrayOf(
                    "00",
                    "01",
                    "02",
                    "03",
                    "04",
                    "05",
                    "06",
                    "07",
                    "08",
                    "09",
                    "10",
                    "11",
                    "12",
                    "13",
                    "14",
                    "15",
                    "16",
                    "17",
                    "18",
                    "19",
                    "20",
                    "21",
                    "22",
                    "23"
                )
                wrapSelectorWheel = false
            }
            sheetView.npMinute.apply { // 이 값을 가져올 때는 무조건 *10 으로 반환을 해주면 됩니다. ex. 10을 선택했다면 1 * 10 = 10 으로 되게끔
                // 이게 10분 단위로 끊다보니까 넘버 피킹에서는 그냥 0~5를 선택하는 것이고 우리가 값을 가져올떈 10을 곱해주면 됩니다.
                minValue = 0
                maxValue = 5
                displayedValues = arrayOf("00", "10", "20", "30", "40", "50")
                wrapSelectorWheel = false
            }

            endtimeDialog.setContentView(sheetView.root)
            endtimeDialog.show()

            sheetView.btnok.setOnClickListener {
                // isStartTimeChk = true
                // chkBtnActivate()

                when (sheetView.npHour.value) {
                    0 -> {
                        binding.tvScheduleaddEndhour.apply {
                            text = "00"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    1 -> {
                        binding.tvScheduleaddEndhour.apply {
                            text = "01"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    2 -> {
                        binding.tvScheduleaddEndhour.apply {
                            text = "02"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    3 -> {
                        binding.tvScheduleaddEndhour.apply {
                            text = "03"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    4 -> {
                        binding.tvScheduleaddEndhour.apply {
                            text = "04"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    5 -> {
                        binding.tvScheduleaddEndhour.apply {
                            text = "05"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    6 -> {
                        binding.tvScheduleaddEndhour.apply {
                            text = "06"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    7 -> {
                        binding.tvScheduleaddEndhour.apply {
                            text = "07"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    8 -> {
                        binding.tvScheduleaddEndhour.apply {
                            text = "08"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    9 -> {
                        binding.tvScheduleaddEndhour.apply {
                            text = "09"
                            setTextColor(getColor(R.color.black))
                        }
                    }
                    else -> {
                        binding.tvScheduleaddEndhour.apply {
                            text = sheetView.npHour.value.toString()
                            setTextColor(getColor(R.color.black))
                        }
                    }
                }

                if (sheetView.npMinute.value == 0) {
                    binding.tvScheduleaddEndminute.apply {
                        text = "00"
                        setTextColor(getColor(R.color.black))
                    }
                } else {
                    binding.tvScheduleaddEndminute.apply {
                        text = (sheetView.npMinute.value * 10).toString()
                        setTextColor(getColor(R.color.black))
                    }
                }
                binding.tvScheduleaddEndcolon.setTextColor(getColor(R.color.black))
                endtimeDialog.dismiss()
            }
            sheetView.btncancel.setOnClickListener {
                endtimeDialog.dismiss()
            }
        }
    }
}