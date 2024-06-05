package com.example.bizlink.domain.usecases

import com.example.bizlink.domain.repository.BizlinkRepository
import com.example.bizlink.data.remote.retrofit.mapper.TaskDomainModelMapper
import com.example.bizlink.domain.mapper.TaskUIModelMapper
import com.example.bizlink.ui.entities.Task
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CreateTaskUsecase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: BizlinkRepository,
    private val mapper: TaskDomainModelMapper,
    private val uiMapper:TaskUIModelMapper
){

    suspend operator fun invoke(task: Task):Task {
       return withContext(dispatcher) {
            val data = uiMapper.mapDomainToUIModel(mapper.mapResponseToDomainModel(repository.createTask(
                mapper.mapDomainModelToRequest(uiMapper.mapUIToDomainModel(task))
            )))
            return@withContext data
       }
    }

    private fun validateUserData() {

    }
}