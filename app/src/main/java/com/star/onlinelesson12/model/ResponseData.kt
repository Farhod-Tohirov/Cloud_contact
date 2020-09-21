package com.star.onlinelesson11.model

data class ResponseData<T>(
    val status: String,
    val message: String = "Successful",
    val data: T? = null
)