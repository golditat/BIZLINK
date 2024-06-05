package com.example.bizlink.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(
    tableName = "task",
    foreignKeys = [ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["mentorID"], onDelete = ForeignKey.NO_ACTION),
        ForeignKey(entity = ProjectEntity::class, parentColumns = ["id"], childColumns = ["projectID"], onDelete = ForeignKey.NO_ACTION)]
)
data class TaskEntity (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")val id:Int,
    @ColumnInfo(name = "taskName")val taskName:String,
    @ColumnInfo(name = "invitingCode")val invitingCode:String,
    @ColumnInfo(name = "deskription")val deskription:String,
    @ColumnInfo(name = "mentorID")val mentorID:Int,
    @ColumnInfo(name = "projectID")val projectID:Int,
    @ColumnInfo(name = "status")val status:Int,
    @ColumnInfo(name = "start_date")val start_date: Long,
    @ColumnInfo(name = "deadline_date")val deadline_date: Long,
    @ColumnInfo(name = "review_date")val review_date: Long
)