package com.example.bizlink.data.remote.retrofit.mapper


import com.example.bizlink.data.remote.retrofit.req.CommentRequest
import com.example.bizlink.data.remote.retrofit.resp.CommentResponce
import com.example.bizlink.domain.model.CommentDomainModel
import com.example.bizlink.ui.entities.Comment

class CommentDomainModelMapper {

    fun mapResponseToDomainModel(input: CommentResponce): CommentDomainModel {
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
    fun mapDomainModelToRequest(input: CommentDomainModel): CommentRequest {
        with(input) {
            return CommentRequest(
                content = content,
                date = date,
                ownerID = ownerID,
                taskID = taskID
            )
        }
    }
}