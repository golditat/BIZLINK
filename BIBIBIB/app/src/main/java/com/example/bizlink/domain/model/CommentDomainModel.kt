package com.example.bizlink.domain.model

import java.util.Date

class CommentDomainModel (
    val id:Int,
    val content:String,
    val date: Date,
    val ownerID:Int,
    val taskID:Int
)
