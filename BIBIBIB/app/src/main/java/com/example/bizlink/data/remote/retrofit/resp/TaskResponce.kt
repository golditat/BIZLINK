package com.example.bizlink.data.remote.retrofit.resp

import java.util.Date

data class TaskResponce(
    val id:Int,
    val taskName:String,
    val invitingCode:String,
    val deskription:String,
    val mentorID:Int,
    val projectID:Int,
    val status:Int,
    val start_date: Date,
    val deadline_date: Date,
    val review_date: Date,
    val commentResponces:List<CommentResponce>,
    val  materialResponces:List<MaterialResponce>
)
