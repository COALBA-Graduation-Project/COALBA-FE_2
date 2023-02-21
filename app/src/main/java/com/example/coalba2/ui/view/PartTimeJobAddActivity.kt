package com.example.coalba2.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coalba2.api.retrofit.RetrofitManager
import com.example.coalba2.data.response.PartTimeManageData
import com.example.coalba2.data.response.WorkspaceStaffListLookResponseData
import com.example.coalba2.databinding.ActivityPartTimeJobAddBinding
import com.example.coalba2.ui.adapter.PartTimeJobManageAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PartTimeJobAddActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityPartTimeJobAddBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityPartTimeJobAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("Network_WorkspaceStaffAdd_data", intent.getLongExtra("storeId", 0).toString())
        binding.btnParttimeAdd.setOnClickListener {
            // 해당 워크스페이스에 알바 추가 서버 연동
            RetrofitManager.workspaceService?.workspaceStaffAdd(intent.getLongExtra("storeId", 0), binding.etParttimeAddEmail.text.toString())?.enqueue(object:
                Callback<Void> {
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    if(response.isSuccessful){
                        Log.d("Network_WorkspaceStaffAdd", "success")
                    }else{
                        // 이곳은 에러 발생할 경우 실행됨
                        val data1 = response.code()
                        Log.d("status code", data1.toString())
                        val data2 = response.headers()
                        Log.d("header", data2.toString())
                        Log.d("server err", response.errorBody()?.string().toString())
                        Log.d("Network_WorkspaceStaffAdd", "fail")
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("Network_WorkspaceStaffAdd", "error")
                }
            })
        }

        binding.ivParttimeAddBack.setOnClickListener {
            finish()
        }
    }
}