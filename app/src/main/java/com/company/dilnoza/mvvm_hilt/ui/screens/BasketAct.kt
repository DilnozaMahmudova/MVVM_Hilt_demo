package com.company.dilnoza.mvvm_hilt.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.dilnoza.mvvm_hilt.contract.BasketContract
import com.company.dilnoza.mvvm_hilt.databinding.ActivityEditBinding
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import com.company.dilnoza.mvvm_hilt.viewModels.BasketViewModel
import com.company.dilnoza.mvvm_hilt.ui.adapters.BasketAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketAct : AppCompatActivity(), BasketContract.View {
    private val viewModel: BasketViewModel by viewModels()
    private lateinit var binding: ActivityEditBinding
    private val adapter = BasketAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Basket"
        binding.listEdit.layoutManager = LinearLayoutManager(this)
        binding.listEdit.adapter = adapter
        viewModel.deleteTask.observe(this, deleteTaskObserver)
        viewModel.openTask.observe(this, openTaskObserver)
        viewModel.restore.observe(this, restoreObserver)
        viewModel.loadData.observe(this, loadDataObserver)
        adapter.setOnItemClickListener(viewModel::openTask)
        adapter.setOnItemDeleteListener(viewModel::delete)
        adapter.setOnItemInsertListener(viewModel::restore)
        binding.home.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private val deleteTaskObserver = Observer<TaskData> { delete(it) }
    private val openTaskObserver = Observer<TaskData> { openTask(it) }
    private  val restoreObserver = Observer<TaskData> { restore(it) }
    private  val loadDataObserver = Observer<List<TaskData>> { loadData(it) }

    override fun loadData(data: List<TaskData>) {
        adapter.submitList(data)
    }

    override fun restore(taskData: TaskData) {
        val ls = adapter.currentList.toMutableList()
        ls.remove(taskData)
        adapter.submitList(ls)
    }

    override fun delete(taskData: TaskData) {
        val ls = adapter.currentList.toMutableList()
        ls.remove(taskData)
        adapter.submitList(ls)
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


}