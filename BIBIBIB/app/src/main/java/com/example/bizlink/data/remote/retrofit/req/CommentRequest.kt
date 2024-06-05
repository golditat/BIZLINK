package com.example.bizlink.data.remote.retrofit.req

import java.util.Date

data class CommentRequest (
    val content:String,
    val ownerID:Int,
    val date:Date,
    val taskID:Int
)