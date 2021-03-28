package com.company.dilnoza.mvvm_hilt.ui.dialogs

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.company.dilnoza.mvvm_hilt.databinding.AddDialogBinding
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import uz.dilnoza.coursework.utils.extentions.SingleBlock
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import java.util.*

class TaskDialog(context: Context) : AlertDialog(context) {
    private var binding=AddDialogBinding.inflate(LayoutInflater.from(context), null,false)
    private var listener: SingleBlock<TaskData>? = null
    private var taskData: TaskData? = null
    private val formatter1= DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val formatter2= DateTimeFormatter.ofPattern("HH:mm")


    init {
        setView(binding.root)
        val cal = Calendar.getInstance()
        var years = cal.get(Calendar.YEAR)
        var months = cal.get(Calendar.MONTH)
        var days = cal.get(Calendar.DAY_OF_MONTH)
        var hour = cal.get(Calendar.HOUR)
        var min = cal.get(Calendar.MINUTE)
        binding.taskDate.setOnClickListener {
            val dialog = DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val dialogTime = TimePickerDialog(
                        context,
                        { _, hourOfDay, minute ->
                            binding.taskTime.text = "$hourOfDay:$minute"
                            hour = hourOfDay
                            min = minute
                          binding.taskTime.text=LocalTime.of(hour,min).format(formatter2)
                        },
                        0,
                        0,
                        true
                    )
                    dialogTime.updateTime(hour, min)
                    dialogTime.show()
                    binding.taskDate.text = "$dayOfMonth/${month + 1}/$year "
                    years = year
                    months = month + 1
                    days = dayOfMonth
                   binding.taskDate.text= LocalDate.of(years,months,days).format(formatter1)
                },
                years,
                months,
                days
            )
            dialog.updateDate(years, months, days)
            dialog.show()

        cal.set(years, months, days, hour, min)
        }

        binding.root.apply {
            binding.ok.setOnClickListener {

                val data = taskData ?: TaskData(0, "", LocalDateTime.of(LocalDate.parse(binding.taskDate.text),
                    LocalTime.parse(binding.taskTime.text)), "", "", "")
                    data.name = binding.taskName.text.toString()
                    data.hashtag = binding.taskHashtag.text.toString()
                    data.info = binding.taskInfo.text.toString()
                    data.dateTime = LocalDateTime.of(LocalDate.parse(binding.taskDate.text),
                        LocalTime.parse(binding.taskTime.text))
                when {
                    binding.taskName.text.toString() == "" -> Toast.makeText(
                        context,
                        "Task name is empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.taskHashtag.text.toString() == "" -> Toast.makeText(
                        context,
                        "Hash tag is empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.taskInfo.text.toString() == "" -> Toast.makeText(
                        context,
                        "Information is empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.taskDate.text == "Date" -> Toast.makeText(
                        context,
                        "Date is empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.taskTime.text == "Time" -> Toast.makeText(
                        context,
                        "Date is empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    else -> {
                        listener?.invoke(data)
                        dismiss()
                        Toast.makeText(context, "Successfully action", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            binding.cancel.setOnClickListener { dismiss() }
        }
    }


    fun setTaskData(taskData: TaskData) = with(binding.root) {
        this@TaskDialog.taskData = taskData
       binding.taskName.setText(taskData.name)
       binding.taskHashtag.setText(taskData.hashtag)
        binding.taskInfo.setText(taskData.info)
       binding.taskDate.text = taskData.dateTime.toLocalDate().toString()
        binding.taskTime.text = taskData.dateTime.toLocalTime().toString()
    }

    fun setOnClickListener(block: SingleBlock<TaskData>) {
        listener = block
    }

}
