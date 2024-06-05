package com.example.bizlink.domain.usecases


import com.example.bizlink.domain.repository.BizlinkRepository
import com.example.bizlink.data.remote.retrofit.mapper.ProjectDomainModelMapper
import com.example.bizlink.domain.mapper.ProjectUIModelMapper
import com.example.bizlink.ui.entities.Project
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetProjectUsecase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: BizlinkRepository,
    private val mapper: ProjectDomainModelMapper,
    private val uiMapper: ProjectUIModelMapper
) {

    suspend operator fun invoke(id:Int):List<Project> {
        return withContext(dispatcher) {
            val data = repository.getProjects(id)
            var projects = ArrayList<Project>()
            data.forEach {
                projects.add(uiMapper.mapDomainToUIModel(mapper.mapResponseToDomainModel(it)))
            }
            return@withContext projects
        }
    }

    private fun validateUserData() {

    }
}