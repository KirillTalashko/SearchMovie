package com.example.searchmovie.presentation.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private var initialY = 0f

    private fun isAtTop() = !canScrollVertically(-1)
    private fun isAtBottom() = !canScrollVertically(1)

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                initialY = event.y
                parent.requestDisallowInterceptTouchEvent(true)
            }

            MotionEvent.ACTION_MOVE -> {
                val deltaY = event.y - initialY

                when {
                    isAtTop() && deltaY > 0 -> {
                        parent.requestDisallowInterceptTouchEvent(false)
                        return false
                    }

                    isAtBottom() && deltaY < 0 -> {
                        parent.requestDisallowInterceptTouchEvent(false)
                        return false
                    }

                    else -> {
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                }
            }

            MotionEvent.ACTION_UP -> performClick()
        }
        return super.onInterceptTouchEvent(event)
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}
