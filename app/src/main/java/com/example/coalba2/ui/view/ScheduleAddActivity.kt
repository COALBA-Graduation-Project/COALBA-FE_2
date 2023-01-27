package com.example.coalba2.ui.view

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.coalba2.R
import com.example.coalba2.databinding.ActivityScheduleAddBinding
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
            }, getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
            datepicker.show()
        }
    }
}