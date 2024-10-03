package com.example.searchmovie.core.utils

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

object TextExpander {

    private const val MAX_LENGTH = 250
    fun getReadMoreText(
        fullText: String,
        textColor: Int,
        isUnderline: Boolean = false,
        onExpand: () -> Unit,
        textReadMore: String
    ): SpannableString {
        if (fullText.length >= MAX_LENGTH) {
            val afterText = SpannableString(fullText.substring(0, 250).plus(textReadMore))
            val clickableSpan: ClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    onExpand()
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = isUnderline
                    ds.color = textColor

                }
            }
            afterText.setSpan(
                clickableSpan,
                afterText.length - 9,
                afterText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return afterText
        } else return SpannableString(fullText)
    }

    fun getReadLessText(
        fullText: String,
        textColor: Int,
        isUnderline: Boolean = false,
        onCollapse: () -> Unit,
        textLessMore: String
    ): SpannableString {
        val beforeText = SpannableString(fullText.plus(textLessMore))
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                onCollapse()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = isUnderline
                ds.color = textColor
            }
        }
        beforeText.setSpan(
            clickableSpan,
            beforeText.length - 9,
            beforeText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return beforeText
    }
}