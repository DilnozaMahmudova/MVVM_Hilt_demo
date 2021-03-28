package com.company.dilnoza.mvvm_hilt.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.company.dilnoza.mvvm_hilt.databinding.ActivityAllTaskBinding
import com.company.dilnoza.mvvm_hilt.ui.adapters.AllTaskPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.company.dilnoza.mvvm_hilt.ui.transformers.Transformers
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllTaskAct : AppCompatActivity() {
    private lateinit var adapter: AllTaskPagerAdapter
    private val transformers = Transformers()
    private lateinit var binding: ActivityAllTaskBinding
    val list = listOf("Canceled", "Delay", "Done", "Has time")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAllTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "All Tasks"
        adapter = AllTaskPagerAdapter(this)
        binding.listPicture.adapter = adapter
        binding.listPicture.setPageTransformer(transformers)
        TabLayoutMediator(binding.tabLay, binding.listPicture) { tab, position ->
            tab.text = list[position]
        }.attach()
       binding.home.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}