package com.example.coalba2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coalba2.R
import com.example.coalba2.data.response.MessageDetailData
import com.example.coalba2.data.response.StoreListForMessageData
import com.example.coalba2.databinding.FragmentMessageDetailBinding
import com.example.coalba2.ui.adapter.MessageDetailAdapter
import com.example.coalba2.ui.adapter.StoreListForMessageAdapter

class MessageDetailFragment : Fragment() {
    private lateinit var binding: FragmentMessageDetailBinding
    lateinit var messageDetailAdapter: MessageDetailAdapter
    val datas = mutableListOf<MessageDetailData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMessageDetailBinding.inflate(inflater, container, false)
        initRecycler()
        return binding.root
    }
    private fun initRecycler(){
        messageDetailAdapter = MessageDetailAdapter(requireContext())
        binding.rvMessagedetail.adapter = messageDetailAdapter

        datas.apply{
            add(MessageDetailData(state= "받은 쪽지", date = "01/29", time = "13:16", messageContent = "사장님!! 오늘 스케줄 나왔나요??"))
            add(MessageDetailData(state= "보낸 쪽지", date = "12/01", time = "10:07", messageContent = "저도 잘 부탁드립니다! 열심히 해주세요.ㅎㅎ"))
            add(MessageDetailData(state= "받은 쪽지", date = "11/30", time = "19:52", messageContent = "사장님~ 앞으로 잘 부탁드립니다~!!"))

            messageDetailAdapter.datas = datas
            messageDetailAdapter.notifyDataSetChanged()
        }
    }
}