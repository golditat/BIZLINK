package com.example.bizlink.domain.usecases

import com.example.bizlink.domain.repository.BizlinkRepository
import com.example.bizlink.data.remote.retrofit.mapper.MaterialDomainModelMapper
import com.example.bizlink.domain.mapper.MaterialUIModelMapper
import com.example.bizlink.ui.entities.Material
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AddMaterialUsecase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: BizlinkRepository,
    private val mapperDomain: MaterialDomainModelMapper,
    private val mapperUI:MaterialUIModelMapper
    ) {

        suspend operator fun invoke(material: Material) {
            return withContext(dispatcher) {
                val data = repository.addMaterial(mapperDomain.mapDomainModelToRequest(mapperUI.mapUIToDomainModel(material)))
            }
        }

        private fun validateUserData() {

        }
}