package com.company.dilnoza.mvvm_hilt.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.company.dilnoza.mvvm_hilt.R
import com.company.dilnoza.mvvm_hilt.databinding.ItemEditBinding
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData
import org.threeten.bp.format.DateTimeFormatter
import uz.dilnoza.coursework.utils.extentions.SingleBlock


class BasketAdapter:androidx.recyclerview.widget.ListAdapter<TaskData, BasketAdapter.ViewHolder>(TaskData.ITEM_CALLBACK) {
    private lateinit var binding: ItemEditBinding
    private var listenerItem:SingleBlock<TaskData>?=null
    private var listenerDelete:SingleBlock<TaskData>?=null
    private var listenerRestore:SingleBlock<TaskData>?=null
    private val formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    override fun onCreateViewHolder(parent:ViewGroup, viewType: Int): ViewHolder {
        binding = ItemEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)=holder.bind()

 fun setOnItemClickListener(block: SingleBlock<TaskData>){
     listenerItem=block
 }
    fun setOnItemInsertListener(block: SingleBlock<TaskData>){
        listenerRestore=block
    }
    fun setOnItemDeleteListener(block: SingleBlock<TaskData>){
        listenerDelete=block
    }
    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.apply {
                setOnClickListener { listenerItem?.invoke(getItem(adapterPosition)) }
                binding.menuMore.setOnClickListener { view1 ->
                    val menu=PopupMenu(context,view1)
                    menu.inflate(R.menu.menu_more)
                    menu.setOnMenuItemClickListener {
                        when(it.itemId){
                            R.id.menuInsert->listenerRestore?.invoke(getItem(adapterPosition))
                            R.id.menuDelete->listenerDelete?.invoke(getItem(adapterPosition))
                        }
                        true
                    }
                    menu.show()
                }
            }
        }

            fun bind() {
            val d=getItem(adapterPosition)
           binding.title.text=d.name
         binding.hashtag.text=d.hashtag
         binding.deadline.text=d.dateTime.format(formatter)

            }
    }
}
