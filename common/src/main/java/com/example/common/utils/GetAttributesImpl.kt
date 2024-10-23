package com.example.common.utils

import android.content.Context

class GetAttributesImpl(private val context: Context) : GetAttributes {
    override fun getAttrs(item: Int): String {
        return context.getString(item)
    }
}