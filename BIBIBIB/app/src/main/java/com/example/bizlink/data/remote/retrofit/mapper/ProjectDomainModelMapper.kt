package com.example.bizlink.data.remote.retrofit.mapper


import com.example.bizlink.data.remote.retrofit.req.ProjectRequest
import com.example.bizlink.data.remote.retrofit.resp.ProjectResponce
import com.example.bizlink.domain.model.ProjectDomainModel
import com.example.bizlink.ui.entities.Project

class ProjectDomainModelMapper {

        fun mapResponseToDomainModel(input: ProjectResponce): ProjectDomainModel {
            with(input) {
                return ProjectDomainModel(
                    id = id,
                    projectName = projectName,
                    invitingCode = invitingCode,
                    ownerID = ownerID
                )
            }
        }
        fun mapDomainModelToRequest(input: ProjectDomainModel): ProjectRequest {
            with(input) {
                return ProjectRequest(
                    projectName = projectName,
                    ownerID = ownerID
                )
            }
        }
}