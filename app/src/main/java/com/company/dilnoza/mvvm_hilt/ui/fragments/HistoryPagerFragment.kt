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
import com.company.dilnoza.mvvm_hilt.contract.HistoryContract
import com.company.dilnoza.mvvm_hilt.databinding.PagerBinding
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import com.company.dilnoza.mvvm_hilt.ui.adapters.HistoryAdapter
import com.company.dilnoza.mvvm_hilt.ui.screens.FullItem
import com.company.dilnoza.mvvm_hilt.viewModels.HistoryViewModel


class HistoryPagerFragment(private val position:Int):Fragment(R.layout.pager), HistoryContract.View{
  lateinit var list:RecyclerView
  private val viewModel:HistoryViewModel by viewModels()
    private  lateinit var binding:PagerBinding
      private  val adapter= HistoryAdapter()
    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding= PagerBinding.bind(view)
        list=view.findViewById(R.id.listEdit)
        list.adapter=adapter
        viewModel.loadData.observe(this,loadDataObserver)
        viewModel.openTask.observe(this,openTaskObserver)
        adapter.setOnItemClickListener(viewModel::openTask)
        val ln=LinearLayoutManager(activity)
        list.layoutManager=ln
    }

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
}