package com.example.bizlink.data.remote.retrofit.exeption

import retrofit2.HttpException

class ExaptionHandlerDelegate {
    fun handleException(ex: Throwable): Throwable {
        return when (ex) {
            is HttpException -> {
                when (ex.code()) {
                    401 -> {
                        UserNotAuthorizedException(message = "not autorised")
                    }

                    403 -> {
                        ex
                    }

                    429 -> {
                        EmptyResponceExeption(message = "empty responce")
                    }

                    else -> {
                        ex
                    }
                }
            }

            else -> {
                ex
            }
        }
    }
}