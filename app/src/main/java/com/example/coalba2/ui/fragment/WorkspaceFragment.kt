package com.example.coalba2.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coalba2.R
import com.example.coalba2.data.response.StoreListData
import com.example.coalba2.databinding.FragmentWorkspaceBinding
import com.example.coalba2.ui.adapter.StoreListAdapter
import com.example.coalba2.ui.view.StoreEditActivity
import com.example.coalba2.ui.view.StoreRegisterActivity

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ivWorkspacePlus.setOnClickListener {
            val intent = Intent(requireContext(), StoreRegisterActivity::class.java)
            startActivity(intent)
        }
        super.onViewCreated(view, savedInstanceState)
    }
    private fun initRecycler(){
        storeListAdapter = StoreListAdapter(requireContext())
        binding.rvStorelist.adapter = storeListAdapter

        datas.apply{
            add(StoreListData(img= R.drawable.ic_emptyimg, name = "송이커피 숙대점"))
            add(StoreListData(img= R.drawable.ic_emptyimg, name = "송이쌀국수 숙대점"))
            add(StoreListData(img= R.drawable.ic_emptyimg, name = "송이마라탕 숙대점"))

            storeListAdapter.datas=datas
            storeListAdapter.notifyDataSetChanged()
        }
    }
}