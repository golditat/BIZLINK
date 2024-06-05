package com.example.bizlink.ui.entities

class JWTLogin(
    val token:String,
    val refreshToken:String,
    val type:String,
    val id:Int,
    val username:String,
    val roleSet:Set<String>,
    val photo:ByteArray
) {
}