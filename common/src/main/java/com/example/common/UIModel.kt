package com.example.common

sealed class UIModel<out T : Any> {
    class Data<out T : Any>(val data: T) : UIModel<T>()
    object Loading : UIModel<Nothing>()
}

fun <T : Any> UIModel<T>.dataOrNull(): T? {
    return when (this) {
        is UIModel.Data -> data
        UIModel.Loading -> null
    }
}
