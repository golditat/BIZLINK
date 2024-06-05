package com.example.bizlink.domain.usecases

import com.example.bizlink.domain.repository.BizlinkRepository
import com.example.bizlink.data.remote.retrofit.mapper.ProjectDomainModelMapper
import com.example.bizlink.domain.mapper.ProjectUIModelMapper
import com.example.bizlink.ui.entities.Project
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SusProjectUsecase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: BizlinkRepository,
    private val mapper: ProjectDomainModelMapper,
    private val uiMapper: ProjectUIModelMapper
) {

    suspend operator fun invoke(code:String, id:Int):Project {
        return withContext(dispatcher) {
            val data = uiMapper.mapDomainToUIModel(mapper.mapResponseToDomainModel(repository.subscribeProject(code, id)))
            return@withContext data
        }
    }

    private fun validateUserData() {

    }
}