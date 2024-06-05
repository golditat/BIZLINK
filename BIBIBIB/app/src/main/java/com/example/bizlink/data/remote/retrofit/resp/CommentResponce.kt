package com.example.bizlink.data.remote.retrofit.resp

import java.util.Date

data class CommentResponce(
    val id:Int,
    val content:String,
    val date: Date,
    val ownerID:Int,
    val taskID:Int
)
