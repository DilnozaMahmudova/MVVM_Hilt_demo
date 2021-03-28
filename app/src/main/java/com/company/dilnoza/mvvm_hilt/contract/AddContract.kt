package com.company.dilnoza.mvvm_hilt.contract

import com.company.dilnoza.mvvm_hilt.room.entity.TaskData

class AddContract {
    interface Model{
        fun insertTask(taskData: TaskData)
    }

    interface View{


    }

    interface ViewModel{
        fun openAddTask()
        fun createTask(taskData: TaskData)
    }
}