package com.example.bizlink.domain.usecases

import com.example.bizlink.domain.repository.BizlinkRepository
import com.example.bizlink.data.remote.retrofit.mapper.TaskDomainModelMapper
import com.example.bizlink.domain.mapper.TaskUIModelMapper
import com.example.bizlink.ui.entities.Task
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetTaskUsecase (
    private val dispatcher: CoroutineDispatcher,
    private val repository: BizlinkRepository,
    private val mapperDomain:TaskDomainModelMapper,
    private val mapperUI:TaskUIModelMapper
) {

    suspend operator fun invoke(id:Int):List<Task> {
        return withContext(dispatcher) {
            val data = repository.getTasks(id)
            var rezult = ArrayList<Task>()
            data.forEach{
                rezult.add(mapperUI.mapDomainToUIModel(mapperDomain.mapResponseToDomainModel(it)))
            }
            return@withContext rezult
        }
    }

    private fun validateUserData() {

    }
}