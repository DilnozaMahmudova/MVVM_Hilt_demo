package com.company.dilnoza.mvvm_hilt.viewModels


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.dilnoza.mvvm_hilt.contract.EditContract
import com.company.dilnoza.mvvm_hilt.extentions.addSourceDisposable
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class EditViewModel @Inject constructor(private val model: EditContract.Model) : EditContract.ViewModel,ViewModel() {
    private val _loadData = MediatorLiveData<List<TaskData>>()
    val loadData: LiveData<List<TaskData>> get() = _loadData
    private val _openTask = MutableLiveData<TaskData>()
    val openTask: LiveData<TaskData> get() = _openTask
    private val _delete = MutableLiveData<TaskData>()
    val deleteTask: LiveData<TaskData> get() = _delete
    private val _confirmEditTask = MutableLiveData<TaskData>()
    val confirmEditTask: LiveData<TaskData> get() = _confirmEditTask
    private val _editTask = MutableLiveData<TaskData>()
    val editTask: LiveData<TaskData> get() = _editTask
    private val _cancelTask = MutableLiveData<TaskData>()
    val cancelTask: LiveData<TaskData> get() = _cancelTask
    init {
        _loadData.addSourceDisposable(model.getTask()){ _loadData.postValue(it) }
    }

    override fun openTask(taskData: TaskData) {
        _openTask.postValue(taskData)
    }

    override fun deleteTask(taskData: TaskData) {
        taskData.status = "Delete"
        model.deleteTask(taskData)
        _delete.postValue(taskData)
    }

    override fun confirmEditTask(taskData: TaskData) {
        model.updateTask(taskData)
        _confirmEditTask.postValue(taskData)

    }

    override fun editTask(taskData: TaskData) {
        taskData.status = ""
        _editTask.postValue(taskData)
    }

    override fun cancelTask(taskData: TaskData) {
        taskData.status = "Cancel"
        model.cancelTask(taskData)
        _cancelTask.postValue(taskData)
    }

}