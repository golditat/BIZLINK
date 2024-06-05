package com.example.bizlink.data.database.typeconverter

import androidx.room.TypeConverter
import com.example.bizlink.ui.entities.Comment
import com.example.bizlink.ui.entities.Material
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date

class CommentTypeConverter {
    @TypeConverter
    fun fromCommentToDbEntity(commentData: Comment): String {
        val resultStr = StringBuilder().apply {
            this.append(commentData.id)
            this.append(';')
            this.append(commentData.content)
            this.append(';')
            this.append(commentData.taskID)
            this.append(';')
            this.append(commentData.ownerID)
            this.append(';')
            this.append(commentData.date.time.toLong())
            this.append(';')
        }
        return resultStr.toString()
    }

    @TypeConverter
    fun fromDbEntityToCommentModel(input: String): Comment {
        val index: Int = Integer.parseInt(input.substringBefore(';'))
        val content: String = input.substringBefore(';')
        val taskId:Int = Integer.parseInt(input.substringBefore(';'))
        val ownerId:Int = Integer.parseInt(input.substringBefore(';'))
        val date: Date = input.substringBefore(';').toLong().let { Date(it) }
        return Comment(
            id = index,
            content = content,
            taskID = taskId,
            ownerID = ownerId,
            date = date
        )
    }
}