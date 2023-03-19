package com.example.coalba2.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.example.coalba2.R
import com.example.coalba2.api.retrofit.RetrofitManager
import com.example.coalba2.data.request.MessageSendData
import com.example.coalba2.data.response.MessageSendResponseData
import com.example.coalba2.data.response.MessagesResponseData
import com.example.coalba2.databinding.ActivityMessageDetailBinding
import com.example.coalba2.databinding.ActivityMessageSendBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageSendActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMessageSendBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityMessageSendBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sName = intent.getStringExtra("staffName")
        val wId = intent.getLongExtra("workID", 0)
        val sId = intent.getLongExtra("staffID", 0)
        binding.tvMessagesend.text = sName

        binding.etMessagesend.addTextChangedListener(object : TextWatcher {
            var maxText = ""
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.etMessagesend.length() > 150){
                    Toast.makeText(this@MessageSendActivity, "최대 150자까지 입력 가능합니다.", Toast.LENGTH_SHORT).show()
                    binding.etMessagesend.setText(maxText)
                    binding.etMessagesend.setSelection(binding.etMessagesend.length())
                    binding.tvMessagesendCount.setText("${binding.etMessagesend.length()}")
                }
                else{
                    binding.tvMessagesendCount.setText("${binding.etMessagesend.length()}")
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        // 해당 워크스페이스의 해당 알바에게 쪽지 보내기
        binding.btnMessagesend.setOnClickListener {
            val messageData = MessageSendData(binding.etMessagesend.text.toString())
            Log.d("Data", messageData.content)
            RetrofitManager.messageService?.messageSend(wId, sId, messageData)?.enqueue(object:
                Callback<MessagesResponseData> {
                override fun onResponse(call: Call<MessagesResponseData>, response: Response<MessagesResponseData>) {
                    if(response.isSuccessful){
                        Log.d("MessageSend", "success")
                        val data = response.body()
                        Log.d("MessagesData", data.toString())
                        finish()
                    }else{ // 이곳은 에러 발생할 경우 실행됨
                        Log.d("MessageSend", "fail")
                    }
                }
                override fun onFailure(call: Call<MessagesResponseData>, t: Throwable) {
                    Log.d("MessageSend", "error")
                }
            })
        }
        binding.ivMessagesendBack.setOnClickListener {
            finish()
        }
    }
}