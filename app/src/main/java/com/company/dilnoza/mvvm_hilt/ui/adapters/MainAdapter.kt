package com.company.dilnoza.mvvm_hilt.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.company.dilnoza.mvvm_hilt.databinding.ItemNoteBinding
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import uz.dilnoza.coursework.utils.extentions.SingleBlock
import uz.dilnoza.coursework.utils.extentions.bindItem

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private lateinit var binding:ItemNoteBinding
    private var sort: SortedList<TaskData> = SortedList(TaskData::class.java, object : SortedList.Callback<TaskData>() {
        override fun areItemsTheSame(item1: TaskData, item2: TaskData) = item1.id == item2.id

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            notifyItemMoved(fromPosition, toPosition)
        }

        override fun onChanged(position: Int, count: Int) {
            notifyItemRangeChanged(position, count)
        }

        override fun onInserted(position: Int, count: Int) {
            notifyItemRangeInserted(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            notifyItemRangeRemoved(position, count)
        }

        override fun compare(o1: TaskData, o2: TaskData) = o1.dateTime.compareTo(o2.dateTime)

        override fun areContentsTheSame(oldItem: TaskData, newItem: TaskData) =
            oldItem.name == newItem.name && oldItem.hashtag == newItem.hashtag
    })
    private var listenerItem: SingleBlock<TaskData>? = null
    private var listenerDone: SingleBlock<TaskData>? = null
    private var listenerCancel: SingleBlock<TaskData>? = null
    private val formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    fun submitList(data: List<TaskData>) {
        sort.clear()
        sort.beginBatchedUpdates()
        for (item:TaskData in data){
            sort.addAll(item)
        }
        sort.endBatchedUpdates()
        notifyItemRangeRemoved(0,data.size)
    }

    fun removeItem(data: TaskData) {
        sort.remove(data)
    }

    fun setOnNextClickListener(block: SingleBlock<TaskData>) {
        listenerItem = block
    }

    fun setOnDoneClickListener(block: SingleBlock<TaskData>) {
        listenerDone = block
    }

    fun setOnCancelListener(block: SingleBlock<TaskData>) {
        listenerCancel = block
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun getItemCount(): Int = sort.size()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()


    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.apply {
                binding.next.setOnClickListener { listenerItem?.invoke(sort[adapterPosition]) }
                binding.did.setOnClickListener { listenerDone?.invoke(sort[adapterPosition]) }
                binding.didnt.setOnClickListener { listenerCancel?.invoke(sort[adapterPosition]) }
            }
        }

        fun bind() = bindItem {
            val d = sort[adapterPosition]
            binding.title.text = d.name
            binding.hashtag.text = d.hashtag
           binding.deadline.text = d.dateTime.format(formatter)
                if (d.dateTime.dayOfYear - LocalDate.now().dayOfYear in 0..1) {binding.color.setBackgroundColor(Color.parseColor("#8C1310"))
               binding.next.setColorFilter(Color.parseColor("#8C1310"))}
                if (d.dateTime.dayOfYear - LocalDate.now().dayOfYear in 2..3) {binding.color.setBackgroundColor(Color.parseColor("#E29402"))
                   binding.next.setColorFilter(Color.parseColor("#E29402"))}
                if (3 < d.dateTime.dayOfYear - LocalDate.now().dayOfYear) {binding.color.setBackgroundColor(Color.parseColor("#187F14"))
                    binding.next.setColorFilter(Color.parseColor("#187F14"))}
        }
    }
}


