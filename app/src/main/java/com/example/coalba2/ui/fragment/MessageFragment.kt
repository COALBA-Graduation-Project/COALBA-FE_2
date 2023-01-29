package com.example.coalba2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coalba2.R
import com.example.coalba2.data.response.StoreListData
import com.example.coalba2.data.response.StoreListForMessageData
import com.example.coalba2.databinding.FragmentMessageBinding
import com.example.coalba2.ui.adapter.StoreListAdapter
import com.example.coalba2.ui.adapter.StoreListForMessageAdapter

class MessageFragment : Fragment() {
    private lateinit var binding: FragmentMessageBinding
    lateinit var storeListForMessageAdapter: StoreListForMessageAdapter
    val datas = mutableListOf<StoreListForMessageData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        initRecycler()
        return binding.root
    }
    private fun initRecycler(){
        storeListForMessageAdapter = StoreListForMessageAdapter(requireContext())
        binding.rvStorelistMessage.adapter = storeListForMessageAdapter

        datas.apply{
            add(StoreListForMessageData(img= R.drawable.ic_emptyimg, name = "송이커피 숙대점"))
            add(StoreListForMessageData(img= R.drawable.ic_emptyimg, name = "송이쌀국수 숙대점"))
            add(StoreListForMessageData(img= R.drawable.ic_emptyimg, name = "송이마라탕 숙대점"))

            storeListForMessageAdapter.datas=datas
            storeListForMessageAdapter.notifyDataSetChanged()
        }
    }
}