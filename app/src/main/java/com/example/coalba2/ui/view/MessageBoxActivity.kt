package com.example.coalba2.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coalba2.R
import com.example.coalba2.data.response.MessageBoxData
import com.example.coalba2.data.response.StoreListData
import com.example.coalba2.data.response.StoreListForMessageData
import com.example.coalba2.databinding.ActivityMessageBoxBinding
import com.example.coalba2.databinding.ActivityWorkspaceHomeBinding
import com.example.coalba2.ui.adapter.MessageBoxAdapter

class MessageBoxActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMessageBoxBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    lateinit var messageBoxAdapter: MessageBoxAdapter
    val datas = mutableListOf<MessageBoxData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityMessageBoxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<StoreListForMessageData>("dataForMessage")
        binding.tvMessagebox.text = data!!.name
        initRecycler()
    }
    private fun initRecycler(){
        messageBoxAdapter = MessageBoxAdapter(this)
        binding.rvMessagebox.adapter = messageBoxAdapter

        datas.apply {
            add(
                MessageBoxData(
                    img = R.drawable.ic_emptyimg,
                    name = "김다은",
                    latestMessage = "사장님!! 오늘 스케줄 나왔나요??",
                    latestDateTime = "2023/01/29"
                )
            )
            add(
                MessageBoxData(
                    img = R.drawable.ic_emptyimg,
                    name = "신지연",
                    latestMessage = "사장님~ 혹시 저 스케줄 변경 가능...",
                    latestDateTime = "2023/01/12"
                )
            )
            add(
                MessageBoxData(
                    img = R.drawable.ic_emptyimg,
                    name = "조예진",
                    latestMessage = "안녕하세요~!! 사장님 잘부탁드립니다...",
                    latestDateTime = "2022/12/31"
                )
            )
        }
            messageBoxAdapter.datas = datas
            messageBoxAdapter.notifyDataSetChanged()
    }
}