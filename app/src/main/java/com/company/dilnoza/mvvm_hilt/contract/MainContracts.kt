package com.company.dilnoza.mvvm_hilt.contract

import androidx.lifecycle.LiveData
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData

class MainContracts{
    interface Model{
        fun getTask():LiveData<List<TaskData>>
        fun cancel(taskData: TaskData)
        fun done(taskData: TaskData)
        fun deadline(taskData: TaskData)
    }

    interface View{
        fun loadData(task:List<TaskData>)
        fun openTask(taskData: TaskData)
        fun cancel(taskData: TaskData)
        fun done(taskData: TaskData)
    }

    interface ViewModel{
        fun openTask(taskData: TaskData)
        fun cancel(taskData: TaskData)
        fun done(taskData: TaskData)

    }
}
