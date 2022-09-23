package com.example.masteringpagingthree_2.data.model // ktlint-disable package-name

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String
) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(
            Status.SUCCESS,
            data,
            "Success"
        )

        fun <T> error(data: T?): Resource<T> = Resource(
            Status.ERROR,
            null,
            "Failed"
        )

        fun <T> loading(data: T?): Resource<T> = Resource(
            Status.LOADING,
            null,
            "Loading"
        )
    }
}
