package com.example.coalba2.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.coalba2.R
import com.example.coalba2.api.retrofit.RetrofitManager
import com.example.coalba2.data.request.MessageSendData
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
        super.onViewCreated(view, savedInstanceState)
        binding.etMessagesend.addTextChangedListener(object : TextWatcher{
            var maxText = ""
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.etMessagesend.length() > 150){
                    Toast.makeText(requireContext(), "최대 150자까지 입력 가능합니다.", Toast.LENGTH_SHORT).show()
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
        val storeId = arguments?.getLong("STOREID")
        val sId = arguments?.getLong("SID")
        // 해당 워크스페이스의 해당 알바에게 쪽지 보내기
        binding.btnMessagesend.setOnClickListener {
            val messageData = MessageSendData(binding.etMessagesend.text.toString())
            Log.d("Data", messageData.content)
            RetrofitManager.messageService?.messageSend(storeId!!, sId!!, messageData)?.enqueue(object:
                Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(response.isSuccessful){
                        Log.d("MessageSend", "success")
                        // 현재 프래그먼트를 종료하고 이전 화면으로 이동
                        requireActivity().supportFragmentManager.popBackStack()
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
    }
}