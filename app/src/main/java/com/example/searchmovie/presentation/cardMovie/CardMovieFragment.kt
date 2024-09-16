package com.example.searchmovie.presentation.cardMovie

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.searchmovie.R
import com.example.searchmovie.databinding.FragmentCardMovieBinding
import com.example.searchmovie.databinding.InformationMovieBinding

class CardMovieFragment : Fragment() {
    //private var _binding: FragmentCardMovieBinding? = null
    private var _binding: InformationMovieBinding? = null
    private val binding
        get() = _binding!!
    val text = "Rey (Daisy Ridley) finally manages to find the legendary Jedi knight, Luke Skywalker (Mark Hamill) on an island with a magical aura. The heroes of The Force Awakens including Leia, Finn Read more.."

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = InformationMovieBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addReadMore(text, binding.descriptionMovie)
    }

        private fun addReadMore(text: String, textView: TextView) {
            val ss = if (text.length >= 270) {
                SpannableString(text.substring(0, 270).plus("Read more"))
            } else SpannableString(text.plus("Read more"))
            val clickableSpan: ClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    addReadLess(text,textView)
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
    }