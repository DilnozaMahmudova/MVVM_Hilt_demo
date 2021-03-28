package com.company.dilnoza.mvvm_hilt.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.company.dilnoza.mvvm_hilt.contract.BasketContract
import com.company.dilnoza.mvvm_hilt.room.dao.TaskDao
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import java.util.concurrent.ExecutorService
import javax.inject.Inject

class BasketRepository @Inject constructor(
    private var executor: ExecutorService,
    private var taskDao: TaskDao
) : BasketContract.Model {
    override fun getDeletedTask(): LiveData<List<TaskData>> {
        val result = MutableLiveData<List<TaskData>>()
        runOnWorkerThread { result.postValue(taskDao.getDeleteList())}
        return result
    }

    override fun restore(taskData: TaskData) {
        runOnWorkerThread { taskDao.update(taskData) }
    }

    override fun delete(taskData: TaskData) {
        runOnWorkerThread { taskDao.delete(taskData) }
    }

    private fun runOnWorkerThread(f: () -> Unit) {
        executor.execute { f() }
    }
}