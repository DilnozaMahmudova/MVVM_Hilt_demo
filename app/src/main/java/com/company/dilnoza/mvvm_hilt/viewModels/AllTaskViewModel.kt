package com.company.dilnoza.mvvm_hilt.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.dilnoza.mvvm_hilt.contract.AllTaskContract
import com.company.dilnoza.mvvm_hilt.extentions.addSourceDisposable
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllTaskViewModel @Inject constructor(private val model: AllTaskContract.Model) : AllTaskContract.ViewModel, ViewModel() {
    private val _openTask = MutableLiveData<TaskData>()
    val openTask: LiveData<TaskData> get() = _openTask
    private val _cancelTask = MutableLiveData<TaskData>()
    val cancelTask: LiveData<TaskData> get() = _cancelTask
    private val _doneTask = MutableLiveData<TaskData>()
    val doneTask: LiveData<TaskData> get() = _doneTask
    private val _loadData = MediatorLiveData<ArrayList<List<TaskData>>>()
    val loadData: LiveData<ArrayList<List<TaskData>>> get() = _loadData
    private val _loadAll = MediatorLiveData<List<TaskData>>()
    val loadAll: LiveData<List<TaskData>> get() = _loadAll

    init {
        loadData()
    }

    override fun openTask(taskData: TaskData) {
        _openTask.postValue(taskData)
    }

    override fun cancel(taskData: TaskData) {
        taskData.status = "Cancel"
        model.cancel(taskData)
        _cancelTask.postValue(taskData)

    }

    override fun done(taskData: TaskData) {
        taskData.status = "Done"
        model.done(taskData)
        _doneTask.postValue(taskData)
    }

    override fun loadData() {
        val list = ArrayList<List<TaskData>>()
        _loadData.addSourceDisposable(model.getCancel()) { list.add(it) }
        _loadData.addSourceDisposable(model.getDeadline()) { list.add(it) }
        _loadData.addSourceDisposable(model.getDone()) {
            list.add(it)
            _loadData.postValue(list)         }
    }

    override fun loadAll() {
        _loadAll.addSourceDisposable(model.getAllTask()) {
            _loadAll.postValue(it)
        }
    }
}

