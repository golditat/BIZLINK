package com.example.bizlink.data.remote.retrofit.mapper


import com.example.bizlink.data.remote.retrofit.req.MaterialRequest
import com.example.bizlink.data.remote.retrofit.resp.MaterialResponce
import com.example.bizlink.domain.model.MaterialDomainModel
import com.example.bizlink.ui.entities.Material

class MaterialDomainModelMapper {

    fun mapResponseToDomainModel(input: MaterialResponce): MaterialDomainModel{
        with(input) {
            return MaterialDomainModel(
                id = id,
                materialName = materialName,
                file = file,
                taskID = taskID
            )
        }
    }
    fun mapDomainModelToRequest(input: MaterialDomainModel): MaterialRequest {
        with(input) {
            return MaterialRequest(
                materialName = materialName,
                file = file,
                taskID = taskID
            )
        }
    }
}