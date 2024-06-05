package com.example.bizlink.domain.model

import com.example.bizlink.data.remote.retrofit.resp.CommentResponce
import com.example.bizlink.data.remote.retrofit.resp.MaterialResponce
import java.util.Date

class TaskDomainModel (
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
    val comments:List<CommentDomainModel>,
    val  materials:List<MaterialDomainModel>
)