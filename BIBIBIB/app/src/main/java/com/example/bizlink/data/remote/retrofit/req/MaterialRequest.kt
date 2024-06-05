package com.example.bizlink.data.remote.retrofit.req

data class MaterialRequest (
    val materialName:String,
    val taskID:Int,
    val file:ByteArray
)