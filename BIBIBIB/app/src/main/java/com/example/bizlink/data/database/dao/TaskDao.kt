package com.example.bizlink.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bizlink.data.database.entity.ProjectEntity
import com.example.bizlink.data.database.entity.TaskEntity
import com.example.bizlink.data.database.entity.UserEntity

@Dao
interface TaskDao {
    @Query("SELECT * FROM task WHERE id LIKE :id")
    fun getTaskById(id: Int): TaskEntity?

    @Query("DELETE FROM task WHERE id == :id")
    fun deleteTask(id:Int)

    @Query("DELETE FROM task")
    fun deleteAllTasks()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTask(taskData: TaskEntity)

    @Query("SELECT * FROM task")
    fun getAllTasks(): List<TaskEntity>?

    @Query("SELECT * FROM task WHERE start_date < :current AND :current < deadline_date")
    fun getStartedTasks(current:Long):List<TaskEntity>?

    @Query("SELECT * FROM task WHERE :current = deadline_date")
    fun getDeadlinedTasks(current:Long):List<TaskEntity>?

    @Query("SELECT * FROM task WHERE deadline_date < :current AND :current < review_date")
    fun getOnReviewTasks(current:Long):List<TaskEntity>?
}