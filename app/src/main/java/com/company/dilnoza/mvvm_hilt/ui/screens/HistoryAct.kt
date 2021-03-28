package com.company.dilnoza.mvvm_hilt.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.company.dilnoza.mvvm_hilt.databinding.ActivityAllTaskBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.company.dilnoza.mvvm_hilt.ui.adapters.HistoryPagerAdapter
import com.company.dilnoza.mvvm_hilt.ui.transformers.Transformers
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class HistoryAct : AppCompatActivity() {
    private val transformers = Transformers()
    val list = listOf("Done", "Delay", "Canceled")
    private lateinit var adapter: HistoryPagerAdapter
    private lateinit var binding:ActivityAllTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAllTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "History of tasks"
        adapter = HistoryPagerAdapter(this)
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