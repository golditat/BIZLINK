package com.example.bizlink.domain.usecases

import com.example.bizlink.di.ServiceLocator
import com.example.bizlink.ui.entities.ProjectItemModel
import com.example.bizlink.ui.entities.TaskItemModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetUsersTasksUsecase(
    private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke():List<TaskItemModel> {
        return withContext(dispatcher) {
            var projects = ArrayList<TaskItemModel>()
            val data = ServiceLocator.projectDao.getAllProjects()
            data?.forEach {
                projects.add(
                    TaskItemModel(
                    id = it.id,
                    taskName = it.projectName
                )
                )
            }
            return@withContext projects
        }
    }
}