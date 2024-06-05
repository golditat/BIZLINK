package com.example.bizlink.domain.usecases


import com.example.bizlink.domain.repository.BizlinkRepository
import com.example.bizlink.data.remote.retrofit.mapper.TaskDomainModelMapper
import com.example.bizlink.domain.mapper.TaskUIModelMapper
import com.example.bizlink.ui.entities.Task
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SubTaskUsecase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: BizlinkRepository,
    private val mapper: TaskDomainModelMapper,
    private val uiMapper: TaskUIModelMapper
) {

    suspend operator fun invoke(code:String, id:Int): Task {
        return withContext(dispatcher) {
            val data = uiMapper.mapDomainToUIModel(mapper.mapResponseToDomainModel(repository.subscribeTask(code, id)))
            return@withContext data
        }
    }

    private fun validateUserData() {

    }
}