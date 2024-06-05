package com.example.bizlink.data.remote.retrofit.mapper


import com.example.bizlink.data.remote.retrofit.req.TaskRequest
import com.example.bizlink.data.remote.retrofit.resp.TaskResponce
import com.example.bizlink.domain.model.CommentDomainModel
import com.example.bizlink.domain.model.MaterialDomainModel
import com.example.bizlink.domain.model.TaskDomainModel
import com.example.bizlink.ui.entities.Task

class TaskDomainModelMapper {
    private val materialMapper:MaterialDomainModelMapper = MaterialDomainModelMapper()
    private val commentMapper:CommentDomainModelMapper = CommentDomainModelMapper()
    fun mapResponseToDomainModel(input: TaskResponce): TaskDomainModel {
        with(input) {
            var materials = ArrayList<MaterialDomainModel>()
            materialResponces.forEach {
                materials.add(materialMapper.mapResponseToDomainModel(it))
            }
            var comments = ArrayList<CommentDomainModel>()
            commentResponces.forEach {
                comments.add(commentMapper.mapResponseToDomainModel(it))
            }
            return TaskDomainModel(
                id =  id,
                taskName= taskName,
                invitingCode= invitingCode,
                deskription = deskription,
                mentorID = mentorID,
                projectID = projectID,
                status = status,
                start_date=  start_date,
                deadline_date = deadline_date,
                review_date= review_date ,
                comments = comments ,
                materials = materials
            )
        }
    }
    fun mapDomainModelToRequest(input: TaskDomainModel): TaskRequest {
        with(input) {
            return TaskRequest(
                taskName= taskName,
                deskription = deskription,
                mentorID = mentorID,
                projectID = projectID,
                status = status,
                start_date=  start_date,
                deadline_date = deadline_date,
                review_date= review_date ,
            )
        }
    }
}