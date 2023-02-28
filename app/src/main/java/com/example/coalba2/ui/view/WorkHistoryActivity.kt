package com.example.coalba2.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.coalba2.api.retrofit.RetrofitManager
import com.example.coalba2.data.response.WorkHistoryData
import com.example.coalba2.data.response.WorkHistoryDateResponseData
import com.example.coalba2.data.response.WorkHistoryListResponseData
import com.example.coalba2.data.response.WorkspaceBriefListLookResponseData
import com.example.coalba2.databinding.ActivityWorkHistoryBinding
import com.example.coalba2.databinding.ActivityWorkspaceHomeBinding
import com.example.coalba2.ui.adapter.WorkHistoryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkHistoryActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityWorkHistoryBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    lateinit var workhistoryAdapter: WorkHistoryAdapter
    val datas = mutableListOf<WorkHistoryData>()
    var workspaceArray: Array<String> = emptyArray()
    var yearmonthArray: Array<String> = emptyArray()
    var storeId: Long = 0
    var yearVar: Int = 0
    var monthVar: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityWorkHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        workhistoryAdapter = WorkHistoryAdapter(this@WorkHistoryActivity)
        binding.ivWorkhistoryBack.setOnClickListener {
            finish()
        }

        // 나의 워크스페이스 요약 리스트 조회 서버 연동
        RetrofitManager.workspaceService?.workspaceBriefListLook()?.enqueue(object:
            Callback<WorkspaceBriefListLookResponseData> {
            override fun onResponse(
                call: Call<WorkspaceBriefListLookResponseData>,
                response: Response<WorkspaceBriefListLookResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("WorkspaceBriefListLook", "success")
                    val data = response.body()
                    val num = data!!.workspaceList.count()

                    for(i in 0..num-1){
                        val itemdata = response.body()?.workspaceList?.get(i)
                        workspaceArray = workspaceArray.plus(itemdata!!.name)
                    }
                    // 워크스페이스 spinner
                    val workspaceAdapter = ArrayAdapter(this@WorkHistoryActivity, android.R.layout.simple_spinner_item, workspaceArray)
                    // Specify the layout to use when the list of choices appears
                    workspaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    // Apply the adapter to the spinner
                    binding.spinWorkhistory.adapter = workspaceAdapter
                    // workspace spinner click
                    binding.spinWorkhistory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            Toast.makeText(this@WorkHistoryActivity, workspaceArray[p2], Toast.LENGTH_SHORT).show()
                            storeId = data.workspaceList.get(p2).workspaceId
                            // 근무내역 및 알바비 관리 년월 리스트 조회 서버 연동
                            RetrofitManager.scheduleService?.workhistoryDate(storeId)?.enqueue(object:
                                Callback<WorkHistoryDateResponseData> {
                                override fun onResponse(
                                    call: Call<WorkHistoryDateResponseData>,
                                    response: Response<WorkHistoryDateResponseData>
                                ) {
                                    if(response.isSuccessful){
                                        Log.d("WorkhistoryDate", "success")
                                        val data2 = response.body()
                                        val num2 = data2!!.dateList.count()
                                        Log.d("num 값", "num 값 " + num2)
                                        // array 초기화
                                        yearmonthArray = emptyArray()

                                        for(i in 0..num2-1){
                                            val itemdata = response.body()?.dateList?.get(i)
                                            Log.d("responsevalue", "itemdata1_response 값 => "+ itemdata)
                                            yearmonthArray = yearmonthArray.plus(itemdata!!.year.toString() + "년 " + itemdata.month.toString()+"월")
                                        }
                                        // yearmonth spinner
                                        val yearmonthAdapter = ArrayAdapter(this@WorkHistoryActivity, android.R.layout.simple_spinner_item, yearmonthArray)
                                        yearmonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                        binding.spinWorkhistory2.adapter = yearmonthAdapter

                                        binding.spinWorkhistory2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                                yearVar = data2.dateList.get(p2).year
                                                monthVar = data2.dateList.get(p2).month
                                                // 해당 가게, 해당 년월 근무내역 및 알바비 관리 리스트 조회 서버 연동
                                                RetrofitManager.scheduleService?.workhistoryList(storeId, yearVar,monthVar)?.enqueue(object:
                                                    Callback<WorkHistoryListResponseData> {
                                                    override fun onResponse(
                                                        call: Call<WorkHistoryListResponseData>,
                                                        response: Response<WorkHistoryListResponseData>
                                                    ) {
                                                        if(response.isSuccessful){
                                                            Log.d("WorkhistoryList", "success")
                                                            val data3 = response.body()

                                                            datas.removeAll(datas)
                                                            workhistoryAdapter.notifyDataSetChanged()
                                                            val num3 = data3!!.workReportList.count()
                                                            Log.d("num3 값", "num3 값 " + num3)
                                                            if (num3 == 0){
                                                                Toast.makeText(this@WorkHistoryActivity, "근무내역이 없습니다", Toast.LENGTH_SHORT).show()
                                                            }
                                                            else{
                                                                binding.rvWorkhistory.adapter = workhistoryAdapter
                                                                for(i in 0..num3-1){
                                                                    val itemdata = response.body()?.workReportList?.get(i)
                                                                    Log.d("responsevalue", "itemdata1_response 값 => "+ itemdata)

                                                                    datas.add(WorkHistoryData(name = itemdata!!.worker!!.name, hour=itemdata!!.totalWorkTimeHour.toString(), minute = itemdata!!.totalWorkTimeMin.toString(), workPay = itemdata!!.totalWorkPay))
                                                                }
                                                                workhistoryAdapter.datas = datas
                                                                workhistoryAdapter.notifyDataSetChanged()
                                                            }
                                                        }else{
                                                            // 이곳은 에러 발생할 경우 실행됨
                                                            Log.d("WorkhistoryList", "fail")
                                                        }
                                                    }
                                                    override fun onFailure(call: Call<WorkHistoryListResponseData>, t: Throwable) {
                                                        Log.d("WorkhistoryList", "error")
                                                    }
                                                })
                                            }

                                            override fun onNothingSelected(p0: AdapterView<*>?) {
                                                Toast.makeText(this@WorkHistoryActivity,"년월을 선택해주세요!", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }else{
                                        // 이곳은 에러 발생할 경우 실행됨
                                        Log.d("WorkhistoryDate", "fail")
                                    }
                                }
                                override fun onFailure(call: Call<WorkHistoryDateResponseData>, t: Throwable) {
                                    Log.d("WorkhistoryDate", "error")
                                }
                            })
                        }
                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            Toast.makeText(this@WorkHistoryActivity,"워크스페이스를 선택해주세요!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    // 이곳은 에러 발생할 경우 실행됨
                    Log.d("WorkspaceBriefListLook", "fail")
                }
            }
            override fun onFailure(call: Call<WorkspaceBriefListLookResponseData>, t: Throwable) {
                Log.d("WorkspaceBriefListLook", "error")
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