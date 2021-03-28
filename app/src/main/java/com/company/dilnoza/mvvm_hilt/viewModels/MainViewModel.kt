package com.company.dilnoza.mvvm_hilt.viewModels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.dilnoza.mvvm_hilt.contract.MainContracts
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

@HiltViewModel

class MainViewModel @Inject constructor(
    private val model: MainContracts.Model
) : MainContracts.ViewModel, ViewModel() {
    private val _loadData = MediatorLiveData<List<TaskData>>()
    val loadData: LiveData<List<TaskData>> get() = _loadData
    private val _openTask = MutableLiveData<TaskData>()
    val openTask: LiveData<TaskData> get() = _openTask
    private val _cancelTask = MutableLiveData<TaskData>()
    val cancelTask: LiveData<TaskData> get() = _cancelTask
    private val _doneTask = MutableLiveData<TaskData>()
    val doneTask: LiveData<TaskData> get() = _doneTask
    init {
        _loadData.addSource(model.getTask()) {
            for (task: TaskData in it) {
                if (task.dateTime.isBefore(LocalDateTime.now())) {
                    task.status = "Deadline"
                    model.deadline(task)
                }
            }
            _loadData.postValue(it)
        }
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
}