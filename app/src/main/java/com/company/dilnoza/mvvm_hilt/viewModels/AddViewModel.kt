package com.company.dilnoza.mvvm_hilt.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.dilnoza.mvvm_hilt.contract.AddContract
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val model: AddContract.Model) :
    AddContract.ViewModel, ViewModel() {
    private val _openDialog=MutableLiveData<Unit>()
    val openDialog:LiveData<Unit>get() = _openDialog
    
    override fun openAddTask() { _openDialog.value=Unit}

    override fun createTask(taskData: TaskData) {  model.insertTask(taskData) }


    
}