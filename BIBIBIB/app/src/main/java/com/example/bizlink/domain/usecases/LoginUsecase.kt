package com.example.bizlink.domain.usecases


import com.example.bizlink.data.database.entity.UserEntity
import com.example.bizlink.domain.repository.BizlinkRepository
import com.example.bizlink.data.remote.retrofit.mapper.JWTDomainModelMapper
import com.example.bizlink.data.remote.retrofit.req.UserLoginRequest
import com.example.bizlink.di.ServiceLocator
import com.example.bizlink.domain.mapper.JWTUIModelMapper
import com.example.bizlink.ui.entities.JWTLogin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LoginUsecase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: BizlinkRepository,
    private val mapper: JWTDomainModelMapper,
    private val uiMapper:JWTUIModelMapper
) {
    suspend operator fun invoke(email:String, password:String):JWTLogin {
        return withContext(dispatcher) {
            val data = uiMapper.mapDomainToUIModel(mapper.mapResponseToDomainModel(repository.login(UserLoginRequest(email, password))))
            ServiceLocator.bizlinkPref.edit()
                .putInt("USER_ID", data.id)
                .putString("EMAIL", email)
                .putString("PASSWORD", password)
                .putString("REFRESH", data.refreshToken)
                .apply()
            ServiceLocator.userDao.addUser(UserEntity(id = 0, email = email, password = password, photo = ByteArray(0)))
            return@withContext data
        }
    }
}