package com.company.dilnoza.mvvm_hilt.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.company.dilnoza.mvvm_hilt.R
import com.company.dilnoza.mvvm_hilt.databinding.ActivityTaskContractsBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.company.dilnoza.mvvm_hilt.ui.adapters.IntoPagerAdapter
import com.company.dilnoza.mvvm_hilt.ui.transformers.CubeRotate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class TaskContractsAct : AppCompatActivity() {
    private lateinit var adapter: IntoPagerAdapter
    private val image =
        arrayListOf(R.drawable.p3, R.drawable.p5, R.drawable.p1, R.drawable.p2, R.drawable.p4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityTaskContractsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val info = arrayListOf(
            "Add to the note the work that needs to be done.",
            "From the Home screen, separate tasks into completed or canceled ones",
            "There is no problem with the deadline",
            "It is easy and fast to find the desired function through hash tags",
            "Tasks are grouped according to status"
        )
        adapter= IntoPagerAdapter(info,image,this)
       binding.listPicture.adapter=adapter
        binding.listPicture.setPageTransformer(CubeRotate())
        adapter.setBack { binding.listPicture.currentItem = binding.listPicture.currentItem - 1 }
        adapter.setNext {  if (binding.listPicture.currentItem != info.size - 1) {
            binding.listPicture.setCurrentItem(binding.listPicture.currentItem + 1, true)
        } else {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }}
        TabLayoutMediator(binding.tabLay, binding.listPicture) { tab, position ->

        }.attach()
        binding.tabLay.selectedTabPosition
    }
}