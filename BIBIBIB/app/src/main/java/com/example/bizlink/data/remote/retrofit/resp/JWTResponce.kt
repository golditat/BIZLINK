package com.example.bizlink.data.remote.retrofit.resp

data class JWTResponce(
  val token:String,
  val refreshToken:String,
  val type:String,
  val id:Int,
  val username:String,
  val roleSet:Set<String>,
  val photo:ByteArray
)
