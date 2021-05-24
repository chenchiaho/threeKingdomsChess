package com.example.android.threekingdomschess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.threekingdomschess.databinding.FragmentInfoBinding

class InfoFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding: FragmentInfoBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_info, container, false)

        return binding.root
    }
}