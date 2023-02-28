package com.example.coalba2.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coalba2.R
import com.example.coalba2.api.retrofit.RetrofitManager
import com.example.coalba2.data.response.StoreListData
import com.example.coalba2.data.response.WorkspaceListLookResponseData
import com.example.coalba2.databinding.FragmentMessageSendBinding
import com.example.coalba2.ui.adapter.StoreListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageSendFragment : Fragment() {
    private lateinit var binding: FragmentMessageSendBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageSendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // todo: 해당 워크스페이스의 해당 알바에게 쪽지 보내기
        binding.btnMessagesend.setOnClickListener {
            RetrofitManager.messageService?.messageSend(4, 3, "안녕하세요!")?.enqueue(object:
                Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(response.isSuccessful){
                        Log.d("MessageSend", "success")
                    }else{
                        // 이곳은 에러 발생할 경우 실행됨
                        Log.d("MessageSend", "fail")
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("MessageSend", "error")
                }
            })
        }
        super.onViewCreated(view, savedInstanceState)
    }
}