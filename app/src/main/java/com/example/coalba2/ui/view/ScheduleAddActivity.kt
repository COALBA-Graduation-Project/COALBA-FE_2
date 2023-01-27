package com.example.coalba2.ui.view

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.coalba2.R
import com.example.coalba2.databinding.ActivityScheduleAddBinding
import com.example.coalba2.databinding.DialogScheduleTimeBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*

class ScheduleAddActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityScheduleAddBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    var formatDate = SimpleDateFormat("yyyy.MM.dd", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityScheduleAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        timePickerClickListener()
        timePickerClickListener2()
    }

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