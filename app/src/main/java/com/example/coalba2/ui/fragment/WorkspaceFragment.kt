package com.example.coalba2.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.coalba2.R
import com.example.coalba2.api.retrofit.RetrofitManager
import com.example.coalba2.data.response.ProfileLookResponseData
import com.example.coalba2.data.response.StoreListData
import com.example.coalba2.data.response.WorkspaceListLookResponseData
import com.example.coalba2.databinding.FragmentWorkspaceBinding
import com.example.coalba2.ui.adapter.StoreListAdapter
import com.example.coalba2.ui.view.StoreEditActivity
import com.example.coalba2.ui.view.StoreRegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkspaceFragment : Fragment() {

    private lateinit var binding: FragmentWorkspaceBinding
    lateinit var storeListAdapter: StoreListAdapter
    val datas = mutableListOf<StoreListData>()

    // 워크스페이스 추가 버튼 클릭 시 StoreRegisterActivity 화면 시작하고 StoreRegisterActivity finish 후 결과값 받아와서 처리
    // 이전 onAcitivityResult 역할과 비슷, 해당 메소드 deprecated 되어서 대신 사용
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        when (it.resultCode) {
            Activity.RESULT_OK -> {
                val responseData = it.data!!.getParcelableExtra<WorkspaceListLookResponseData>("responseData")
                //responseData는 워크스페이스 등록 api 호출 후 응답 데이터
                println("responseData = $responseData")
                datas.clear() // adapter에서 사용하는 datas 일단 모두 지우고 위에서 받아온 responseData 전달
                responseData!!.workspaceList.forEach {
                        workspace -> datas.add(StoreListData(workspace.workspaceId, workspace.imageUrl, workspace.name))
                }
                binding.rvStorelist.adapter?.notifyItemRangeChanged(0, responseData.workspaceList.count())
                //adapter에게 데이터 변경되었다는 것 알림
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkspaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ivWorkspacePlus.setOnClickListener {
            val intent = Intent(requireContext(), StoreRegisterActivity::class.java)
            startForResult.launch(intent)  // intent 전달하여 activity launch
        }
        // 나의 워크스페이스 리스트 조회 서버 연동
        RetrofitManager.workspaceService?.workspaceListLook()?.enqueue(object:
            Callback<WorkspaceListLookResponseData> {
            override fun onResponse(
                call: Call<WorkspaceListLookResponseData>,
                response: Response<WorkspaceListLookResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("Network_WorkspaceListLook", "success")
                    val data = response.body()
                    val num = data!!.workspaceList.count()
                    storeListAdapter = StoreListAdapter(requireContext())
                    binding.rvStorelist.adapter = storeListAdapter

                    for(i in 0..num-1){
                        val itemdata = response.body()?.workspaceList?.get(i)
                        datas.add(StoreListData(itemdata!!.workspaceId,itemdata!!.imageUrl, itemdata!!.name))
                    }
                    storeListAdapter.datas=datas
                    storeListAdapter.notifyDataSetChanged()

                }else{
                    // 이곳은 에러 발생할 경우 실행됨
                    Log.d("Network_WorkspaceListLook", "fail")
                }
            }
            override fun onFailure(call: Call<WorkspaceListLookResponseData>, t: Throwable) {
                Log.d("Network_WorkspaceListLook", "error")
            }
        })
        super.onViewCreated(view, savedInstanceState)
    }
}