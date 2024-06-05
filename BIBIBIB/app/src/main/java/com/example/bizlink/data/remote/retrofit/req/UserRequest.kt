package com.example.bizlink.data.remote.retrofit.req

data class UserRequest (
    val email:String,
    val password:String,
    val repeatPassword:String,
    val photo:ByteArray
)