package com.example.bizlink.domain.mapper

import com.example.bizlink.data.remote.retrofit.resp.JWTResponce
import com.example.bizlink.domain.model.JWTDomainModel
import com.example.bizlink.ui.entities.JWTLogin

class JWTUIModelMapper {
    fun mapDomainToUIModel(input: JWTDomainModel): JWTLogin {
        with(input) {
            return JWTLogin(
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