package com.example.bizlink.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bizlink.data.database.entity.ProjectEntity
import com.example.bizlink.data.database.entity.UserEntity

@Dao
interface ProjectDao {
    @Query("SELECT * FROM project WHERE id LIKE :id")
    fun getProjectById(id:Int): ProjectEntity?

    @Query("DELETE FROM project WHERE id == :id")
    fun deleteProject(id:Int)

    @Query("DELETE FROM project")
    fun deleteAllProject()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProject(projectData: ProjectEntity)

    @Query("SELECT * FROM project")
    fun getAllProjects(): List<ProjectEntity>?

}