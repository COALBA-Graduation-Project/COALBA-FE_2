package com.example.coalba2.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.coalba2.R
import com.example.coalba2.api.retrofit.RetrofitManager
import com.example.coalba2.data.response.*
import com.example.coalba2.databinding.ActivityMessageBoxBinding
import com.example.coalba2.databinding.ActivityWorkspaceHomeBinding
import com.example.coalba2.ui.adapter.MessageBoxAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageBoxActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMessageBoxBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    lateinit var messageBoxAdapter: MessageBoxAdapter
    val datas = mutableListOf<MessageBoxData>()
    var storeId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityMessageBoxBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivMessageboxBack.setOnClickListener {
            finish()
        }
        messageBoxAdapter = MessageBoxAdapter(this)

        val data = intent.getParcelableExtra<StoreListForMessageData>("dataForMessage")
        binding.tvMessagebox.text = data!!.name
        storeId = data.workspaceId

        // 해당 워크스페이스 내 알바와의 쪽지함 리스트 조회 서버 연동
        RetrofitManager.messageService?.messageBox(storeId)?.enqueue(object:
            Callback<MessageBoxResponseData> {
            override fun onResponse(
                call: Call<MessageBoxResponseData>,
                response: Response<MessageBoxResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("MessageBox", "success")
                    val data = response.body()
                    Log.d("MessageBoxData", data.toString())
                    val num = data!!.messageBoxList.count()
                    if(num == 0){
                        Toast.makeText(this@MessageBoxActivity, "알바생이 존재하지 않습니다", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        binding.rvMessagebox.adapter = messageBoxAdapter

                        for(i in 0..num-1){
                            val itemdata = response.body()?.messageBoxList?.get(i)
                            Log.d("responsevalue", "itemdata1_response 값 => "+ itemdata)
                            if (itemdata?.latestDateTime == null){
                                datas.add(MessageBoxData(storeId, itemdata!!.staff!!.staffId, itemdata.staff!!.imageUrl, itemdata.staff!!.name, "", ""))
                            }
                            else{
                                datas.add(MessageBoxData(storeId, itemdata!!.staff!!.staffId, itemdata!!.staff!!.imageUrl, itemdata.staff!!.name, itemdata.latestMessage, itemdata.latestDateTime))
                            }
                        }
                        messageBoxAdapter.datas = datas
                        messageBoxAdapter.notifyDataSetChanged()
                    }
                }else{
                    // 이곳은 에러 발생할 경우 실행됨
                    Log.d("MessageBox", "fail")
                }
            }
            override fun onFailure(call: Call<MessageBoxResponseData>, t: Throwable) {
                Log.d("MessageBox", "error")
            }
        })
    }
}