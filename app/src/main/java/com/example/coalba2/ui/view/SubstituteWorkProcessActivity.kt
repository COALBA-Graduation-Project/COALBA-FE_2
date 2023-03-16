package com.example.coalba2.ui.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.coalba2.R
import com.example.coalba2.api.retrofit.RetrofitManager
import com.example.coalba2.databinding.ActivitySubstituteWorkProcessBinding
import com.example.coalba2.data.SubstituteReqState.*
import com.example.coalba2.data.response.SubstituteReqDetailResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubstituteWorkProcessActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivitySubstituteWorkProcessBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivitySubstituteWorkProcessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val substituteReqId = intent.extras!!.getLong("substituteReqId")
        // 대타근무 요청 관리 상세 조회 서버 연동
        RetrofitManager.substituteReqService?.substituteReqDetail(substituteReqId)?.enqueue(object:
            Callback<SubstituteReqDetailResponseData> {
            override fun onResponse(
                call: Call<SubstituteReqDetailResponseData>,
                response: Response<SubstituteReqDetailResponseData>
            ) {
                if (response.isSuccessful) {
                    Log.d("SubstituteReqDetail", "success")
                    val data = response.body()!!

                    //데이터 바인딩
                    Glide.with(this@SubstituteWorkProcessActivity).load(data.senderImageUrl).into(binding.ivSubstituteworkProcessProfile1)
                    Glide.with(this@SubstituteWorkProcessActivity).load(data.receiverImageUrl).into(binding.ivSubstituteworkProcessProfile2)
                    binding.tvSubstituteworkProcessName1.text = data.senderName
                    binding.tvSubstituteworkProcessName2.text = data.receiverName
                    binding.tvSubstituteworkProcessStore.text = data.workspaceName
                    binding.tvSubstituteworkProcessDate.text = data.startDateTime.split(" ")[0]
                    binding.tvSubstituteworkProcessStarttime.text = data.startDateTime.split(" ")[1]
                    binding.tvSubstituteworkProcessEndtime.text = data.endDateTime.split(" ")[1]
                    binding.etSubstituteworkMessage.setText(data.reqMessage)

                    when (data.status) {
                        APPROVAL.value -> {
                            binding.tvSubstituteworkProcessState.visibility = View.VISIBLE
                            binding.tvSubstituteworkProcessState.text = APPROVAL.text
                            binding.btnRefuse.visibility = View.GONE
                            binding.btnSubmit.visibility = View.GONE
                        }
                        DISAPPROVAL.value -> {
                            binding.tvSubstituteworkProcessState.visibility = View.VISIBLE
                            binding.tvSubstituteworkProcessState.text = DISAPPROVAL.text
                            binding.tvSubstituteworkProcessState.setTextColor(ContextCompat.getColor(binding.root.context, R.color.refuse))
                            binding.btnRefuse.visibility = View.GONE
                            binding.btnSubmit.visibility = View.GONE
                        }
                        else -> {
                            binding.tvSubstituteworkProcessState.visibility = View.GONE
                            binding.btnRefuse.visibility = View.VISIBLE
                            binding.btnSubmit.visibility = View.VISIBLE
                        }
                    }
                } else {
                    // 이곳은 에러 발생할 경우 실행됨
                    Log.d("SubstituteReqDetail", "fail")
                }
            }
            override fun onFailure(call: Call<SubstituteReqDetailResponseData>, t: Throwable) {
                Log.d("SubstituteReqDetail", "error")
            }
        })

        // 대타근무 요청 수락 서버 연동
        binding.btnSubmit.setOnClickListener {
            RetrofitManager.substituteReqService?.substituteReqAccept(substituteReqId)?.enqueue(object:
                Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d("SubstituteReqAccept", "success")
//아래 intent의 extra 값을 이전 activity에서 받아서 사용해야 하는데 지금은 불가
                        setResult(Activity.RESULT_OK, Intent().putExtra("substituteReqId", substituteReqId).putExtra("state", APPROVAL.value))
                        finish()
                    } else {
                        // 이곳은 에러 발생할 경우 실행됨
                        Log.d("SubstituteReqAccept", "fail")
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("SubstituteReqAccept", "error")
                }
            })
        }

        // 대타근무 요청 거절 서버 연동
        binding.btnRefuse.setOnClickListener {
            RetrofitManager.substituteReqService?.substituteReqReject(substituteReqId)?.enqueue(object:
                Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d("SubstituteReqReject", "success")
//아래 intent의 extra 값을 이전 activity에서 받아서 사용해야 하는데 지금은 불가
                        setResult(Activity.RESULT_OK, Intent().putExtra("substituteReqId", substituteReqId).putExtra("state", DISAPPROVAL.value))
                        finish()
                    } else {
                        // 이곳은 에러 발생할 경우 실행됨
                        Log.d("SubstituteReqReject", "fail")
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("Network_SubstituteReqReject", "error")
                }
            })
        }
        binding.ivSubstituteworkProcessBack.setOnClickListener {
            finish()
        }
    }
}