package com.company.dilnoza.mvvm_hilt.model

import com.company.dilnoza.mvvm_hilt.contract.AddContract
import com.company.dilnoza.mvvm_hilt.room.dao.TaskDao
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import java.util.concurrent.ExecutorService
import javax.inject.Inject

class AddRepository @Inject constructor(    private var executor:ExecutorService,
        private val taskDao:TaskDao): AddContract.Model {

    override fun insertTask(taskData: TaskData) {
        runOnWorkerThread {
        val id=taskDao.insert(taskData)
        taskData.id=id
        }
    }
    private fun runOnWorkerThread(f: () -> Unit) { executor.execute { f() } }
}