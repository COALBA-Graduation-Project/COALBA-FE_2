package com.example.coalba2.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.coalba2.R
import com.example.coalba2.api.retrofit.RetrofitManager
import com.example.coalba2.data.response.*
import com.example.coalba2.databinding.FragmentMessageDetailBinding
import com.example.coalba2.ui.adapter.MessageDetailAdapter
import com.example.coalba2.ui.adapter.StoreListForMessageAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageDetailFragment : Fragment() {
    private lateinit var binding: FragmentMessageDetailBinding
    lateinit var messageDetailAdapter: MessageDetailAdapter
    val datas = mutableListOf<MessageDetailData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageDetailBinding.inflate(inflater, container, false)
        messageDetailAdapter = MessageDetailAdapter(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val storeId = arguments?.getLong("STOREID")
        val sId = arguments?.getLong("SID")
        // 해당 워크스페이스의 해당 알바 쪽지함 내 메시지 리스트 조회 (최신순) 서버 연동
        RetrofitManager.messageService?.messages(storeId!!, sId!!)?.enqueue(object:
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
                        Toast.makeText(requireContext(), "메시지가 존재하지 않습니다", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        binding.rvMessagedetail.adapter = messageDetailAdapter
                        for(i in 0..num-1){
                            val itemdata = response.body()?.messageList?.get(i)
                            Log.d("responsevalue", "itemdata1_response 값 => "+ itemdata)
                            datas.add(MessageDetailData(state= itemdata!!.criteria, date = itemdata.createdDate, messageContent = itemdata.content))
                        }
                        messageDetailAdapter.datas = datas
                        messageDetailAdapter.notifyDataSetChanged()
                    }
                }else{
                    // 이곳은 에러 발생할 경우 실행됨
                    Log.d("Messages", "fail")
                }
            }
            override fun onFailure(call: Call<MessagesResponseData>, t: Throwable) {
                Log.d("Messages", "error")
            }
        })
    }
}