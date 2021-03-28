package com.company.dilnoza.mvvm_hilt.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.company.dilnoza.mvvm_hilt.databinding.ItemNoteBinding
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import org.threeten.bp.format.DateTimeFormatter
import uz.dilnoza.coursework.utils.extentions.SingleBlock


class AllTaskAdapterB : ListAdapter<TaskData, AllTaskAdapterB.ViewHolder>(TaskData.ITEM_CALLBACK) {
    private var listenerItem: SingleBlock<TaskData>? = null
    private lateinit var binding: ItemNoteBinding
    private var listenerDone: SingleBlock<TaskData>? = null
    private var listenerCancel: SingleBlock<TaskData>? = null
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    fun setOnItemClickListener(block: SingleBlock<TaskData>) {
        listenerItem = block
    }

    fun setOnDoneClickListener(block: SingleBlock<TaskData>) {
        listenerDone = block
    }

    fun setOnCancelListener(block: SingleBlock<TaskData>) {
        listenerCancel = block
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val d = getItem(adapterPosition)
            binding.title.text = d.name
            binding.hashtag.text = d.hashtag
            binding.deadline.text = d.dateTime.format(formatter)
            binding.color.setBackgroundColor(Color.TRANSPARENT)
            binding.next.setColorFilter(Color.TRANSPARENT)
        }
    }
}

