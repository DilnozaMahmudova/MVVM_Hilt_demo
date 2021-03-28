package com.company.dilnoza.mvvm_hilt.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.company.dilnoza.mvvm_hilt.databinding.ActivityFullItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullItem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityFullItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title="Task"
        binding.taskName.text = intent.getStringExtra("TITLE")
       binding.hashTag.text = intent.getStringExtra("HASHTAG")
        binding.taskInfo.text = intent.getStringExtra("INFO")
        binding.deadline.text = intent.getStringExtra("DATETIME")
       binding.home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java))
            finish() }
    }

}