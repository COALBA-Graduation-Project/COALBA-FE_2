package com.example.coalba2.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coalba2.R
import com.example.coalba2.api.retrofit.RetrofitManager
import com.example.coalba2.data.response.PartTimeManageData
import com.example.coalba2.data.response.StoreListData
import com.example.coalba2.data.response.WorkspaceListLookResponseData
import com.example.coalba2.data.response.WorkspaceStaffListLookResponseData
import com.example.coalba2.databinding.ActivityPartTimeJobManageBinding
import com.example.coalba2.ui.adapter.PartTimeJobManageAdapter
import com.example.coalba2.ui.adapter.StoreListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PartTimeJobManageActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityPartTimeJobManageBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    var storeId: Long = 0

    lateinit var partTimeJobManageAdapter: PartTimeJobManageAdapter
    val datas = mutableListOf<PartTimeManageData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityPartTimeJobManageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivParttimeManagePlus.setOnClickListener {
            val intent = Intent(this, PartTimeJobAddActivity::class.java)
            intent.putExtra("storeId", storeId)
            startActivity(intent)
        }
        binding.ivParttimeManageBack.setOnClickListener {
            finish()
        }
        // 해당 워크스페이스 내 알바 정보 리스트 조회 서버 연동
        storeId = intent.getLongExtra("storeId", 0)
        RetrofitManager.workspaceService?.workspaceStaffListLook(storeId)?.enqueue(object:
            Callback<WorkspaceStaffListLookResponseData> {
            override fun onResponse(
                call: Call<WorkspaceStaffListLookResponseData>,
                response: Response<WorkspaceStaffListLookResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("Network_WorkspaceStaffListLook", "success")
                    Log.d("Network_WorkspaceStaffListLook_data", storeId.toString())
                    val data = response.body()
                    val num = data!!.staffInfoList.count()
                    Log.d("num 값", "num 값 " + num)
                    partTimeJobManageAdapter = PartTimeJobManageAdapter(this@PartTimeJobManageActivity)
                    binding.rvParttimeManage.adapter = partTimeJobManageAdapter

                    for(i in 0..num-1){
                        val itemdata = response.body()?.staffInfoList?.get(i)
                        Log.d("responsevalue", "itemdata1_response 값 => "+ itemdata)
                        datas.add(PartTimeManageData(itemdata!!.imageUrl, itemdata!!.name, itemdata!!.phoneNumber, itemdata!!.birthDate))
                    }
                    partTimeJobManageAdapter.datas = datas
                    partTimeJobManageAdapter.notifyDataSetChanged()
                }else{
                    // 이곳은 에러 발생할 경우 실행됨
                    Log.d("Network_WorkspaceStaffListLook", "fail")
                }
            }
            override fun onFailure(call: Call<WorkspaceStaffListLookResponseData>, t: Throwable) {
                Log.d("Network_WorkspaceStaffListLook", "error")
            }
        })
    }
}