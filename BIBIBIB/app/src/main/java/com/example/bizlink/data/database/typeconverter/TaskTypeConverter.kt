package com.example.bizlink.data.database.typeconverter

import androidx.room.TypeConverter
import com.example.bizlink.ui.entities.Project
import com.example.bizlink.ui.entities.Task
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date

class TaskTypeConverter {
    @TypeConverter
    fun fromTaskToDbEntity(taskData: Task): String {
        val resultStr = StringBuilder().apply {
            this.append(taskData.id)
            this.append(';')
            this.append(taskData.taskName)
            this.append(';')
            this.append(taskData.invitingCode)
            this.append(';')
            this.append(taskData.deskription)
            this.append(';')
            this.append(taskData.mentorID)
            this.append(';')
            this.append(taskData.projectID)
            this.append(';')
            this.append(taskData.status)
            this.append(';')
            this.append(taskData.start_date.time.toLong())
            this.append(';')
            this.append(taskData.deadline_date.time.toLong())
            this.append(';')
            this.append(taskData.review_date.time.toLong())
            this.append(';')
        }
        return resultStr.toString()
    }

    @TypeConverter
    fun fromDbEntityToTaskModel(input: String): Task {
        val index: Int = Integer.parseInt(input.substringBefore(';'))
        val taskName: String = input.substringBefore(';')
        val invitingCode:String = input.substringBefore(';')
        val deskription:String = input.substringBefore(';')
        val mentorId:Int = Integer.parseInt(input.substringBefore(';'))
        val projectId:Int = Integer.parseInt(input.substringBefore(';'))
        val status:Int = Integer.parseInt(input.substringBefore(';'))
        val start_date:Date = input.substringBefore(';').toLong().let { Date(it) }
        val deadline_date:Date = input.substringBefore(';').toLong().let { Date(it) }
        val review_date:Date = input.substringBefore(';').toLong().let { Date(it) }
        return Task(
            id = index,
            taskName = taskName,
            invitingCode = invitingCode,
            deskription = deskription,
            mentorID = mentorId,
            projectID = projectId,
            status = status,
            start_date = start_date,
            deadline_date = deadline_date,
            review_date = review_date,
            comments = ArrayList(),
            materials =ArrayList()
        )
    }

}