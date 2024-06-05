package com.example.bizlink.domain.usecases

import com.example.bizlink.data.remote.retrofit.mapper.TaskDomainModelMapper
import com.example.bizlink.domain.mapper.TaskUIModelMapper
import com.example.bizlink.domain.repository.BizlinkRepository
import com.example.bizlink.ui.entities.TaskItemModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetDeadlinedTaskUsecase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: BizlinkRepository
) {
    suspend operator fun invoke(input:Long):List<TaskItemModel> {
        return withContext(dispatcher) {
            var rez = ArrayList<TaskItemModel>()
            val data = repository.getDeadlinedTasks(input)
            data.forEach {
                rez.add(
                    TaskItemModel(
                        it.id,
                        it.taskName
                    )
                )
            }
            return@withContext rez
        }
    }
}