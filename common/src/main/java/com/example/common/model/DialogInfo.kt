package com.example.common.model

data class DialogInfo(
    val title: String? = null,
    val description: String,
    val actionPositiveFirst: (() -> Unit)? = null,
    val actionPositiveSecond: (() -> Unit)? = null,
    val nameActionPositive: String? = null,
    val actionNegative: (() -> Unit)? = null,
    val nameActionNegative: String? = null,
)
