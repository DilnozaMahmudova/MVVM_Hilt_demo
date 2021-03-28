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
import com.company.dilnoza.mvvm_hilt.ui.adapters.AllTaskAdapterB
import com.company.dilnoza.mvvm_hilt.ui.screens.FullItem
import com.company.dilnoza.mvvm_hilt.viewModels.AllTaskViewModel


class AllTaskFragmentB : Fragment(R.layout.pager), AllTaskContract.View {
    lateinit var list: RecyclerView
    val adapter = AllTaskAdapterB()
    private val viewModel: AllTaskViewModel by viewModels()
    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding=PagerBinding.bind(view)
        list = binding.listEdit
        list.adapter = adapter
        viewModel.loadAll.observe(this, loadDataObserver)
        viewModel.openTask.observe(this, openTaskObserver)
        viewModel.cancelTask.observe(this, cancelTaskObserver)
        viewModel.doneTask.observe(this, doneTaskObserver)
        adapter.setOnItemClickListener(viewModel::openTask)
        adapter.setOnCancelListener(viewModel::cancel)
        adapter.setOnDoneClickListener(viewModel::done)
        val ln = LinearLayoutManager(activity)
        list.layoutManager = ln
    }
    private val doneTaskObserver = Observer<TaskData> { done(it) }
    private val cancelTaskObserver = Observer<TaskData> { cancel(it) }
    private val openTaskObserver = Observer<TaskData> { openTask(it) }
    private val loadDataObserver = Observer<List<TaskData>> { adapter.submitList(it) }

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
