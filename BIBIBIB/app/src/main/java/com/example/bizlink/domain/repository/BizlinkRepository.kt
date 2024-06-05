package com.example.bizlink.domain.repository

import com.example.bizlink.data.database.entity.TaskEntity
import com.example.bizlink.data.remote.retrofit.req.CommentRequest
import com.example.bizlink.data.remote.retrofit.req.MaterialRequest
import com.example.bizlink.data.remote.retrofit.req.ProjectRequest
import com.example.bizlink.data.remote.retrofit.req.TaskRequest
import com.example.bizlink.data.remote.retrofit.req.UserLoginRequest
import com.example.bizlink.data.remote.retrofit.req.UserRequest
import com.example.bizlink.data.remote.retrofit.resp.CommentResponce
import com.example.bizlink.data.remote.retrofit.resp.JWTResponce
import com.example.bizlink.data.remote.retrofit.resp.MaterialResponce
import com.example.bizlink.data.remote.retrofit.resp.ProjectResponce
import com.example.bizlink.data.remote.retrofit.resp.TaskResponce
import com.example.bizlink.data.remote.retrofit.resp.UserResponce
import com.example.bizlink.ui.entities.Comment
import com.example.bizlink.ui.entities.Material
import com.example.bizlink.ui.entities.TaskItemModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BizlinkRepository {
    suspend fun addComment( comment: CommentRequest):CommentResponce
    suspend fun addMaterial( material: MaterialRequest):MaterialResponce
    suspend fun createProject(project: ProjectRequest): ProjectResponce
    suspend fun subscribeProject(code:String, id:Int): ProjectResponce
    suspend fun getProjects(id:Int):List<ProjectResponce>
    suspend fun createTask(task: TaskRequest): TaskResponce
    suspend fun subscribeTask(code:String, id:Int): TaskResponce
    suspend fun getTasks( id:Int):List<TaskResponce>
    suspend fun reg(user: UserRequest): UserResponce
    suspend fun login(userlogin: UserLoginRequest): JWTResponce
    suspend fun getStartedTasks(current:Long):List<TaskEntity>
    suspend fun getDeadlinedTasks(current:Long):List<TaskEntity>
    suspend fun getOnReviewTasks(current:Long):List<TaskEntity>

}