package com.company.dilnoza.mvvm_hilt.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.dilnoza.mvvm_hilt.R
import com.company.dilnoza.mvvm_hilt.contract.AllTaskContract
import com.company.dilnoza.mvvm_hilt.databinding.PagerBinding
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import com.company.dilnoza.mvvm_hilt.ui.adapters.AllTaskAdapter
import com.company.dilnoza.mvvm_hilt.ui.screens.FullItem
import com.company.dilnoza.mvvm_hilt.viewModels.AllTaskViewModel

class AllTaskFragment(private val position: Int) : Fragment(R.layout.pager), AllTaskContract.View {
    private lateinit var list: RecyclerView

    private val viewModel: AllTaskViewModel by viewModels()
    val adapter = AllTaskAdapter()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding=PagerBinding.bind(view)
        list = binding.listEdit
        list.adapter = adapter
        viewModel.loadData.observe(this, loadDataObserver)
        viewModel.openTask.observe(this, openTaskObserver)
        viewModel.openTask.observe(this, cancelTaskObserver)
        viewModel.openTask.observe(this, doneTaskObserver)
        adapter.setOnItemClickListener(viewModel::openTask)
        val ln = LinearLayoutManager(activity)
        list.layoutManager = ln
    }

    private val doneTaskObserver = Observer<TaskData> { done(it) }
    private val cancelTaskObserver = Observer<TaskData> { cancel(it) }
    private val openTaskObserver = Observer<TaskData> { openTask(it) }
    private val loadDataObserver = Observer<ArrayList<List<TaskData>>> {
        when (position) {
            0 -> { adapter.submitList(it[0]) }
            1 -> { adapter.submitList(it[1]) }
            2 -> { adapter.submitList(it[2]) }
        }
    }

    override fun openTask(taskData: TaskData) {
        val intent = Intent(context, FullItem::class.java)
        intent.putExtra("TITLE", taskData.name)
        intent.putExtra("HASHTAG", taskData.hashtag)
        intent.putExtra("INFO", taskData.info)
        intent.putExtra("DATETIME", taskData.dateTime.toString())
        startActivity(intent)
    }


    override fun cancel(taskData: TaskData) {
        val ls = adapter.currentList.toMutableList()
        ls.remove(taskData)
        adapter.submitList(ls)
    }

    override fun done(taskData: TaskData) {
        val ls = adapter.currentList.toMutableList()
        ls.remove(taskData)
        adapter.submitList(ls)
    }
}