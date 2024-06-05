package com.example.bizlink.domain.mapper

import com.example.bizlink.domain.model.CommentDomainModel
import com.example.bizlink.domain.model.MaterialDomainModel
import com.example.bizlink.domain.model.TaskDomainModel
import com.example.bizlink.ui.entities.Comment
import com.example.bizlink.ui.entities.Material
import com.example.bizlink.ui.entities.Task

class TaskUIModelMapper {
    private val commentMapper:CommentUIModelMapper = CommentUIModelMapper()
    private val materialMapper:MaterialUIModelMapper = MaterialUIModelMapper()
    fun mapUIToDomainModel(input: Task):TaskDomainModel{
        with(input){
            return TaskDomainModel(
                id = id,
                taskName = taskName,
                invitingCode = invitingCode,
                deskription = deskription,
                mentorID =  mentorID,
                projectID = projectID,
                status = status,
                start_date = start_date,
                deadline_date = deadline_date,
                review_date = review_date,
                comments =ArrayList<CommentDomainModel>(),
                materials = ArrayList<MaterialDomainModel>()
            )
        }
    }

    fun mapDomainToUIModel(input:TaskDomainModel):Task{
        with(input){
            var material = ArrayList<Material>()
            var comment = ArrayList<Comment>()
            materials.forEach {
                material.add(materialMapper.mapDomainToUIModel(it))
            }
            comments.forEach {
                comment.add(commentMapper.mapDomainToUIModel(it))
            }
            return Task(
                id = id,
                taskName = taskName,
                invitingCode = invitingCode,
                deskription = deskription,
                mentorID =  mentorID,
                projectID = projectID,
                status = status,
                start_date = start_date,
                deadline_date = deadline_date,
                review_date = review_date,
                comments = comment,
                materials = material
            )
        }
    }
}