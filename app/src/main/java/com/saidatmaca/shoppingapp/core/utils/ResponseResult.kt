package com.saidatmaca.shoppingapp.core.utils


data class ResponseResult<T>(
    val data: List<T>,
    val isError: Boolean,
    val errorText : String = ""
)