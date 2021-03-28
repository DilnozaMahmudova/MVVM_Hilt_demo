package com.company.dilnoza.mvvm_hilt.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.company.dilnoza.mvvm_hilt.contract.AddContract
import com.company.dilnoza.mvvm_hilt.databinding.ActivityAddTaskBinding
import com.company.dilnoza.mvvm_hilt.ui.dialogs.TaskDialog
import com.company.dilnoza.mvvm_hilt.viewModels.AddViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskAct : AppCompatActivity(), AddContract.View {
    private lateinit var binding:ActivityAddTaskBinding
    private val viewModel: AddViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Add Task"
        viewModel.openDialog.observe(this,openDialogObserver)
        binding.add.setOnClickListener { viewModel.openAddTask() }
        binding.home.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    private val openDialogObserver=Observer<Unit>{ TaskDialog(this).apply {
        setOnClickListener (viewModel::createTask)
        show()
    }}

}