package com.company.dilnoza.mvvm_hilt.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.company.dilnoza.mvvm_hilt.ui.fragments.DirectoryFragment

class DirectoryAdapter(
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {
    private lateinit var fragment: Fragment
    override fun getItemCount(): Int = 8

    override fun createFragment(position: Int): Fragment {
            fragment = DirectoryFragment(position)
        return fragment
    }

}