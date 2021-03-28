package com.company.dilnoza.mvvm_hilt.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.dilnoza.mvvm_hilt.contract.BasketContract
import com.company.dilnoza.mvvm_hilt.extentions.addSourceDisposable
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val model: BasketContract.Model
) : BasketContract.ViewModel, ViewModel() {
    private val _openTask = MutableLiveData<TaskData>()
    val openTask: LiveData<TaskData> get() = _openTask
    private val _delete = MutableLiveData<TaskData>()
    val deleteTask: LiveData<TaskData> get() = _delete
    private val _restore = MutableLiveData<TaskData>()
    val restore: LiveData<TaskData> get() = _restore
    private val _loadData = MediatorLiveData<List<TaskData>>()
    val loadData: LiveData<List<TaskData>> get() = _loadData

    init {
        _loadData.addSourceDisposable(model.getDeletedTask()) {
            _loadData.postValue(it)
        }
    }


    override fun openTask(taskData: TaskData) {
        _openTask.postValue(taskData)
    }

    override fun restore(taskData: TaskData) {
        taskData.status = ""
            model.restore(taskData)
        _restore.postValue(taskData)
        }

    override fun delete(taskData: TaskData) {
        taskData.status = "Delete"
            model.delete(taskData)
           _delete.postValue(taskData)
        }
    }
