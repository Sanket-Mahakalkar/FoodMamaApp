package com.example.foodmama.api

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */

data class Resource<T>(
    var status: Status,
    var data: T? = null,
    var errorMessage: String? = null) {


    companion object{

        /**
         * Creates [Resource] object with `SUCCESS` status and [data].
         * Returning object of Resource(Status.SUCCESS, data, null)
         * last value is null so passing it optionally
         *
         */
        fun <T> success(data: T): Resource<T> = Resource(Status.SUCCESS,data)

        /**
         * Creates [Resource] object with `LOADING` status to notify
         * the UI to showing loading.
         * Returning object of Resource(Status.LOADING, null, null)
         * last two values are null so passing them optionally
         */
        fun <T> loading(): Resource<T> = Resource(Status.LOADING)

        /**
         * Creates [Resource] object with `ERROR` status and [message].
         * Returning object of Resource(Status.ERROR, errorMessage = message)
         */
        fun <T> error(message:String): Resource<T> = Resource(Status.ERROR, errorMessage = message)
    }


    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}
