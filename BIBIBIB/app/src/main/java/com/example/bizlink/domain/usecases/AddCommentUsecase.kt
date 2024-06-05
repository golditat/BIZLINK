package com.example.bizlink.domain.usecases

import com.example.bizlink.domain.repository.BizlinkRepository
import com.example.bizlink.data.remote.retrofit.mapper.CommentDomainModelMapper
import com.example.bizlink.domain.mapper.CommentUIModelMapper
import com.example.bizlink.ui.entities.Comment
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AddCommentUsecase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: BizlinkRepository,
    private val mapperDomain: CommentDomainModelMapper,
    private val mapperUI:CommentUIModelMapper

) {
        suspend operator fun invoke(comment: Comment) {
            return withContext(dispatcher) {
                val data = repository.addComment(mapperDomain.mapDomainModelToRequest(mapperUI.mapUIToDomainModel(comment)))
            }
        }

        private fun validateUserData() {

        }
}