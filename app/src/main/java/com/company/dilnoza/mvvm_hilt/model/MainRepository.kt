package com.company.dilnoza.mvvm_hilt.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.company.dilnoza.mvvm_hilt.contract.MainContracts
import com.company.dilnoza.mvvm_hilt.room.dao.TaskDao
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import java.util.concurrent.ExecutorService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private var executor: ExecutorService,
    private var taskDao: TaskDao
) : MainContracts.Model {

    override fun getTask(): LiveData<List<TaskData>> {
        val result = MutableLiveData<List<TaskData>>()
        runOnWorkerThread { result.postValue(taskDao.getList()) }
        return result
    }

    override fun cancel(taskData: TaskData) {
        runOnWorkerThread { taskDao.update(taskData) }
    }

    override fun done(taskData: TaskData) {
        runOnWorkerThread { taskDao.update(taskData) }
    }

    override fun deadline(taskData: TaskData) {
        runOnWorkerThread { taskDao.update(taskData) }
    }

    private fun runOnWorkerThread(f: () -> Unit) {
        executor.execute { f() }
    }
}