package com.example.searchmovie

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CenterZoomLayoutManager(context: Context) : LinearLayoutManager(
    context,
    HORIZONTAL, false
) {
    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        if (orientation == HORIZONTAL) {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                child?.scaleX = 2.0f
                child?.scaleY = 2.0f
            }
            return scrolled
        } else {
            return 0
        }
    }
}