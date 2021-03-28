package com.company.dilnoza.mvvm_hilt.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.dilnoza.mvvm_hilt.R
import com.company.dilnoza.mvvm_hilt.contract.MainContracts
import com.company.dilnoza.mvvm_hilt.databinding.ActivityMainBinding
import com.company.dilnoza.mvvm_hilt.databinding.ContentBinding
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import com.company.dilnoza.mvvm_hilt.ui.adapters.MainAdapter
import com.company.dilnoza.mvvm_hilt.viewModels.MainViewModel
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : AppCompatActivity(), MainContracts.View {
    private val viewModel:MainViewModel by viewModels()
    private lateinit var binding:ActivityMainBinding
    private lateinit var binding2:ContentBinding
    private lateinit var adapter: MainAdapter
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        sharedPref = this.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE)
        editor = sharedPref.edit()
        if (sharedPref.getBoolean("SPLASH", true)) {
            editor.putBoolean("SPLASH", false).apply()
            val homeIntent = Intent(this, TaskContractsAct::class.java)
            startActivity(homeIntent)
        }
        binding= ActivityMainBinding.inflate(layoutInflater)
        binding2= ContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "MainScreen"
        adapter = MainAdapter()

        binding2.list.layoutManager = LinearLayoutManager(this)
       binding2.list.adapter = adapter
        adapter.setOnCancelListener(viewModel::cancel)
        adapter.setOnDoneClickListener(viewModel::done)
        adapter.setOnNextClickListener {
            openTask(it)
        }

       binding2.menu.setOnClickListener { binding.drawerLayout.openDrawer(GravityCompat.START) }
        binding.navigationView.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.all_task -> {
                    startActivity(Intent(this, AllTaskAct::class.java))
                    finish()
                }
                R.id.add_task -> {
                    startActivity(Intent(this, AddTaskAct::class.java))
                    finish()
                }
                R.id.basket -> {
                    startActivity(Intent(this, BasketAct::class.java))
                    finish()
                }
                R.id.history -> {
                    startActivity(Intent(this, HistoryAct::class.java))
                    finish()
                }
                R.id.edit -> {
                    startActivity(Intent(this, EditAct::class.java))
                    finish()
                }
                R.id.rules -> {
                    startActivity(Intent(this, TaskContractsAct::class.java))
                    finish()
                }
                R.id.instruction -> {
                    startActivity(Intent(this, HandbookAct::class.java))
                    finish()
                }
                R.id.share -> {
                    startActivity(Intent(this, ShareAct::class.java))
                    finish()
                }
            }
            item.isChecked = true
           binding.drawerLayout.closeDrawers()
            true

        }
        viewModel.cancelTask.observe(this,cancelTaskObserver)
        viewModel.doneTask.observe(this,doneTaskObserver)
        viewModel.loadData.observe(this, loadDataObserver)
        viewModel.openTask.observe(this,openTaskObserver)

    }
    private val cancelTaskObserver=Observer<TaskData>{cancel(it)}
    private val openTaskObserver=Observer<TaskData>{openTask(it)}
    private val doneTaskObserver=Observer<TaskData>{done(it)}
    private val loadDataObserver=Observer<List<TaskData>>{ loadData(it)}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun loadData(task: List<TaskData>) {
        adapter.submitList(task)
    }

    override fun openTask(taskData: TaskData) {
        val intent = Intent(this, FullItem::class.java)
        intent.putExtra("TITLE", taskData.name)
        intent.putExtra("HASHTAG", taskData.hashtag)
        intent.putExtra("INFO", taskData.info)
        intent.putExtra("DATETIME", taskData.dateTime.toString())
        startActivity(intent)
        finish()
    }

    override fun cancel(taskData: TaskData) {
        adapter.removeItem(taskData)
    }

    override fun done(taskData: TaskData) {
        adapter.removeItem(taskData)
    }
}