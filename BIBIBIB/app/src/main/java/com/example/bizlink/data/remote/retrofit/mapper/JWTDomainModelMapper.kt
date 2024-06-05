package com.example.bizlink.data.remote.retrofit.mapper

import com.example.bizlink.data.remote.retrofit.resp.JWTResponce
import com.example.bizlink.domain.model.JWTDomainModel
import com.example.bizlink.ui.entities.JWTLogin

class JWTDomainModelMapper {

    fun mapResponseToDomainModel(input: JWTResponce): JWTDomainModel {
        with(input) {
            return JWTDomainModel(
                id =  id,
                token = token,
                refreshToken = refreshToken,
                type = type,
                username = username,
                roleSet = roleSet,
                photo = photo
            )
        }
    }

}