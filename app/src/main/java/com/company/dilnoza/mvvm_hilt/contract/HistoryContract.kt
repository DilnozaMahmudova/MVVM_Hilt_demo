package com.company.dilnoza.mvvm_hilt.contract

import androidx.lifecycle.LiveData
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData

class HistoryContract {
    interface Model {
        fun getDeadline(): LiveData<List<TaskData>>
        fun getDone(): LiveData<List<TaskData>>
        fun getCancel(): LiveData<List<TaskData>>
    }

    interface View {
        fun openTask(taskData: TaskData)
    }
    interface ViewModel{
        fun openTask(taskData: TaskData)
        fun loadData()
    }
}
