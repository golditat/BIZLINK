package com.example.bizlink.data.remote.retrofit.req


import java.util.Date

data class TaskRequest (
    val taskName:String,
    val deskription:String,
    val mentorID:Int,
    val projectID:Int,
    val status:Int,
    val start_date:Date,
    val deadline_date:Date,
    val review_date:Date
)