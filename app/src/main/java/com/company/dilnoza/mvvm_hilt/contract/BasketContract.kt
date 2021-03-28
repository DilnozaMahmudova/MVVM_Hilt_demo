package com.company.dilnoza.mvvm_hilt.contract

import androidx.lifecycle.LiveData
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData

class BasketContract{
    interface Model{
        fun getDeletedTask():LiveData<List<TaskData>>
        fun restore(taskData: TaskData)
        fun delete(taskData: TaskData)
    }

    interface View{
        fun loadData(data:List<TaskData>)
        fun restore(taskData: TaskData)
        fun delete(taskData: TaskData)
        fun openTask(taskData: TaskData)
    }
    interface ViewModel{
        fun openTask(taskData: TaskData)
        fun restore(taskData: TaskData)
        fun delete(taskData: TaskData)
    }
}