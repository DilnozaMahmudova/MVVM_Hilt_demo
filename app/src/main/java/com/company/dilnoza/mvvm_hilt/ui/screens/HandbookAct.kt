package com.company.dilnoza.mvvm_hilt.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.company.dilnoza.mvvm_hilt.databinding.ActivityHandbookBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.company.dilnoza.mvvm_hilt.ui.adapters.DirectoryAdapter
import com.company.dilnoza.mvvm_hilt.ui.transformers.Transformers
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HandbookAct : AppCompatActivity() {
    private lateinit var adapter: DirectoryAdapter
    private val transformers = Transformers()
    val list = listOf(
        "Main screen",
        "Add task",
        "All tasks",
        "Basket",
        "Edit",
        "History",
        "Share",
        "Rules"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityHandbookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = DirectoryAdapter(this)
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