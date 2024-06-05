package com.example.bizlink.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(
    tableName = "comment",
    foreignKeys = [ForeignKey(entity = TaskEntity::class, parentColumns = ["id"], childColumns = ["taskID"], onDelete = ForeignKey.NO_ACTION),
        ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["ownerID"], onDelete = ForeignKey.NO_ACTION)]
)
data class CommentEntity (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")val id:Int,
    @ColumnInfo(name = "content")val content:String,
    @ColumnInfo(name = "date")val date: Long,
    @ColumnInfo(name = "ownerID")val ownerID:Int,
    @ColumnInfo(name = "taskID")val taskID:Int
)