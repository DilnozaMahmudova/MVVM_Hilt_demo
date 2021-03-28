package com.company.dilnoza.mvvm_hilt.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.dilnoza.mvvm_hilt.contract.HistoryContract
import com.company.dilnoza.mvvm_hilt.extentions.addSourceDisposable
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val model: HistoryContract.Model
) : HistoryContract.ViewModel, ViewModel() {
    private val _loadData = MediatorLiveData<ArrayList<List<TaskData>>>()
    val loadData: LiveData<ArrayList<List<TaskData>>> get() = _loadData
    private val _openTask = MutableLiveData<TaskData>()
    val openTask: LiveData<TaskData> get() = _openTask

    init {
        loadData()
    }

    override fun openTask(taskData: TaskData) {
        _openTask.postValue(taskData)
    }

    override fun loadData() {
        val list = ArrayList<List<TaskData>>()
        _loadData.addSourceDisposable(model.getDone()) { list.add(it) }
        _loadData.addSourceDisposable(model.getDeadline()) { list.add(it) }
        _loadData.addSourceDisposable(model.getCancel()) {
            list.add(it)
            _loadData.postValue(list)
        }
    }
}




