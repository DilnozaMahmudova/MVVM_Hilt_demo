package com.company.dilnoza.mvvm_hilt.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.company.dilnoza.mvvm_hilt.contract.HistoryContract
import com.company.dilnoza.mvvm_hilt.room.dao.TaskDao
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import java.util.concurrent.ExecutorService
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private var executor: ExecutorService,
    private var taskDao: TaskDao
) : HistoryContract.Model {
    override fun getDeadline(): LiveData<List<TaskData>> {
        val result = MutableLiveData<List<TaskData>>()
        runOnWorkerThread { result.postValue(taskDao.getDeadlineList()) }
        return result
    }

    override fun getDone(): LiveData<List<TaskData>> {
        val result = MutableLiveData<List<TaskData>>()
        runOnWorkerThread { result.postValue(taskDao.getDoneList())}
        return result
    }

    override fun getCancel(): LiveData<List<TaskData>> {
        val result = MutableLiveData<List<TaskData>>()
        runOnWorkerThread {  result.postValue(taskDao.getCancelList()) }
        return result
    }

    private fun runOnWorkerThread(f: () -> Unit) {
        executor.execute { f() }
    }
}