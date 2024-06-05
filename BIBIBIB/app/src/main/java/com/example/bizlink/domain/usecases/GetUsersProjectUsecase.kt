package com.example.bizlink.domain.usecases

import com.example.bizlink.di.ServiceLocator
import com.example.bizlink.domain.repository.BizlinkRepository
import com.example.bizlink.ui.entities.Project
import com.example.bizlink.ui.entities.ProjectItemModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetUsersProjectUsecase(
    private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke():List<ProjectItemModel> {
        return withContext(dispatcher) {
            var projects = ArrayList<ProjectItemModel>()
            val data = ServiceLocator.projectDao.getAllProjects()
            data?.forEach {
                projects.add(ProjectItemModel(
                    id = it.id,
                    projectName = it.projectName
                ))
            }
            return@withContext projects
        }
    }
}