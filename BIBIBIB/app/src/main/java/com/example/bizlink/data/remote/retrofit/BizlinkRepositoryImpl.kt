package com.example.bizlink.data.remote.retrofit

import com.example.bizlink.data.database.dao.CommentDao
import com.example.bizlink.data.database.dao.MaterialDao
import com.example.bizlink.data.database.dao.ProjectDao
import com.example.bizlink.data.database.dao.TaskDao
import com.example.bizlink.data.database.dao.UserDao
import com.example.bizlink.data.database.entity.CommentEntity
import com.example.bizlink.data.database.entity.MaterialEntity
import com.example.bizlink.data.database.entity.ProjectEntity
import com.example.bizlink.data.database.entity.TaskEntity
import com.example.bizlink.data.remote.retrofit.exeption.EmptyResponceExeption
import com.example.bizlink.data.remote.retrofit.exeption.NotCreatedInstanceExeption
import com.example.bizlink.data.remote.retrofit.exeption.RegistrationExeption
import com.example.bizlink.data.remote.retrofit.exeption.SubscribeExeption
import com.example.bizlink.data.remote.retrofit.exeption.UserNotAuthorizedException
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
import com.example.bizlink.di.ServiceLocator
import com.example.bizlink.domain.repository.BizlinkRepository

class BizlinkRepositoryImpl(
    val userDao: UserDao,
    val taskDao: TaskDao,
    val projectDao: ProjectDao,
    val materialDao: MaterialDao,
    val commentDao: CommentDao,
    val api: BizlinkApi
): BizlinkRepository {
    override suspend fun addComment(comment: CommentRequest): CommentResponce {
        var input = api.addComment(comment)
        ServiceLocator.commentDao.addComment(
            CommentEntity(
            id = input.id,
            content = input.content,
            date = input.date.time,
            ownerID = input.ownerID,
            taskID = input.taskID
        )
        )
        return input
    }

    override suspend fun addMaterial(material: MaterialRequest): MaterialResponce {
        var input = api.addMaterial(material)
        ServiceLocator.materialDao.addMaterial(
            MaterialEntity(
            id = input.id,
            materialName =  input.materialName,
            taskID = input.taskID,
            file = input.file
        )
        )
        return input
    }

    override suspend fun createProject(project: ProjectRequest): ProjectResponce {
        var input = api.createProject(project)
        ServiceLocator.projectDao.addProject(
            ProjectEntity(
            id = input.id,
            ownerID = input.ownerID,
            projectName = input.projectName,
            invitingCode = input.invitingCode
        )
        )
        return if(input != null){
            input
        }else{
            throw NotCreatedInstanceExeption(message = "problem in project creating")
        }
    }

    override suspend fun subscribeProject(code: String, id: Int): ProjectResponce {
        var input = api.subscribeProject(code, id)
        return if(input!= null){
            ServiceLocator.projectDao.addProject(ProjectEntity(
                id = input.id,
                projectName = input.projectName,
                invitingCode = input.invitingCode,
                ownerID = input.ownerID
            ))
            input
        }else{
            throw SubscribeExeption(message = "subscribing project error")
        }
    }

    override suspend fun getProjects(id: Int): List<ProjectResponce> {
        var input = api.getProjects(id)
        return if(input!= null && !input.isEmpty()){
            input.forEach {
                ServiceLocator.projectDao.addProject(ProjectEntity(
                    id = it.id,
                    projectName = it.projectName,
                    invitingCode = it.invitingCode,
                    ownerID = it.ownerID
                ))
            }
            input
        }else{
            throw EmptyResponceExeption(message = "empty responce in user projects")
        }
    }

    override suspend fun createTask(task: TaskRequest): TaskResponce {
        var input = api.createTask(task)
        return if(input!= null){
            ServiceLocator.taskDao.addTask(TaskEntity(
                id = input.id,
                taskName = input.taskName,
                invitingCode = input.invitingCode,
                deskription = input.deskription,
                mentorID = input.mentorID,
                projectID = input.projectID,
                status = input.status,
                start_date = input.start_date.time,
                deadline_date = input.deadline_date.time,
                review_date = input.review_date.time
            ))
            input
        }else{
            throw SubscribeExeption(message = "subscribing project error")
        }
    }

    override suspend fun subscribeTask(code: String, id: Int): TaskResponce {
        var input = api.subscribeTask(code, id)
        return if(input!= null){
            ServiceLocator.taskDao.addTask(
                TaskEntity(
                    id = input.id,
                    taskName = input.taskName,
                    invitingCode = input.invitingCode,
                    deskription = input.deskription,
                    mentorID = input.mentorID,
                    projectID = input.projectID,
                    status = input.status,
                    start_date = input.start_date.time,
                    deadline_date = input.deadline_date.time,
                    review_date = input.review_date.time
                )
            )
            input
        }else{
            throw SubscribeExeption(message = "subscribing task error")
        }
    }

    override suspend fun getTasks(id: Int): List<TaskResponce> {
        var input = api.getTasks(id)
        return if(input!= null && !input.isEmpty()){
            input.forEach {
                ServiceLocator.taskDao.addTask(TaskEntity(
                    id = it.id,
                    taskName = it.taskName,
                    invitingCode = it.invitingCode,
                    deskription = it.deskription,
                    mentorID = it.mentorID,
                    projectID = it.projectID,
                    status = it.status,
                    start_date = it.start_date.time,
                    deadline_date = it.deadline_date.time,
                    review_date = it.review_date.time
                ))
            }
            input
        }else{
            throw EmptyResponceExeption(message = "empty responce in user tasks")
        }
    }

    override suspend fun reg(user: UserRequest): UserResponce {
        var input = api.reg(user)
        return if(input != null){
            input
        }else{
            throw RegistrationExeption(message = "user not registred")
        }
    }

    override suspend fun login(userlogin: UserLoginRequest): JWTResponce {
        var input = api.login(userlogin)
        return if(input != null){
           input
        }else{
            throw UserNotAuthorizedException(message = "not autorised")
        }
    }

    override suspend fun getStartedTasks(current: Long): List<TaskEntity> {
        val input = ServiceLocator.taskDao.getStartedTasks(current)
        return if(input != null){
            input
        }else{
            emptyList<TaskEntity>()
        }
    }

    override suspend fun getDeadlinedTasks(current: Long): List<TaskEntity> {
        val input = ServiceLocator.taskDao.getDeadlinedTasks(current)
        return if(input != null){
            input
        }else{
            emptyList<TaskEntity>()
        }
    }

    override suspend fun getOnReviewTasks(current: Long): List<TaskEntity> {
        val input = ServiceLocator.taskDao.getOnReviewTasks(current)
        return if(input != null){
            input
        }else{
            emptyList<TaskEntity>()
        }
    }

}