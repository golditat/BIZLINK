package com.example.bizlink.ui.entities

import java.util.Date
import java.util.TimeZone

class Comment(
    var id:Int,
    var content:String,
    var date: Date,
    var ownerID:Int,
    var taskID:Int
) {
}