package com.example.bizlink.domain.usecases

import com.example.bizlink.domain.repository.BizlinkRepository
import com.example.bizlink.data.remote.retrofit.mapper.ProjectDomainModelMapper
import com.example.bizlink.domain.mapper.ProjectUIModelMapper
import com.example.bizlink.ui.entities.Project
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CreateProjectUsecase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: BizlinkRepository,
    private val mapperDomain: ProjectDomainModelMapper,
    private val mapperUI:ProjectUIModelMapper
    ){

        suspend operator fun invoke(project: Project):Project {
            var data:Project
            withContext(dispatcher) {
                data = mapperUI.mapDomainToUIModel(mapperDomain.mapResponseToDomainModel(
                    repository.createProject(mapperDomain.mapDomainModelToRequest(mapperUI.mapUIToDomainModel(project)))))
            }
            return data
        }

        private fun validateUserData() {

        }
}