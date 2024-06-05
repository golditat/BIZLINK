package com.example.bizlink.ui.entities

import java.util.Date

class Task (
    var id:Int,
    var taskName:String,
    var invitingCode:String,
    var deskription:String,
    var mentorID:Int,
    var projectID:Int,
    var status:Int,
    var start_date: Date,
    var deadline_date: Date,
    var review_date: Date,
    var comments:List<Comment>,
    var  materials:List<Material>
){

}