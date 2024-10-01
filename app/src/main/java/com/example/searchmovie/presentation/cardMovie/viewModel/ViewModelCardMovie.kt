package com.example.searchmovie.presentation.cardMovie.viewModel


import androidx.lifecycle.ViewModel
import com.example.searchmovie.domain.repositopy.MovieRepository

class ViewModelCardMovie(private val repository: MovieRepository) : ViewModel() {
/*
    private var _trackingReadMore = MutableLiveData(false)

    val trackingReadMore: LiveData<Boolean>
        get() = _trackingReadMore

    private fun addReadMore(text: String, textView: TextView) {
        if (text.length >= 250) {
            _trackingReadMore.value = true
            val afterText = SpannableString(text.substring(0, 250).plus("...Read more"))
            val clickableSpan: ClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    addReadLess(text, textView)
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                    ds.color = textView.resources.getColor(R.color.black)
                }
            }
            afterText.setSpan(
                clickableSpan,
                afterText.length - 10,
                afterText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            textView.text = afterText
            textView.movementMethod = LinkMovementMethod.getInstance()
        } else {
            textView.text = text
        }
    }

    private fun addReadLess(text: String, textView: TextView) {
        _trackingReadMore .value = false
        val beforeText = SpannableString(text.plus("...Read less"))
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                addReadMore(text,textView)
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

    fun getAddReadMore(text: String, textView: TextView){
        addReadMore(text, textView)
    }*/
}