package com.example.coalba2.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            startActivity(intent)
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
                    Log.d("num 값", "num 값 " + num)
                    storeListAdapter = StoreListAdapter(requireContext())
                    binding.rvStorelist.adapter = storeListAdapter

                    for(i in 0..num-1){
                        val itemdata = response.body()?.workspaceList?.get(i)
                        Log.d("responsevalue", "itemdata1_response 값 => "+ itemdata)
                        datas.add(StoreListData(itemdata!!.imageUrl, itemdata!!.name))
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