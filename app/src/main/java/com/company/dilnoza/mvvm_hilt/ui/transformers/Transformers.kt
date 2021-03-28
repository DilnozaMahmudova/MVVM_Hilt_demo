package com.company.dilnoza.mvvm_hilt.ui.transformers

import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class Transformers:ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.translationX = -position * page.width
        page.cameraDistance=12000F

        if (position < 0.5 && position > -0.5) {
            page.visibility = VISIBLE
        } else {
            page.visibility = INVISIBLE
        }


        when {
            position < -1 -> {     // [-Infinity,-1)
                page.alpha=0f

            }
            position <= 0 -> {    // [-1,0]
                page.alpha=1f
                page.rotationY = 180 * (1 - abs(position) + 1)

            }
            position <= 1 -> {    // (0,1]
                page.alpha=1f
                page.rotationY = -180 * (1 - abs(position) + 1)

            }
            else -> {
                page.alpha=0f
            }
        }
    }
}