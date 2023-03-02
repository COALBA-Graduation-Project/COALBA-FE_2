package com.example.coalba2.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coalba2.R
import com.example.coalba2.api.retrofit.RetrofitManager
import com.example.coalba2.data.response.PartTimeManageData
import com.example.coalba2.data.response.SubstituteReqListLookResponseData
import com.example.coalba2.data.response.SubstituteWorkDateData
import com.example.coalba2.data.response.SubstituteWorkScheduleData
import com.example.coalba2.databinding.ActivitySubstituteWorkManageBinding
import com.example.coalba2.ui.adapter.SubstituteWorkDateAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubstituteWorkManageActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivitySubstituteWorkManageBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    private val itemList = mutableListOf<SubstituteWorkDateData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivitySubstituteWorkManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 대타근무 요청 관리 리스트 조회 서버 연동
        RetrofitManager.substituteReqService?.substituteReqListLook()?.enqueue(object:
            Callback<SubstituteReqListLookResponseData> {
            override fun onResponse(
                call: Call<SubstituteReqListLookResponseData>,
                response: Response<SubstituteReqListLookResponseData>
            ) {
                if (response.isSuccessful) {
                    Log.d("SubstituteReqListLook", "success")
                    val datas = response.body()!!.totalSubstituteReqList

                    for (data in datas) {
                        itemList.add(SubstituteWorkDateData(
                            "${data.year}년 ${data.month}월",
                            data.substituteReqList.map { sr ->
                                SubstituteWorkScheduleData(sr.substituteReqId, sr.senderImageUrl, sr.senderName, sr.receiverImageUrl, sr.receiverName, sr.status, sr.workspaceName,
                                    sr.startDateTime.split(" ")[0], sr.startDateTime.split(" ")[1], sr.endDateTime.split(" ")[1])
                            }.toMutableList()
                        ))
                    }
                    initRecycler()
                } else {
                    // 이곳은 에러 발생할 경우 실행됨
                    Log.d("SubstituteReqListLook", "fail")
                }
            }

            override fun onFailure(call: Call<SubstituteReqListLookResponseData>, t: Throwable) {
                Log.d("SubstituteReqListLook", "error")
            }
        })
    }
    private fun initRecycler(){
        binding.rvSubstituteworkDate.adapter = SubstituteWorkDateAdapter().build(itemList)
        binding.rvSubstituteworkDate.layoutManager = LinearLayoutManager(this)
    }
}