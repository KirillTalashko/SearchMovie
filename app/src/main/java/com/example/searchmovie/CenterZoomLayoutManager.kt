package com.example.searchmovie

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.utils.log
import java.lang.Math.abs

class CenterZoomLayoutManager(context: Context) : LinearLayoutManager(
    context,
    HORIZONTAL, false
) {
    private val shrinkAmount = 0.15f
    private val shrinkDirection = 0.9f
    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        if (orientation == HORIZONTAL) {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            scaleChildren()
            return scrolled
        } else {
            return 0
        }
    }

    private fun scaleChildren() {
        val midpoint = width / 2
        val endDistance = shrinkDirection * midpoint
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childMidpoint = child?.let { getDecoratedLeft(it) + getDecoratedRight(it) / 2f }
            val distanceRelativeEdge = endDistance.coerceAtMost(abs(midpoint - childMidpoint!!))
            val scale = 1f - (shrinkAmount * distanceRelativeEdge) / endDistance
            child.scaleX = scale
            child.scaleY = scale
            "scale $scale, $width".log()
            "endDistance $endDistance , childMidpoint $childMidpoint, distanceRelativeEdge $distanceRelativeEdge ".log()
        }
    }
}
