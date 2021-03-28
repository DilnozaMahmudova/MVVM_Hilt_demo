package com.company.dilnoza.mvvm_hilt.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.dilnoza.mvvm_hilt.databinding.PicturePageBinding
import uz.dilnoza.coursework.utils.extentions.bindItem

class PictureRecycler(
    private val ls: List<Int>
) :
    RecyclerView.Adapter<PictureRecycler.ViewHolder>() {
    private lateinit var binding:PicturePageBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :ViewHolder{
        binding= PicturePageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }

    override fun getItemCount() = ls.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {


        fun bind() = bindItem {
            val d = ls[adapterPosition]
            binding.image.setImageResource(d)
        }
    }
}
