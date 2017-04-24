package com.bl_lia.kirakiratter.domain.interactor.account

import com.bl_lia.kirakiratter.domain.entity.Relationship
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.repository.AccountRepository
import io.reactivex.Single

class GetRelationshipUseCase(
        private val accountRepository: AccountRepository,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) : SingleUseCase<Relationship>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Single<Relationship> =
            accountRepository.relationship(params[0] as Int)
}