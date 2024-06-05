package com.example.bizlink.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user",
    indices = [Index(value = ["email"],
        unique = true)]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id:Int = 0,
    @ColumnInfo(name="email") var email:String,
    @ColumnInfo(name="password") var password:String,
    @ColumnInfo(name = "photo") var photo:ByteArray
)