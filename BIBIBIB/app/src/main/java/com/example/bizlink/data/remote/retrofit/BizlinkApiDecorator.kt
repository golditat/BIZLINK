package com.example.bizlink.data.remote.retrofit

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
import retrofit2.http.Body
import retrofit2.http.Path

class BizlinkApiDecorator(
    private val bizlinkApi: BizlinkApi
) {
    suspend fun addComment(comment: CommentRequest):Result<CommentResponce>{
        return runCatching (){
            bizlinkApi.addComment(comment)
        }
    }
    suspend fun addMaterial( material: MaterialRequest):Result<MaterialResponce>{
        return runCatching {
            bizlinkApi.addMaterial(material)
        }
    }
    suspend fun createProject(project: ProjectRequest): Result<ProjectResponce> {
        return runCatching {
            bizlinkApi.createProject(project)
        }
    }
    suspend fun subscribeProject( code:String,  id:Int): Result<ProjectResponce> {
        return runCatching {
            bizlinkApi.subscribeProject(code, id)
        }
    }
    suspend fun getProjects( id:Int): Result<List<ProjectResponce>> {
        return runCatching {
            bizlinkApi.getProjects(id)
        }
    }
    suspend fun createTask( task: TaskRequest): Result<TaskResponce> {
        return  runCatching {
            bizlinkApi.createTask(task)
        }
    }
    suspend fun subscribeTask( code:String, id:Int): Result<TaskResponce> {
        return runCatching {
            bizlinkApi.subscribeTask(code, id)
        }
    }
    suspend fun getTasks( id:Int): Result<List<TaskResponce>> {
        return runCatching {
            bizlinkApi.getTasks(id)
        }
    }
    suspend fun reg( user: UserRequest): Result<UserResponce> {
        return runCatching {
            bizlinkApi.reg(user)
        }
    }
    suspend fun login(userlogin: UserLoginRequest): Result<JWTResponce> {
        return runCatching {
            bizlinkApi.login(userlogin)
        }
    }
    suspend fun restorePassword(email:String): Result<Unit> {
        return runCatching { bizlinkApi.restorePassword(email) }
    }
}