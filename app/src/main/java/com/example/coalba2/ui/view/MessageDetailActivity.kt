package com.example.coalba2.ui.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.coalba2.api.retrofit.RetrofitManager
import com.example.coalba2.data.response.MessageBoxData
import com.example.coalba2.data.response.MessageDetailData
import com.example.coalba2.data.response.MessageSendResponseData
import com.example.coalba2.data.response.MessagesResponseData
import com.example.coalba2.databinding.ActivityMessageDetailBinding
import com.example.coalba2.ui.adapter.MessageDetailAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageDetailActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMessageDetailBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    lateinit var messageDetailAdapter: MessageDetailAdapter
    var datas = mutableListOf<MessageDetailData>()
    var storeId: Long = 0
    var sId: Long = 0
    var staffName : String = ""

    // 쪽지보내기 버튼 클릭 시 MessageSendActivity 화면 시작하고 MessageSendActivity finish 후 결과값 받아와서 처리
    // 이전 onAcitivityResult 역할과 비슷, 해당 메소드 deprecated 되어서 대신 사용
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        when (it.resultCode) {
            Activity.RESULT_OK -> {
                val responseData = it.data!!.getParcelableExtra<MessageSendResponseData>("responseData")
                //responseData는 워크스페이스 등록 api 호출 후 응답 데이터
                Log.d("responseData =", responseData.toString())
                datas.clear()
                // todo : 수정 필요!
                datas.add(MessageDetailData(responseData!!.sendingOrReceiving, responseData.createDate, responseData.content))
                binding.rvMessagedetail.adapter?.notifyDataSetChanged()
                //adapter에게 데이터 변경되었다는 것 알림
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityMessageDetailBinding.inflate(layoutInflater)
        messageDetailAdapter = MessageDetailAdapter(this)
        setContentView(binding.root)

        // MessageBoxAdapter로부터 데이터 받아오기
        val data = intent.getParcelableExtra<MessageBoxData>("dataForMessageDetail")
        binding.tvMessagedetail.text = data!!.name
        staffName = data.name
        storeId = data.workspaceId
        sId = data.staffId

        // 해당 워크스페이스의 해당 알바 쪽지함 내 메시지 리스트 조회 (최신순) 서버 연동
        RetrofitManager.messageService?.messages(storeId, sId)?.enqueue(object:
            Callback<MessagesResponseData> {
            override fun onResponse(
                call: Call<MessagesResponseData>,
                response: Response<MessagesResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("Messages", "success")
                    val data = response.body()
                    Log.d("MessagesData", data.toString())

                    val num = data!!.messageList.count()
                    if(num == 0){
                        Toast.makeText(this@MessageDetailActivity, "메시지가 존재하지 않습니다", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        binding.rvMessagedetail.adapter = messageDetailAdapter
                        for(i in 0..num-1){
                            val itemdata = response.body()?.messageList?.get(i)
                            datas.add(MessageDetailData(state= itemdata!!.sendingOrReceiving, date = itemdata.createDate, messageContent = itemdata.content))
                        }
                        messageDetailAdapter.datas = datas
                        messageDetailAdapter.notifyDataSetChanged()
                    }
                }else{ // 이곳은 에러 발생할 경우 실행됨
                    Log.d("Messages", "fail")
                }
            }
            override fun onFailure(call: Call<MessagesResponseData>, t: Throwable) {
                Log.d("Messages", "error")
            }
        })
        binding.ivMessagedetailBack.setOnClickListener {
            finish()
        }
        binding.ivMessagedetailSend.setOnClickListener {
            val intent = Intent(this, MessageSendActivity::class.java)
            intent.putExtra("staffName", staffName)
            intent.putExtra("workID",storeId)
            intent.putExtra("staffID",sId)
            startForResult.launch(intent)
        }
    }
    // 액티비티가 파괴될 때..
    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 함
        mBinding = null
        super.onDestroy()
    }
}