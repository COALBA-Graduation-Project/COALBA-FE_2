package com.example.coalba2.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.example.coalba2.R
import com.example.coalba2.data.response.MessageBoxData
import com.example.coalba2.data.response.StoreListForMessageData
import com.example.coalba2.databinding.ActivityMessageDetailBinding
import com.example.coalba2.ui.fragment.MessageDetailFragment

class MessageDetailActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMessageDetailBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityMessageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<MessageBoxData>("dataForMessageDetail")
        binding.tvMessagedetail.text = data!!.name

        // 맨처음에 보여질 fragment 설정
        supportFragmentManager.beginTransaction().replace(R.id.fl_messagedatail, MessageDetailFragment()).commit()

        binding.ivMessagedetailBack.setOnClickListener {
            finish()
        }
    }
    // 액티비티가 파괴될 때..
    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 함
        mBinding = null
        super.onDestroy()
    }
}