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
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BizlinkApi {
    @POST("/commentResponce/add/comment/")
    suspend fun addComment(@Body comment: CommentRequest):CommentResponce

    @POST("/materialResponce/add/material/")
    suspend fun addMaterial(@Body material: MaterialRequest):MaterialResponce

    @POST("/projectResponce/add/project/")
    suspend fun createProject(@Body project: ProjectRequest): ProjectResponce

    @POST("/project/subscribe/project/{code, uuid}")
    suspend fun subscribeProject(@Path("code") code:String, @Path("uuid") id:Int): ProjectResponce

    @GET("/project/projects/{uuid}")
    suspend fun getProjects(@Path("uuid") id:Int):List<ProjectResponce>

    @POST("/taskResponce/add/task/")
    suspend fun createTask(@Body task: TaskRequest): TaskResponce

    @PUT("/task/subscribe/task/{code, uuid}")
    suspend fun subscribeTask(@Path("code") code:String, @Path("uuid") id:Int): TaskResponce

    @GET("/task/tasks/{uuid}")
    suspend fun getTasks(@Path("uuid") id:Int):List<TaskResponce>

    @POST("/userResponce/register/")
    suspend fun reg(@Body user: UserRequest): UserResponce

    @POST("/userResponce/login/")
    suspend fun login(@Body userlogin: UserLoginRequest): JWTResponce

    @GET("/user/restore-password/{email}")
    suspend fun restorePassword(@Path("email") email:String)
}