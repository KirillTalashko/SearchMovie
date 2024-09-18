package com.example.searchmovie.core.extension

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.example.searchmovie.R

fun TextView.addReadMore(text: String) {
    if (text.length >= 250) {
        val afterText = SpannableString(text.substring(0, 250).plus("...Read more"))
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                addReadLess(text, this@addReadMore)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = resources.getColor(R.color.black)
            }
        }
        afterText.setSpan(
            clickableSpan,
            afterText.length - 10,
            afterText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this.text = afterText
        this.movementMethod = LinkMovementMethod.getInstance()
    } else {
        this.text = text
    }
}

    private fun addReadLess(text: String, textView: TextView) {
    val beforeText = SpannableString(text.plus("...Read less"))
    val clickableSpan: ClickableSpan = object : ClickableSpan() {
        override fun onClick(view: View) {
            textView.addReadMore(text)
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
            ds.setColor(textView.resources.getColor(R.color.black))
        }
    }
    beforeText.setSpan(
        clickableSpan,
        beforeText.length - 10,
        beforeText.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    textView.text = beforeText
    textView.movementMethod = LinkMovementMethod.getInstance()
}
