package com.example.bizlink.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "material",
    foreignKeys = [ForeignKey(entity = TaskEntity::class, parentColumns = ["id"], childColumns = ["taskID"], onDelete = ForeignKey.NO_ACTION)]
)
data class MaterialEntity (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")val id:Int,
    @ColumnInfo(name = "materialName")val materialName:String,
    @ColumnInfo(name = "taskID")val taskID:Int,
    @ColumnInfo(name = "file")val file:ByteArray
)