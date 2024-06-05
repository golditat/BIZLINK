package com.example.bizlink.domain.mapper

import com.example.bizlink.data.remote.retrofit.req.CommentRequest
import com.example.bizlink.data.remote.retrofit.resp.CommentResponce
import com.example.bizlink.domain.model.CommentDomainModel
import com.example.bizlink.ui.entities.Comment

class CommentUIModelMapper {
    fun mapUIToDomainModel(input: Comment): CommentDomainModel {
        with(input) {
            return CommentDomainModel(
                id = id,
                content = content,
                date = date,
                ownerID = ownerID,
                taskID = taskID
            )
        }
    }
    fun mapDomainToUIModel(input: CommentDomainModel): Comment {
        with(input) {
            return Comment(
                id = id,
                content = content,
                date = date,
                ownerID = ownerID,
                taskID = taskID
            )
        }
    }
}