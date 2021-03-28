package com.company.dilnoza.mvvm_hilt.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.company.dilnoza.mvvm_hilt.R
import com.company.dilnoza.mvvm_hilt.databinding.IntoPageBinding

class IntoFragment:Fragment(R.layout.into_page) {
private lateinit var binding:IntoPageBinding
    private var listenerNext: ((Int)->Unit)?=null
    private var listenerBack: ((Int) -> Unit)? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle=requireArguments()
        binding= IntoPageBinding.bind(view)
        val image=bundle.getInt("IMAGE")
        binding.info.text=bundle.getString("TEXT")
      binding.page.setBackgroundResource(image)
        binding.back.setOnClickListener {
            listenerBack?.invoke(image)
        }
       binding.next.setOnClickListener {
            listenerNext?.invoke(image)
        }
    }
    fun setNext(block: (Int) -> Unit) {
        listenerNext = block
    }

    fun setBack(block: (Int) -> Unit) {
        listenerBack = block
    }
}