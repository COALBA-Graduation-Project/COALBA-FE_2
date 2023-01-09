package com.example.coalba2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coalba2.data.response.StoreListData
import com.example.coalba2.databinding.FragmentWorkspaceBinding
import com.example.coalba2.ui.adapter.StoreListAdapter

class WorkspaceFragment : Fragment() {

    private lateinit var binding: FragmentWorkspaceBinding
    lateinit var storeListAdapter: StoreListAdapter
    val datas = mutableListOf<StoreListData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkspaceBinding.inflate(inflater, container, false)
        initRecycler()
        return binding.root
    }
    private fun initRecycler(){
        storeListAdapter = StoreListAdapter(requireContext())
        binding.rvStorelist.adapter = storeListAdapter

        datas.apply{
            add(StoreListData(name = "송이커피 숙대점"))
            add(StoreListData(name = "송이쌀국수 숙대점"))
            add(StoreListData(name = "송이마라탕 숙대점"))

            storeListAdapter.datas=datas
            storeListAdapter.notifyDataSetChanged()
        }
    }
}