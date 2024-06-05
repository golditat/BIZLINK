package com.example.bizlink.domain.mapper

import com.example.bizlink.data.remote.retrofit.req.MaterialRequest
import com.example.bizlink.data.remote.retrofit.resp.MaterialResponce
import com.example.bizlink.domain.model.MaterialDomainModel
import com.example.bizlink.ui.entities.Material

class MaterialUIModelMapper {
    fun mapUIToDomainModel(input: Material): MaterialDomainModel {
        with(input) {
            return MaterialDomainModel(
                id = id,
                materialName = materialName,
                file = file,
                taskID = taskID
            )
        }
    }
    fun mapDomainToUIModel(input: MaterialDomainModel): Material {
        with(input) {
            return Material(
                materialName = materialName,
                file = file,
                taskID = taskID,
                id = id
            )
        }
    }
}