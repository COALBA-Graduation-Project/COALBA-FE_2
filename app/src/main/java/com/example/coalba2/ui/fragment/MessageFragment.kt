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
import com.example.coalba2.data.response.StoreListData
import com.example.coalba2.data.response.StoreListForMessageData
import com.example.coalba2.data.response.WorkspaceListLookResponseData
import com.example.coalba2.databinding.FragmentMessageBinding
import com.example.coalba2.ui.adapter.StoreListAdapter
import com.example.coalba2.ui.adapter.StoreListForMessageAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageFragment : Fragment() {
    private lateinit var binding: FragmentMessageBinding
    lateinit var storeListForMessageAdapter: StoreListForMessageAdapter
    val datas = mutableListOf<StoreListForMessageData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 나의 워크스페이스 리스트 조회 서버 연동
        RetrofitManager.workspaceService?.workspaceListLook()?.enqueue(object:
            Callback<WorkspaceListLookResponseData> {
            override fun onResponse(
                call: Call<WorkspaceListLookResponseData>,
                response: Response<WorkspaceListLookResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("WorkspaceListLook", "success")
                    val data = response.body()
                    val num = data!!.workspaceList.count()
                    Log.d("num 값", "num 값 " + num)
                    storeListForMessageAdapter = StoreListForMessageAdapter(requireContext())
                    binding.rvStorelistMessage.adapter = storeListForMessageAdapter
                    if (num == 0){
                        Toast.makeText(requireContext(), "존재하는 워크스페이스가 없습니다", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        for(i in 0..num-1){
                            val itemdata = response.body()?.workspaceList?.get(i)
                            Log.d("responsevalue", "itemdata1_response 값 => "+ itemdata)
                            datas.add(StoreListForMessageData(itemdata!!.workspaceId, itemdata.imageUrl, itemdata.name))
                        }
                        storeListForMessageAdapter.datas=datas
                        storeListForMessageAdapter.notifyDataSetChanged()
                    }
                }else{
                    // 이곳은 에러 발생할 경우 실행됨
                    Log.d("WorkspaceListLook", "fail")
                }
            }
            override fun onFailure(call: Call<WorkspaceListLookResponseData>, t: Throwable) {
                Log.d("WorkspaceListLook", "error")
            }
        })
        super.onViewCreated(view, savedInstanceState)
    }
}