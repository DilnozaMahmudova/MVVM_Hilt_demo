package com.company.dilnoza.mvvm_hilt.contract

import androidx.lifecycle.LiveData
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData

class EditContract {
    interface Model{
        fun getTask():LiveData<List<TaskData>>
        fun updateTask(taskData: TaskData)
        fun cancelTask(taskData: TaskData)
        fun deleteTask(taskData: TaskData)

    }
    interface View{
        fun loadData(data:List<TaskData>)
        fun openEditDialog(taskData: TaskData)
        fun openTask(taskData: TaskData)
        fun updateTask(taskData: TaskData)
        fun deleteTask(taskData: TaskData)
        fun cancelTask(taskData: TaskData)


    }
    interface ViewModel{
        fun openTask(taskData: TaskData)
        fun deleteTask(taskData: TaskData)
        fun confirmEditTask(taskData: TaskData)
        fun editTask(taskData: TaskData)
        fun cancelTask(taskData: TaskData)
    }
}