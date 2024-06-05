package com.example.bizlink.domain.model

class UserDomainModel(
    val id:Int,
    val email:String,
    val photo:ByteArray,
    val password:String,
    val repPassword:String
)