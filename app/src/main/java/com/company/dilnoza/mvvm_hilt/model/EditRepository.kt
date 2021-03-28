package com.company.dilnoza.mvvm_hilt.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.company.dilnoza.mvvm_hilt.contract.EditContract
import com.company.dilnoza.mvvm_hilt.room.dao.TaskDao
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import java.util.concurrent.ExecutorService
import javax.inject.Inject

class EditRepository @Inject constructor(
    private var executor: ExecutorService,
    private var taskDao: TaskDao
) : EditContract.Model {

    override fun getTask(): LiveData<List<TaskData>> {
        val result = MutableLiveData<List<TaskData>>()
        runOnWorkerThread {   result.postValue(taskDao.getAll()) }
        return result
    }

    override fun updateTask(taskData: TaskData) {
        runOnWorkerThread { taskDao.update(taskData) }
    }

    override fun cancelTask(taskData: TaskData) {
        runOnWorkerThread { taskDao.update(taskData) }
    }

    override fun deleteTask(taskData: TaskData) {
        runOnWorkerThread { taskDao.update(taskData) }
    }

    private fun runOnWorkerThread(f: () -> Unit) {
        executor.execute { f() }
    }

}