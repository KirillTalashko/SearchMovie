package com.example.searchmovie.presentation.customView

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.example.searchmovie.R

class DescriptionReadMore @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var textDirection: TextView

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.text_description_read_more, FrameLayout(context), true)
        textDirection = findViewById(R.id.read_more)
    }

    private fun addReadMore(text: String,textView: TextView) {
        val ss = if (text.length >= 270) {
            SpannableString(text.substring(0, 270).plus("Read more"))
        }else SpannableString (text.plus("Read more"))
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                addReadLess(text,textView)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.setColor(resources.getColor(R.color.gray_text))
            }
        }
        ss.setSpan(clickableSpan, ss.length - 10, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun addReadLess(text: String,textView: TextView) {
        val ss = SpannableString("$text Read less")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                addReadMore(text,textView)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.setColor(resources.getColor(R.color.black))
            }
        }
        ss.setSpan(clickableSpan, ss.length - 10, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    fun setText(text: String,textView: TextView){
        return addReadMore(text,textView)
    }
    fun getTextView():TextView{
        return textDirection
    }
}