package com.example.bizlink.data.remote.retrofit.mapper


import com.example.bizlink.data.remote.retrofit.req.UserRequest
import com.example.bizlink.data.remote.retrofit.resp.UserResponce
import com.example.bizlink.domain.model.UserDomainModel

class UserDomainModelMapper {

    private val materialMapper:MaterialDomainModelMapper = MaterialDomainModelMapper()
    private val commentMapper:CommentDomainModelMapper = CommentDomainModelMapper()
    fun mapResponseToDomainModel(input: UserResponce): UserDomainModel {
        with(input) {
            return UserDomainModel(
                id =  id,
                email = email,
                photo = photo,
                password = "",
                repPassword = ""
            )
        }
    }
    fun mapDomainModelToRequest(input: UserDomainModel): UserRequest {
        with(input) {
            return UserRequest(
                email = email,
                password = password,
                repeatPassword = repPassword,
                photo = photo
            )
        }
    }
}