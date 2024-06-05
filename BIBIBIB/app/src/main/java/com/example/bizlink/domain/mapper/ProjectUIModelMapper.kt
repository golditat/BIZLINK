package com.example.bizlink.domain.mapper

import com.example.bizlink.data.remote.retrofit.req.ProjectRequest
import com.example.bizlink.data.remote.retrofit.resp.ProjectResponce
import com.example.bizlink.domain.model.ProjectDomainModel
import com.example.bizlink.ui.entities.Project

class ProjectUIModelMapper {
    fun mapUIToDomainModel(input: Project): ProjectDomainModel {
        with(input) {
            return ProjectDomainModel(
                id = id,
                projectName = projectName,
                invitingCode = invitingCode,
                ownerID = ownerID
            )
        }
    }
    fun mapDomainToUIModel(input: ProjectDomainModel): Project {
        with(input) {
            return Project(
                id = id,
                projectName = projectName,
                ownerID = ownerID,
                invitingCode = invitingCode
            )
        }
    }
}