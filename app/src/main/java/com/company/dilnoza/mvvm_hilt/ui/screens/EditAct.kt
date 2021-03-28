package com.company.dilnoza.mvvm_hilt.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.dilnoza.mvvm_hilt.contract.EditContract
import com.company.dilnoza.mvvm_hilt.databinding.ActivityEditBinding
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import com.company.dilnoza.mvvm_hilt.viewModels.EditViewModel
import com.company.dilnoza.mvvm_hilt.ui.adapters.EditActAdapter
import com.company.dilnoza.mvvm_hilt.ui.dialogs.TaskDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditAct : AppCompatActivity(), EditContract.View {
    private val viewModel:EditViewModel by viewModels()
    private val adapter = EditActAdapter()
    private  lateinit var binding:ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Edit tasks"
       binding.listEdit.layoutManager = LinearLayoutManager(this)
       binding.listEdit.adapter = adapter
        viewModel.cancelTask.observe(this,cancelObserver)
        viewModel.confirmEditTask.observe(this, confirmObserver)
        viewModel.deleteTask.observe(this, deleteObserver)
        viewModel.editTask.observe(this,editObserver)
        viewModel.loadData.observe(this,loadObserver)
        viewModel.openTask.observe(this,openObserver)
        adapter.setOnEditListener(viewModel::editTask)
        adapter.setOnItemClickListener(viewModel::openTask)
        adapter.setOnItemDeleteListener(viewModel::deleteTask)
        adapter.setOnItemCancelListener(viewModel::cancelTask)
        binding.home.setOnClickListener {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        }
    }
    private val cancelObserver=Observer<TaskData>{cancelTask(it)}
    private val confirmObserver=Observer<TaskData>{updateTask(it)}
    private val deleteObserver=Observer<TaskData>{deleteTask(it)}
    private val editObserver=Observer<TaskData>{openEditDialog(it)}
    private val loadObserver=Observer<List<TaskData>>{loadData(it)}
    private val openObserver=Observer<TaskData>{openTask(it)}

    override fun loadData(data: List<TaskData>) {
        adapter.submitList(data)
    }

    override fun openEditDialog(taskData: TaskData) {
        val dialog = TaskDialog(this)
        dialog.setTaskData(taskData)
        dialog.setOnClickListener(viewModel::confirmEditTask)
        dialog.show()

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

    override fun updateTask(taskData: TaskData) {
        val ls = adapter.currentList.toMutableList()
        val index = ls.indexOfFirst { it.id == taskData.id }
        ls[index] = taskData
        adapter.submitList(ls)
        adapter.notifyItemChanged(index)
    }

    override fun deleteTask(taskData: TaskData) {
        val ls = adapter.currentList.toMutableList()
        ls.remove(taskData)
        adapter.submitList(ls)
    }

    override fun cancelTask(taskData: TaskData) {
        val ls = adapter.currentList.toMutableList()
        ls.remove(taskData)
        adapter.submitList(ls)
    }
}