package com.example.bizlink.data.database

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Database
import androidx.room.TypeConverter
import com.example.bizlink.data.database.dao.CommentDao
import com.example.bizlink.data.database.dao.MaterialDao
import com.example.bizlink.data.database.dao.ProjectDao
import com.example.bizlink.data.database.dao.TaskDao
import com.example.bizlink.data.database.dao.UserDao
import com.example.bizlink.data.database.entity.CommentEntity
import com.example.bizlink.data.database.entity.MaterialEntity
import com.example.bizlink.data.database.entity.ProjectEntity
import com.example.bizlink.data.database.entity.TaskEntity
import com.example.bizlink.data.database.entity.UserEntity
import com.example.bizlink.data.database.typeconverter.CommentTypeConverter
import com.example.bizlink.data.database.typeconverter.MaterialTypeConverter
import com.example.bizlink.data.database.typeconverter.ProjectTypeConverter
import com.example.bizlink.data.database.typeconverter.TaskTypeConverter
import com.example.bizlink.data.database.typeconverter.UserTypeConverter

@Database(
    entities = [UserEntity::class, TaskEntity::class, ProjectEntity::class, MaterialEntity::class, CommentEntity::class],
    version = 1
)
@TypeConverters(UserTypeConverter::class , ProjectTypeConverter::class, TaskTypeConverter::class, MaterialTypeConverter::class, CommentTypeConverter::class )
abstract class Database : RoomDatabase(){

    abstract val userDao:UserDao
    abstract val taskDao:TaskDao
    abstract val projectDao:ProjectDao
    abstract val materialDao: MaterialDao
    abstract val commentDao:CommentDao

}