package com.example.bizlink.domain.mapper

import com.example.bizlink.domain.model.UserDomainModel
import com.example.bizlink.ui.entities.User

class UserUIModelMapper {
    fun mapDomainToUIModel(input:UserDomainModel): User {
         with(input){
             return User(
                 id = id,
                 email = email,
                 photo = photo
            )
        }
    }

    fun mapUIToDomainModel(input:User, password:String, repPassword:String):UserDomainModel{
        with(input){
            return UserDomainModel(
                id = id,
                photo = photo,
                email = email,
                password = password,
                repPassword = repPassword
            )
        }
    }
}