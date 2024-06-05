package com.example.bizlink.domain.usecases

import com.example.bizlink.domain.repository.BizlinkRepository
import com.example.bizlink.data.remote.retrofit.mapper.UserDomainModelMapper
import com.example.bizlink.domain.mapper.UserUIModelMapper
import com.example.bizlink.ui.entities.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RegistrationUsecase (
    private val dispatcher: CoroutineDispatcher,
    private val repository: BizlinkRepository,
    private val mapper: UserDomainModelMapper,
    private val userUIModelMapper: UserUIModelMapper
) {

    suspend operator fun invoke(user: User, password:String, repPassword:String) {
        return withContext(dispatcher) {
            val data = repository.reg(mapper.mapDomainModelToRequest(userUIModelMapper.mapUIToDomainModel(user, password, repPassword)))
        }
    }

    private fun validateUserData() {

    }
}