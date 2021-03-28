package com.company.dilnoza.mvvm_hilt.ui.transformers

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kotlin.math.abs
import kotlin.properties.Delegates


class Horizontal(context: Context?, orientation: Int, reverseLayout: Boolean) : LinearLayoutManager(
    context,
    orientation,
    reverseLayout
) {
    private var childMidPoint by Delegates.notNull<Float>()
    private val mShrinkAmount = 0.15f
    private val mShrinkDistance = 0.9f

    override fun scrollVerticallyBy(dy: Int, recycler: Recycler, state: RecyclerView.State): Int {
        val orientation = orientation
        return if (orientation == VERTICAL) {
            val scrolled = super.scrollVerticallyBy(dy, recycler, state)
            val midpoint = height / 2f
            val d0 = 0f
            val d1 = mShrinkDistance * midpoint
            val s0 = 1f
            val s1 = 1f - mShrinkAmount
            for (i in 0 until childCount) {
                val child: View? = getChildAt(i)
                if (child != null) {
                    childMidPoint = if (findFirstVisibleItemPosition() == 0 && i == 0) {
                        getDecoratedRight(child) - child.width / 2f
                    } else {
                        getDecoratedLeft(child) + child.width / 2f
                    }
                }
                val d = d1.coerceAtMost(abs(midpoint - childMidPoint))
                val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                if (child != null) {
                    child.scaleX = scale
                }
                if (child != null) {
                    child.scaleY = scale
                }
            }
            scrolled
        } else {
            0
        }
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: Recycler, state: RecyclerView.State): Int {
        val orientation = orientation
        return if (orientation == HORIZONTAL) {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            val midpoint = width / 2f
            val d0 = 0f
            val d1 = mShrinkDistance * midpoint
            val s0 = 1f
            val s1 = 1f - mShrinkAmount
            for (i in 0 until childCount) {
                val child: View? = getChildAt(i)
                val childMidpoint = (child?.let { getDecoratedRight(it) }!! + child?.let {
                    getDecoratedLeft(
                        it
                    )
                }!!) / 2f
                val d = d1.coerceAtMost(abs(midpoint - childMidpoint))
                val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                child.scaleX = scale
                child.scaleY = scale
            }
            scrolled
        } else {
            0
        }
    }
}