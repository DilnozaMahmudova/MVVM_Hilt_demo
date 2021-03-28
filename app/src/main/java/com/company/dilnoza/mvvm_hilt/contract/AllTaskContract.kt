package com.company.dilnoza.mvvm_hilt.contract

import androidx.lifecycle.LiveData
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData

class AllTaskContract {
    interface Model {
        fun getAllTask(): LiveData<List<TaskData>>
        fun cancel(taskData: TaskData)
        fun done(taskData: TaskData)
        fun getDeadline(): LiveData<List<TaskData>>
        fun getDone(): LiveData<List<TaskData>>
        fun getCancel(): LiveData<List<TaskData>>
    }

    interface View {
        fun openTask(taskData: TaskData)
        fun cancel(taskData: TaskData)
        fun done(taskData: TaskData)
    }


    interface ViewModel {
        fun openTask(taskData: TaskData)
        fun cancel(taskData: TaskData)
        fun done(taskData: TaskData)
        fun loadData()
        fun loadAll()
    }
}