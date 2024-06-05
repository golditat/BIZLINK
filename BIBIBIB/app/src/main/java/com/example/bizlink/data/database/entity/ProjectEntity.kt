package com.example.bizlink.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "project",
    foreignKeys = [ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["ownerID"], onDelete = ForeignKey.NO_ACTION)]
)
data class ProjectEntity (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")val id:Int,
    @ColumnInfo(name = "projectName")val projectName:String,
    @ColumnInfo(name = "invitingCode")val invitingCode:String,
    @ColumnInfo(name = "ownerID")val ownerID:Int
)