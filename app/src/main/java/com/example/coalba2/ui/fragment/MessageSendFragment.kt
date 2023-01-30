package com.example.coalba2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coalba2.R
import com.example.coalba2.databinding.FragmentMessageSendBinding

class MessageSendFragment : Fragment() {
    private lateinit var binding: FragmentMessageSendBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageSendBinding.inflate(inflater, container, false)
        return binding.root
    }
}